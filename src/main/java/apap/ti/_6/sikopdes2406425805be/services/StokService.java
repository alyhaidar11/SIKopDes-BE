package apap.ti._6.sikopdes2406425805be.services;

import apap.ti._6.sikopdes2406425805be.dto.mutasistok.MutasiStokCreateRequestDTO;
import apap.ti._6.sikopdes2406425805be.dto.mutasistok.MutasiStokResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.stok.StokGeraiResponseDTO;
import apap.ti._6.sikopdes2406425805be.exceptions.BadRequestException;
import apap.ti._6.sikopdes2406425805be.exceptions.InactiveResourceException;
import apap.ti._6.sikopdes2406425805be.exceptions.InsufficientStockException;
import apap.ti._6.sikopdes2406425805be.exceptions.ResourceNotFoundException;
import apap.ti._6.sikopdes2406425805be.models.Gerai;
import apap.ti._6.sikopdes2406425805be.models.MutasiStok;
import apap.ti._6.sikopdes2406425805be.models.MutasiStok.JenisMutasi;
import apap.ti._6.sikopdes2406425805be.models.MutasiStok.SumberMutasi;
import apap.ti._6.sikopdes2406425805be.models.Produk;
import apap.ti._6.sikopdes2406425805be.models.StokGerai;
import apap.ti._6.sikopdes2406425805be.repositories.MutasiStokRepository;
import apap.ti._6.sikopdes2406425805be.repositories.StokGeraiRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class StokService {

    private static final String STOK_ID_PREFIX = "STK";
    private static final String MUTASI_ID_PREFIX = "MTS";
    private static final int ID_DIGITS = 4; 

    private final StokGeraiRepository stokGeraiRepository;
    private final MutasiStokRepository mutasiStokRepository;
    private final GeraiService geraiService;
    private final ProdukService produkService;

    public StokService(
            StokGeraiRepository stokGeraiRepository,
            MutasiStokRepository mutasiStokRepository,
            GeraiService geraiService,
            ProdukService produkService) {
        this.stokGeraiRepository = stokGeraiRepository;
        this.mutasiStokRepository = mutasiStokRepository;
        this.geraiService = geraiService;
        this.produkService = produkService;
    }

    @Transactional(readOnly = true)
    public List<StokGeraiResponseDTO> getPersediaan(String geraiId, String produkId, String sort) {
        Specification<StokGerai> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (hasText(geraiId)) {
                predicates.add(cb.equal(root.get("gerai").get("id"), geraiId.trim()));
            }
            if (hasText(produkId)) {
                predicates.add(cb.equal(root.get("produk").get("id"), produkId.trim()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return stokGeraiRepository.findAll(spec, resolvePersediaanSort(sort))
                .stream()
                .map(StokGeraiResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MutasiStokResponseDTO> getRiwayatMutasi(
            String geraiId,
            String produkId,
            String jenis,
            LocalDate startDate,
            LocalDate endDate) {

        JenisMutasi jenisFilter = parseJenis(jenis);

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new BadRequestException("startDate tidak boleh setelah endDate");
        }

        Specification<MutasiStok> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (hasText(geraiId)) {
                predicates.add(cb.equal(root.get("gerai").get("id"), geraiId.trim()));
            }
            if (hasText(produkId)) {
                predicates.add(cb.equal(root.get("produk").get("id"), produkId.trim()));
            }
            if (jenisFilter != null) {
                predicates.add(cb.equal(root.get("jenis"), jenisFilter));
            }
            // inclusive: startDate 00:00:00 s.d. sebelum hari setelah endDate
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("tanggalMutasi"), startDate.atStartOfDay()));
            }
            if (endDate != null) {
                predicates.add(cb.lessThan(
                        root.get("tanggalMutasi"), endDate.plusDays(1).atStartOfDay()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Sort sort = Sort.by(Sort.Direction.DESC, "tanggalMutasi")
                .and(Sort.by(Sort.Direction.DESC, "id"));

        return mutasiStokRepository.findAll(spec, sort)
                .stream()
                .map(MutasiStokResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public MutasiStokResponseDTO catatMutasi(MutasiStokCreateRequestDTO request) {
        if (request.getJenis() == null) {
            throw new BadRequestException("Jenis mutasi wajib diisi");
        }
        String keterangan = normalize(request.getKeterangan());

        MutasiStok mutasi = switch (request.getJenis()) {
            case MASUK -> prosesMasuk(request, keterangan);
            case KELUAR -> prosesKeluar(request, keterangan);
            case PENYESUAIAN -> prosesPenyesuaian(request, keterangan);
        };

        return MutasiStokResponseDTO.fromEntity(mutasi);
    }

    private MutasiStok prosesMasuk(MutasiStokCreateRequestDTO req, String keterangan) {
        int jumlah = requirePositive(req.getJumlah(), "Jumlah");

        Gerai gerai = geraiService.getGeraiAktifById(req.getGeraiId());
        Produk produk = produkService.getProdukAktifById(req.getProdukId());

        Optional<StokGerai> stok = cariStokDenganLock(gerai, produk);
        int sebelum = stok.map(StokGerai::getJumlah).orElse(0);

        return catatPerubahan(gerai, produk, stok, sebelum + jumlah,
                JenisMutasi.MASUK, jumlah, SumberMutasi.MANUAL, null, keterangan);
    }

    private MutasiStok prosesKeluar(MutasiStokCreateRequestDTO req, String keterangan) {
        int jumlah = requirePositive(req.getJumlah(), "Jumlah");
        if (keterangan == null) {
            throw new BadRequestException("Keterangan wajib diisi untuk mutasi KELUAR");
        }

        Gerai gerai = geraiService.getGeraiById(req.getGeraiId());
        Produk produk = produkService.getProdukById(req.getProdukId());

        Optional<StokGerai> stok = cariStokDenganLock(gerai, produk);

        if (stok.isEmpty()) {
            if (!isAktif(gerai) || !isAktif(produk)) {
                throw new ResourceNotFoundException(
                        "Record stok untuk Produk " + produk.getNama() + " di Gerai "
                        + gerai.getNama() + " belum ada");
            }
            throw new InsufficientStockException(produk.getNama(), 0, jumlah);
        }

        int sebelum = stok.get().getJumlah();
        if (sebelum < jumlah) {
            throw new InsufficientStockException(produk.getNama(), sebelum, jumlah);
        }

        return catatPerubahan(gerai, produk, stok, sebelum - jumlah,
                JenisMutasi.KELUAR, jumlah, SumberMutasi.MANUAL, null, keterangan);
    }

    // ------------------------------------------------------------------
    // PENYESUAIAN: stokFisikAktual menggantikan saldo sistem
    // ------------------------------------------------------------------
    private MutasiStok prosesPenyesuaian(MutasiStokCreateRequestDTO req, String keterangan) {
        Integer aktual = req.getStokFisikAktual();
        if (aktual == null || aktual < 0) {
            throw new BadRequestException("Stok fisik aktual wajib diisi dan minimal 0");
        }
        if (keterangan == null) {
            throw new BadRequestException("Keterangan wajib diisi untuk mutasi PENYESUAIAN");
        }

        Gerai gerai = geraiService.getGeraiById(req.getGeraiId());
        Produk produk = produkService.getProdukById(req.getProdukId());
        boolean keduanyaAktif = isAktif(gerai) && isAktif(produk);

        Optional<StokGerai> stok = cariStokDenganLock(gerai, produk);

        if (!keduanyaAktif && stok.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Record stok untuk Produk " + produk.getNama() + " di Gerai "
                    + gerai.getNama() + " belum ada");
        }

        int saldo = stok.map(StokGerai::getJumlah).orElse(0);

        if (aktual == saldo) {
            throw new BadRequestException(
                    "Stok fisik aktual harus berbeda dari saldo sistem (" + saldo + ")");
        }
        if (!keduanyaAktif && aktual > saldo) {
            throw new InactiveResourceException(
                    "Gerai atau Produk sudah nonaktif sehingga penyesuaian hanya boleh menurunkan "
                    + "saldo (saldo saat ini: " + saldo + ", stok fisik aktual: " + aktual + ")");
        }

        int selisih = Math.abs(aktual - saldo);

        return catatPerubahan(gerai, produk, stok, aktual,
                JenisMutasi.PENYESUAIAN, selisih, SumberMutasi.MANUAL, null, keterangan);
    }

    @Transactional(readOnly = true)
    public void validasiStokTersedia(Gerai gerai, Produk produk, int jumlah) {
        int tersedia = stokGeraiRepository
                .findByGerai_IdAndProduk_Id(gerai.getId(), produk.getId())
                .map(StokGerai::getJumlah)
                .orElse(0);

        if (tersedia < jumlah) {
            throw new InsufficientStockException(produk.getNama(), tersedia, jumlah);
        }
    }

    @Transactional
    public void kurangiStokUntukPenjualan(Gerai gerai, Produk produk, int jumlah, String transaksiId) {
        Optional<StokGerai> stok = cariStokDenganLock(gerai, produk);
        int sebelum = stok.map(StokGerai::getJumlah).orElse(0);

        if (sebelum < jumlah) {
            throw new InsufficientStockException(produk.getNama(), sebelum, jumlah);
        }

        catatPerubahan(gerai, produk, stok, sebelum - jumlah,
                JenisMutasi.KELUAR, jumlah, SumberMutasi.PENJUALAN, transaksiId,
                "Penjualan " + transaksiId);
    }

    @Transactional
    public void kembalikanStokDariPembatalan(Gerai gerai, Produk produk, int jumlah, String transaksiId) {
        Optional<StokGerai> stok = cariStokDenganLock(gerai, produk);
        int sebelum = stok.map(StokGerai::getJumlah).orElse(0);

        catatPerubahan(gerai, produk, stok, sebelum + jumlah,
                JenisMutasi.MASUK, jumlah, SumberMutasi.PEMBATALAN_PENJUALAN, transaksiId,
                "Pembatalan transaksi " + transaksiId);
    }

    private MutasiStok catatPerubahan(
            Gerai gerai,
            Produk produk,
            Optional<StokGerai> stokAda,
            int stokSesudah,
            JenisMutasi jenis,
            int jumlah,
            SumberMutasi sumber,
            String referensiId,
            String keterangan) {

        int stokSebelum = stokAda.map(StokGerai::getJumlah).orElse(0);

        StokGerai stok = stokAda.orElseGet(() -> {
            StokGerai baru = new StokGerai();
            baru.setId(nextId(STOK_ID_PREFIX,
                    stokGeraiRepository.findTopByOrderByIdDesc().map(StokGerai::getId)));
            baru.setGerai(gerai);
            baru.setProduk(produk);
            return baru;
        });
        stok.setJumlah(stokSesudah);
        stokGeraiRepository.save(stok);

        MutasiStok mutasi = new MutasiStok();
        mutasi.setId(nextId(MUTASI_ID_PREFIX,
                mutasiStokRepository.findTopByOrderByIdDesc().map(MutasiStok::getId)));
        mutasi.setGerai(gerai);
        mutasi.setProduk(produk);
        mutasi.setJenis(jenis);
        mutasi.setJumlah(jumlah);
        mutasi.setStokSebelum(stokSebelum);
        mutasi.setStokSesudah(stokSesudah);
        mutasi.setSumber(sumber);
        mutasi.setReferensiId(referensiId);
        mutasi.setKeterangan(keterangan);

        return mutasiStokRepository.save(mutasi);
    }

    private Optional<StokGerai> cariStokDenganLock(Gerai gerai, Produk produk) {
        return stokGeraiRepository.findWithLockByGerai_IdAndProduk_Id(gerai.getId(), produk.getId());
    }

    private Sort resolvePersediaanSort(String sort) {
        String value = hasText(sort) ? sort.trim().toUpperCase(Locale.ROOT) : "JUMLAH_ASC";
        Sort namaProdukAsc = Sort.by(Sort.Direction.ASC, "produk.nama");
        Sort namaGeraiAsc = Sort.by(Sort.Direction.ASC, "gerai.nama");

        return switch (value) {
            case "JUMLAH_ASC" -> Sort.by(Sort.Direction.ASC, "jumlah").and(namaProdukAsc).and(namaGeraiAsc);
            case "JUMLAH_DESC" -> Sort.by(Sort.Direction.DESC, "jumlah").and(namaProdukAsc).and(namaGeraiAsc);
            case "PRODUK_ASC" -> namaProdukAsc.and(namaGeraiAsc);
            case "PRODUK_DESC" -> Sort.by(Sort.Direction.DESC, "produk.nama").and(namaGeraiAsc);
            default -> throw new BadRequestException(
                    "Nilai sort tidak valid: " + sort
                    + ". Gunakan JUMLAH_ASC, JUMLAH_DESC, PRODUK_ASC, atau PRODUK_DESC");
        };
    }

    private JenisMutasi parseJenis(String jenis) {
        if (!hasText(jenis)) {
            return null;
        }
        try {
            return JenisMutasi.valueOf(jenis.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(
                    "Jenis mutasi tidak valid: " + jenis + ". Gunakan MASUK, KELUAR, atau PENYESUAIAN");
        }
    }

    private int requirePositive(Integer value, String label) {
        if (value == null || value <= 0) {
            throw new BadRequestException(label + " wajib diisi dan harus lebih dari 0");
        }
        return value;
    }

    private boolean isAktif(Gerai gerai) {
        return Boolean.TRUE.equals(gerai.getStatusAktif());
    }

    private boolean isAktif(Produk produk) {
        return Boolean.TRUE.equals(produk.getStatusAktif());
    }

    private boolean hasText(String s) {
        return s != null && !s.isBlank();
    }

    private String normalize(String s) {
        return hasText(s) ? s.trim() : null;
    }

    /** Format ID: prefix + 4 digit (STK0001, MTS0001, ...). */
    private String nextId(String prefix, Optional<String> lastId) {
        int last = lastId.map(id -> {
            try {
                return Integer.parseInt(id.substring(prefix.length()));
            } catch (RuntimeException e) {
                throw new IllegalStateException("Format ID tidak dikenali: " + id);
            }
        }).orElse(0);

        int next = last + 1;
        if (next > (int) Math.pow(10, ID_DIGITS) - 1) {
            throw new IllegalStateException("Kapasitas ID " + prefix + " sudah penuh");
        }
        return prefix + String.format("%0" + ID_DIGITS + "d", next);
    }
}