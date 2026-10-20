package apap.ti._6.sikopdes2406425805be.services;

import apap.ti._6.sikopdes2406425805be.dto.penjualan.DetailTransaksiResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.penjualan.PembatalanPenjualanRequestDTO;
import apap.ti._6.sikopdes2406425805be.dto.penjualan.PenjualanCreateRequestDTO;
import apap.ti._6.sikopdes2406425805be.dto.penjualan.PenjualanDetailResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.penjualan.PenjualanItemRequestDTO;
import apap.ti._6.sikopdes2406425805be.dto.penjualan.PenjualanResponseDTO;
import apap.ti._6.sikopdes2406425805be.exceptions.BadRequestException;
import apap.ti._6.sikopdes2406425805be.exceptions.InvalidTransactionStatusException;
import apap.ti._6.sikopdes2406425805be.exceptions.ResourceNotFoundException;
import apap.ti._6.sikopdes2406425805be.models.Anggota;
import apap.ti._6.sikopdes2406425805be.models.DetailTransaksi;
import apap.ti._6.sikopdes2406425805be.models.DetailTransaksi.JenisHarga;
import apap.ti._6.sikopdes2406425805be.models.Gerai;
import apap.ti._6.sikopdes2406425805be.models.Produk;
import apap.ti._6.sikopdes2406425805be.models.TransaksiPenjualan;
import apap.ti._6.sikopdes2406425805be.models.TransaksiPenjualan.StatusTransaksi;
import apap.ti._6.sikopdes2406425805be.repositories.DetailTransaksiRepository;
import apap.ti._6.sikopdes2406425805be.repositories.TransaksiPenjualanRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class PenjualanService {

    private static final String TRANSAKSI_ID_PREFIX = "TRX";
    private static final int TRANSAKSI_ID_DIGITS = 5;
    private static final String DETAIL_ID_PREFIX = "DTL";
    private static final int DETAIL_ID_DIGITS = 4;

    private final TransaksiPenjualanRepository transaksiPenjualanRepository;
    private final DetailTransaksiRepository detailTransaksiRepository;
    private final AnggotaService anggotaService;
    private final GeraiService geraiService;
    private final ProdukService produkService;
    private final StokService stokService;

    public PenjualanService(
            TransaksiPenjualanRepository transaksiPenjualanRepository,
            DetailTransaksiRepository detailTransaksiRepository,
            AnggotaService anggotaService,
            GeraiService geraiService,
            ProdukService produkService,
            StokService stokService) {
        this.transaksiPenjualanRepository = transaksiPenjualanRepository;
        this.detailTransaksiRepository = detailTransaksiRepository;
        this.anggotaService = anggotaService;
        this.geraiService = geraiService;
        this.produkService = produkService;
        this.stokService = stokService;
    }

    @Transactional(readOnly = true)
    public List<PenjualanResponseDTO> getAll(
            String geraiId,
            String status,
            LocalDate startDate,
            LocalDate endDate,
            Long minTotal,
            Long maxTotal,
            String search) {

        StatusTransaksi statusFilter = parseStatus(status);

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new BadRequestException("startDate tidak boleh setelah endDate");
        }
        if (minTotal != null && maxTotal != null && minTotal > maxTotal) {
            throw new BadRequestException("minTotal tidak boleh lebih besar dari maxTotal");
        }

        String keyword = hasText(search)
                ? "%" + search.trim().toLowerCase(Locale.ROOT) + "%"
                : null;

        Specification<TransaksiPenjualan> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (hasText(geraiId)) {
                predicates.add(cb.equal(root.get("gerai").get("id"), geraiId.trim()));
            }
            if (statusFilter != null) {
                predicates.add(cb.equal(root.get("status"), statusFilter));
            }
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("tanggalTransaksi"), startDate.atStartOfDay()));
            }
            if (endDate != null) {
                predicates.add(cb.lessThan(
                        root.get("tanggalTransaksi"), endDate.plusDays(1).atStartOfDay()));
            }
            if (minTotal != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("total"), minTotal));
            }
            if (maxTotal != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("total"), maxTotal));
            }
            if (keyword != null) {
                Join<TransaksiPenjualan, Anggota> anggota = root.join("anggota", JoinType.LEFT);
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("id")), keyword),
                        cb.like(cb.lower(anggota.get("nama")), keyword),
                        cb.like(cb.lower(anggota.get("nomorAnggota")), keyword)));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Sort sort = Sort.by(Sort.Direction.DESC, "tanggalTransaksi")
                .and(Sort.by(Sort.Direction.DESC, "id"));

        return transaksiPenjualanRepository.findAll(spec, sort)
                .stream()
                .map(PenjualanResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public PenjualanDetailResponseDTO getDetail(String id) {
        TransaksiPenjualan transaksi = transaksiPenjualanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaksi", id));
        return buildDetail(transaksi);
    }

    @Transactional
    public PenjualanDetailResponseDTO checkout(PenjualanCreateRequestDTO request) {
        List<PenjualanItemRequestDTO> items = request.getItems();
        if (items == null || items.isEmpty()) {
            throw new BadRequestException("Minimal satu item harus dipilih");
        }
        if (!hasText(request.getGeraiId())) {
            throw new BadRequestException("Gerai wajib dipilih");
        }

        Gerai gerai = geraiService.getGeraiAktifById(request.getGeraiId().trim());

        Anggota anggota = hasText(request.getAnggotaId())
                ? anggotaService.getAnggotaForCheckout(request.getAnggotaId().trim())
                : null;
        JenisHarga jenisHarga = anggota != null ? JenisHarga.ANGGOTA : JenisHarga.NORMAL;

        List<ItemCheckout> itemCheckout = validasiDanHitung(gerai, items, jenisHarga);

        long total = 0L;
        for (ItemCheckout item : itemCheckout) {
            total = tambah(total, item.subtotal(), item.produk());
        }

        TransaksiPenjualan transaksi = new TransaksiPenjualan();
        transaksi.setId(nextTransaksiId());
        transaksi.setGerai(gerai);
        transaksi.setAnggota(anggota);
        transaksi.setStatus(StatusTransaksi.SELESAI);
        transaksi.setTotal(total);
        TransaksiPenjualan saved = transaksiPenjualanRepository.save(transaksi);

        int nomorDetail = lastNumber(
                detailTransaksiRepository.findTopByOrderByIdDesc().map(DetailTransaksi::getId),
                DETAIL_ID_PREFIX);

        List<DetailTransaksi> daftarDetail = new ArrayList<>();
        for (ItemCheckout item : itemCheckout) {
            nomorDetail++;
            DetailTransaksi detail = new DetailTransaksi();
            detail.setId(formatId(DETAIL_ID_PREFIX, DETAIL_ID_DIGITS, nomorDetail));
            detail.setTransaksi(saved);
            detail.setProduk(item.produk());
            detail.setNamaProduk(item.produk().getNama());
            detail.setJumlah(item.jumlah());
            detail.setJenisHarga(jenisHarga);
            detail.setHargaSatuan(item.hargaSatuan());
            detail.setSubtotal(item.subtotal());
            daftarDetail.add(detail);
        }
        detailTransaksiRepository.saveAll(daftarDetail);

        for (ItemCheckout item : itemCheckout) {
            stokService.kurangiStokUntukPenjualan(gerai, item.produk(), item.jumlah(), saved.getId());
        }

        return buildDetail(saved);
    }

    @Transactional
    public PenjualanDetailResponseDTO batal(String id, PembatalanPenjualanRequestDTO request) {
        TransaksiPenjualan transaksi = transaksiPenjualanRepository.findWithLockById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaksi", id));

        if (transaksi.getStatus() != StatusTransaksi.SELESAI) {
            throw new InvalidTransactionStatusException(transaksi.getId(), transaksi.getStatus());
        }

        String alasan = request.getAlasanPembatalan() == null ? "" : request.getAlasanPembatalan().trim();
        if (alasan.isEmpty()) {
            throw new BadRequestException("Alasan pembatalan wajib diisi");
        }

        List<DetailTransaksi> daftarDetail = new ArrayList<>(
                detailTransaksiRepository.findByTransaksi_IdOrderByNamaProdukAsc(transaksi.getId()));
        daftarDetail.sort(Comparator.comparing(d -> d.getProduk().getId()));

        for (DetailTransaksi detail : daftarDetail) {
            stokService.kembalikanStokDariPembatalan(
                    transaksi.getGerai(),
                    detail.getProduk(),
                    detail.getJumlah(),
                    transaksi.getId());
        }

        transaksi.setStatus(StatusTransaksi.DIBATALKAN);
        transaksi.setTanggalPembatalan(LocalDateTime.now());
        transaksi.setAlasanPembatalan(alasan);
        TransaksiPenjualan saved = transaksiPenjualanRepository.save(transaksi);

        return buildDetail(saved);
    }

    private List<ItemCheckout> validasiDanHitung(
            Gerai gerai,
            List<PenjualanItemRequestDTO> items,
            JenisHarga jenisHarga) {

        Set<String> sudahAda = new HashSet<>();
        for (PenjualanItemRequestDTO item : items) {
            if (item == null || !hasText(item.getProdukId())) {
                throw new BadRequestException("Produk wajib dipilih pada setiap item");
            }
            if (!sudahAda.add(item.getProdukId().trim())) {
                throw new BadRequestException(
                        "Produk dengan id " + item.getProdukId().trim()
                        + " muncul lebih dari satu kali. Setiap Produk hanya boleh satu baris item");
            }
        }

        List<ItemCheckout> hasil = new ArrayList<>();
        for (PenjualanItemRequestDTO item : items) {
            String produkId = item.getProdukId().trim();
            Integer jumlah = item.getJumlah();

            Produk produk = produkService.getProdukAktifById(produkId);

            if (jumlah == null || jumlah <= 0) {
                throw new BadRequestException(
                        "Jumlah untuk Produk " + produk.getNama() + " harus lebih dari 0");
            }

            stokService.validasiStokTersedia(gerai, produk, jumlah);

            long hargaSatuan = jenisHarga == JenisHarga.ANGGOTA
                    ? produk.getHargaAnggota()
                    : produk.getHargaNormal();
            long subtotal = kali(hargaSatuan, jumlah, produk);

            hasil.add(new ItemCheckout(produk, jumlah, hargaSatuan, subtotal));
        }

        hasil.sort(Comparator.comparing(i -> i.produk().getId()));
        return hasil;
    }

    private PenjualanDetailResponseDTO buildDetail(TransaksiPenjualan transaksi) {
        List<DetailTransaksiResponseDTO> detail = detailTransaksiRepository
                .findByTransaksi_IdOrderByNamaProdukAsc(transaksi.getId())
                .stream()
                .map(DetailTransaksiResponseDTO::fromEntity)
                .toList();
        return PenjualanDetailResponseDTO.fromEntity(transaksi, detail);
    }

    private long kali(long harga, int jumlah, Produk produk) {
        try {
            return Math.multiplyExact(harga, (long) jumlah);
        } catch (ArithmeticException e) {
            throw new BadRequestException("Nilai transaksi untuk Produk " + produk.getNama() + " terlalu besar");
        }
    }

    private long tambah(long a, long b, Produk produk) {
        try {
            return Math.addExact(a, b);
        } catch (ArithmeticException e) {
            throw new BadRequestException("Total transaksi terlalu besar (terakhir pada Produk " + produk.getNama() + ")");
        }
    }

    private StatusTransaksi parseStatus(String status) {
        if (!hasText(status)) {
            return null;
        }
        try {
            return StatusTransaksi.valueOf(status.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(
                    "Status transaksi tidak valid: " + status + ". Gunakan SELESAI atau DIBATALKAN");
        }
    }

    private String nextTransaksiId() {
        int next = lastNumber(
                transaksiPenjualanRepository.findTopByOrderByIdDesc().map(TransaksiPenjualan::getId),
                TRANSAKSI_ID_PREFIX) + 1;
        return formatId(TRANSAKSI_ID_PREFIX, TRANSAKSI_ID_DIGITS, next);
    }

    private int lastNumber(java.util.Optional<String> lastId, String prefix) {
        return lastId.map(id -> {
            try {
                return Integer.parseInt(id.substring(prefix.length()));
            } catch (RuntimeException e) {
                throw new IllegalStateException("Format ID tidak dikenali: " + id);
            }
        }).orElse(0);
    }

    private String formatId(String prefix, int digits, int number) {
        if (number > (int) Math.pow(10, digits) - 1) {
            throw new IllegalStateException("Kapasitas ID " + prefix + " sudah penuh");
        }
        return prefix + String.format("%0" + digits + "d", number);
    }

    private boolean hasText(String s) {
        return s != null && !s.isBlank();
    }

    private record ItemCheckout(Produk produk, int jumlah, long hargaSatuan, long subtotal) {
    }
}