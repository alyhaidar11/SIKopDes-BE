package apap.ti._6.sikopdes2406425805be.services;

import apap.ti._6.sikopdes2406425805be.dto.produk.ProdukCreateRequestDTO;
import apap.ti._6.sikopdes2406425805be.dto.produk.ProdukDetailResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.produk.ProdukResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.produk.ProdukUpdateRequestDTO;
import apap.ti._6.sikopdes2406425805be.dto.stok.StokGeraiResponseDTO;
import apap.ti._6.sikopdes2406425805be.exceptions.BadRequestException;
import apap.ti._6.sikopdes2406425805be.exceptions.DuplicateResourceException;
import apap.ti._6.sikopdes2406425805be.exceptions.InactiveResourceException;
import apap.ti._6.sikopdes2406425805be.exceptions.ResourceNotFoundException;
import apap.ti._6.sikopdes2406425805be.models.Produk;
import apap.ti._6.sikopdes2406425805be.repositories.ProdukRepository;
import apap.ti._6.sikopdes2406425805be.repositories.StokGeraiRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ProdukService {

    private static final String ID_PREFIX = "PRD";
    private static final int ID_DIGITS = 4; // PRD + 4 digit = 7 karakter

    private final ProdukRepository produkRepository;
    private final StokGeraiRepository stokGeraiRepository;

    public ProdukService(
            ProdukRepository produkRepository,
            StokGeraiRepository stokGeraiRepository) {
        this.produkRepository = produkRepository;
        this.stokGeraiRepository = stokGeraiRepository;
    }

    @Transactional(readOnly = true)
    public List<ProdukResponseDTO> getAll(String search, String status) {
        Boolean statusFilter = parseStatus(status);
        String keyword = (search == null || search.isBlank())
                ? null
                : "%" + search.trim().toLowerCase(Locale.ROOT) + "%";

        Specification<Produk> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null) {
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("kodeProduk")), keyword),
                        cb.like(cb.lower(root.get("nama")), keyword)));
            }
            if (statusFilter != null) {
                predicates.add(cb.equal(root.get("statusAktif"), statusFilter));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return produkRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "nama"))
                .stream()
                .map(ProdukResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProdukDetailResponseDTO getDetail(String id) {
        return buildDetail(getProdukById(id));
    }

    @Transactional
    public ProdukDetailResponseDTO create(ProdukCreateRequestDTO request) {
        String kode = request.getKodeProduk().trim();

        if (produkRepository.existsByKodeProdukIgnoreCase(kode)) {
            throw new DuplicateResourceException("Produk", "kodeProduk", kode);
        }
        validateHarga(request.getHargaNormal(), request.getHargaAnggota());

        request.setKodeProduk(kode);
        request.setNama(request.getNama().trim());

        Produk saved = produkRepository.save(request.toEntity(generateId()));
        return buildDetail(saved);
    }

    @Transactional
    public ProdukDetailResponseDTO update(String id, ProdukUpdateRequestDTO request) {
        Produk produk = getProdukById(id);

        if (!Boolean.TRUE.equals(produk.getStatusAktif())) {
            throw new InactiveResourceException(
                    "Produk " + produk.getKodeProduk() + " sudah nonaktif sehingga tidak dapat diubah");
        }
        validateHarga(request.getHargaNormal(), request.getHargaAnggota());

        request.setNama(request.getNama().trim());
        request.applyTo(produk);

        return buildDetail(produkRepository.save(produk));
    }

    @Transactional
    public ProdukResponseDTO nonaktifkan(String id) {
        Produk produk = getProdukById(id);

        if (!Boolean.TRUE.equals(produk.getStatusAktif())) {
            throw new InactiveResourceException("Produk", produk.getKodeProduk());
        }

        produk.setStatusAktif(false);
        return ProdukResponseDTO.fromEntity(produkRepository.save(produk));
    }


    @Transactional(readOnly = true)
    public Produk getProdukById(String id) {
        return produkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produk", id));
    }

    @Transactional(readOnly = true)
    public Produk getProdukAktifById(String id) {
        Produk produk = getProdukById(id);
        if (!Boolean.TRUE.equals(produk.getStatusAktif())) {
            throw new InactiveResourceException("Produk", produk.getKodeProduk());
        }
        return produk;
    }

    private ProdukDetailResponseDTO buildDetail(Produk produk) {
        List<StokGeraiResponseDTO> daftarStok = stokGeraiRepository
                .findByProduk_IdOrderByGerai_NamaAsc(produk.getId())
                .stream()
                .map(StokGeraiResponseDTO::fromEntity)
                .toList();

        return ProdukDetailResponseDTO.fromEntity(produk, daftarStok);
    }

    private void validateHarga(Long hargaNormal, Long hargaAnggota) {
        if (hargaNormal == null || hargaNormal <= 0) {
            throw new BadRequestException("Harga normal harus lebih dari 0");
        }
        if (hargaAnggota == null || hargaAnggota <= 0) {
            throw new BadRequestException("Harga anggota harus lebih dari 0");
        }
        if (hargaAnggota > hargaNormal) {
            throw new BadRequestException("Harga anggota tidak boleh melebihi harga normal");
        }
    }

    private String generateId() {
        int next = produkRepository.findTopByOrderByIdDesc()
                .map(Produk::getId)
                .map(this::extractNumber)
                .orElse(0) + 1;

        int max = (int) Math.pow(10, ID_DIGITS) - 1;
        if (next > max) {
            throw new IllegalStateException("Kapasitas ID Produk sudah penuh");
        }
        return ID_PREFIX + String.format("%0" + ID_DIGITS + "d", next);
    }

    private int extractNumber(String id) {
        try {
            return Integer.parseInt(id.substring(ID_PREFIX.length()));
        } catch (RuntimeException e) {
            return (int) produkRepository.count();
        }
    }

    private Boolean parseStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }
        String value = status.trim().toUpperCase(Locale.ROOT);
        return switch (value) {
            case "AKTIF", "TRUE" -> true;
            case "NONAKTIF", "FALSE" -> false;
            default -> throw new BadRequestException(
                    "Status tidak valid: " + status + ". Gunakan AKTIF atau NONAKTIF");
        };
    }
}