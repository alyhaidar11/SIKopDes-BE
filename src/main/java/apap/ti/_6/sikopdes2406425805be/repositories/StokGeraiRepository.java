
package apap.ti._6.sikopdes2406425805be.repositories;

import apap.ti._6.sikopdes2406425805be.models.StokGerai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StokGeraiRepository
        extends JpaRepository<StokGerai, String> {

    List<StokGerai> findByGerai_Id(String geraiId);

    List<StokGerai> findByProduk_Id(String produkId);

    Optional<StokGerai> findByGerai_IdAndProduk_Id(
            String geraiId,
            String produkId
    );

    boolean existsByGerai_IdAndProduk_Id(
            String geraiId,
            String produkId
    );
}
