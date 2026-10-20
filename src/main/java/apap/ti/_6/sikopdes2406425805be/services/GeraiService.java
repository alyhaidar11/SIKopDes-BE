package apap.ti._6.sikopdes2406425805be.services;

import apap.ti._6.sikopdes2406425805be.dto.gerai.GeraiCreateRequestDTO;
import apap.ti._6.sikopdes2406425805be.dto.gerai.GeraiDetailResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.gerai.GeraiResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.gerai.GeraiUpdateRequestDTO;
import apap.ti._6.sikopdes2406425805be.dto.penjualan.PenjualanResponseDTO;
import apap.ti._6.sikopdes2406425805be.exceptions.BadRequestException;
import apap.ti._6.sikopdes2406425805be.exceptions.DuplicateResourceException;
import apap.ti._6.sikopdes2406425805be.exceptions.InactiveResourceException;
import apap.ti._6.sikopdes2406425805be.exceptions.ResourceNotFoundException;
import apap.ti._6.sikopdes2406425805be.models.Gerai;
import apap.ti._6.sikopdes2406425805be.repositories.GeraiRepository;
import apap.ti._6.sikopdes2406425805be.repositories.StokGeraiRepository;
import apap.ti._6.sikopdes2406425805be.repositories.TransaksiPenjualanRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class GeraiService {

    private static final String ID_PREFIX = "GRI";
    private static final int ID_DIGITS = 4;

    private final GeraiRepository geraiRepository;
    private final StokGeraiRepository stokGeraiRepository;
    private final TransaksiPenjualanRepository transaksiPenjualanRepository;

    public GeraiService(
            GeraiRepository geraiRepository,
            StokGeraiRepository stokGeraiRepository,
            TransaksiPenjualanRepository transaksiPenjualanRepository) {
        this.geraiRepository = geraiRepository;
        this.stokGeraiRepository = stokGeraiRepository;
        this.transaksiPenjualanRepository = transaksiPenjualanRepository;
    }

    @Transactional(readOnly = true)
    public List<GeraiResponseDTO> getAll(String search, String status) {
        Boolean statusFilter = parseStatus(status);
        String keyword = (search == null || search.isBlank())
                ? null
                : "%" + search.trim().toLowerCase(Locale.ROOT) + "%";

        Specification<Gerai> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null) {
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("kodeGerai")), keyword),
                        cb.like(cb.lower(root.get("nama")), keyword)));
            }
            if (statusFilter != null) {
                predicates.add(cb.equal(root.get("statusAktif"), statusFilter));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return geraiRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "nama"))
                .stream()
                .map(GeraiResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public GeraiDetailResponseDTO getDetail(String id) {
        Gerai gerai = getGeraiById(id);
        return buildDetail(gerai);
    }

    @Transactional
    public GeraiDetailResponseDTO create(GeraiCreateRequestDTO request) {
        String kode = request.getKodeGerai().trim();

        if (geraiRepository.existsByKodeGeraiIgnoreCase(kode)) {
            throw new DuplicateResourceException("Gerai", "kodeGerai", kode);
        }

        request.setKodeGerai(kode);
        request.setNama(request.getNama().trim());
        request.setAlamat(request.getAlamat().trim());

        Gerai saved = geraiRepository.save(request.toEntity(generateId()));
        return buildDetail(saved);
    }

    @Transactional
    public GeraiDetailResponseDTO update(String id, GeraiUpdateRequestDTO request) {
        Gerai gerai = getGeraiById(id);

        if (!Boolean.TRUE.equals(gerai.getStatusAktif())) {
            throw new InactiveResourceException(
                    "Gerai " + gerai.getKodeGerai() + " sudah nonaktif sehingga tidak dapat diubah");
        }

        request.setNama(request.getNama().trim());
        request.setAlamat(request.getAlamat().trim());
        request.applyTo(gerai);

        Gerai saved = geraiRepository.save(gerai);
        return buildDetail(saved);
    }

    @Transactional
    public GeraiResponseDTO nonaktifkan(String id) {
        Gerai gerai = getGeraiById(id);

        if (!Boolean.TRUE.equals(gerai.getStatusAktif())) {
            throw new InactiveResourceException("Gerai", gerai.getKodeGerai());
        }

        gerai.setStatusAktif(false);
        return GeraiResponseDTO.fromEntity(geraiRepository.save(gerai));
    }


    @Transactional(readOnly = true)
    public Gerai getGeraiById(String id) {
        return geraiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gerai", id));
    }

    @Transactional(readOnly = true)
    public Gerai getGeraiAktifById(String id) {
        Gerai gerai = getGeraiById(id);
        if (!Boolean.TRUE.equals(gerai.getStatusAktif())) {
            throw new InactiveResourceException("Gerai", gerai.getKodeGerai());
        }
        return gerai;
    }

    private GeraiDetailResponseDTO buildDetail(Gerai gerai) {
        long jumlahRecordStok = stokGeraiRepository.countByGerai_Id(gerai.getId());
        long jumlahProdukDenganStok =
                stokGeraiRepository.countByGerai_IdAndJumlahGreaterThan(gerai.getId(), 0);

        List<PenjualanResponseDTO> transaksiTerbaru = transaksiPenjualanRepository
                .findTop5ByGerai_IdOrderByTanggalTransaksiDescIdDesc(gerai.getId())
                .stream()
                .map(PenjualanResponseDTO::fromEntity)
                .toList();

        return GeraiDetailResponseDTO.fromEntity(
                gerai, jumlahRecordStok, jumlahProdukDenganStok, transaksiTerbaru);
    }

    private String generateId() {
        int next = geraiRepository.findTopByOrderByIdDesc()
                .map(Gerai::getId)
                .map(this::extractNumber)
                .orElse(0) + 1;

        int max = (int) Math.pow(10, ID_DIGITS) - 1;
        if (next > max) {
            throw new IllegalStateException("Kapasitas ID Gerai sudah penuh");
        }
        return ID_PREFIX + String.format("%0" + ID_DIGITS + "d", next);
    }

    private int extractNumber(String id) {
        try {
            return Integer.parseInt(id.substring(ID_PREFIX.length()));
        } catch (RuntimeException e) {
            return (int) geraiRepository.count();
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