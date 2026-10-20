
package apap.ti._6.sikopdes2406425805be.repositories;

import apap.ti._6.sikopdes2406425805be.models.Gerai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeraiRepository extends JpaRepository<Gerai, String> {

    Optional<Gerai> findByKodeGerai(String kodeGerai);

    boolean existsByKodeGerai(String kodeGerai);
}
