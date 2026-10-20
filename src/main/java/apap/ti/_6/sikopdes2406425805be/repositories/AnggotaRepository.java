
package apap.ti._6.sikopdes2406425805be.repositories;

import apap.ti._6.sikopdes2406425805be.models.Anggota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnggotaRepository extends JpaRepository<Anggota, String> {

    Optional<Anggota> findByNomorAnggota(String nomorAnggota);

    boolean existsByNomorAnggota(String nomorAnggota);
}
