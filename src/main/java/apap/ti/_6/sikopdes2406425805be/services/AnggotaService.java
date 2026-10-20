package apap.ti._6.sikopdes2406425805be.services;

import apap.ti._6.sikopdes2406425805be.dto.anggota.AnggotaResponseDTO;
import apap.ti._6.sikopdes2406425805be.exceptions.BadRequestException;
import apap.ti._6.sikopdes2406425805be.exceptions.InactiveResourceException;
import apap.ti._6.sikopdes2406425805be.exceptions.ResourceNotFoundException;
import apap.ti._6.sikopdes2406425805be.models.Anggota;
import apap.ti._6.sikopdes2406425805be.models.Anggota.StatusKeanggotaan;
import apap.ti._6.sikopdes2406425805be.repositories.AnggotaRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class AnggotaService {

    private final AnggotaRepository anggotaRepository;

    public AnggotaService(AnggotaRepository anggotaRepository) {
        this.anggotaRepository = anggotaRepository;
    }

    @Transactional(readOnly = true)
    public List<AnggotaResponseDTO> getAll(String search, String status) {
        StatusKeanggotaan statusFilter = parseStatus(status);
        String keyword = (search == null || search.isBlank())
                ? null
                : "%" + search.trim().toLowerCase(Locale.ROOT) + "%";

        Specification<Anggota> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null) {
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("nomorAnggota")), keyword),
                        cb.like(cb.lower(root.get("nama")), keyword)));
            }
            if (statusFilter != null) {
                predicates.add(cb.equal(root.get("statusKeanggotaan"), statusFilter));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return anggotaRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "nama"))
                .stream()
                .map(AnggotaResponseDTO::fromEntity)
                .toList();
    }

    /**
     * Method internal untuk PenjualanService saat checkout.
     * - Tidak ditemukan -> ResourceNotFoundException (404)
     * - TIDAK_AKTIF     -> InactiveResourceException (409)
     */
    @Transactional(readOnly = true)
    public Anggota getAnggotaForCheckout(String anggotaId) {
        Anggota anggota = anggotaRepository.findById(anggotaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Anggota dengan id " + anggotaId + " tidak ditemukan. "
                        + "Jika sesuai, proses transaksi sebagai pembeli umum tanpa memilih Anggota"));

        if (anggota.getStatusKeanggotaan() != StatusKeanggotaan.AKTIF) {
            throw new InactiveResourceException(
                    "Anggota " + anggota.getNomorAnggota() + " berstatus TIDAK_AKTIF "
                    + "sehingga tidak dapat digunakan pada transaksi. "
                    + "Jika sesuai, proses transaksi sebagai pembeli umum tanpa memilih Anggota");
        }
        return anggota;
    }

    private StatusKeanggotaan parseStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }
        try {
            return StatusKeanggotaan.valueOf(status.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(
                    "Status keanggotaan tidak valid: " + status + ". Gunakan AKTIF atau TIDAK_AKTIF");
        }
    }
}