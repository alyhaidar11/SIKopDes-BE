
package apap.ti._6.sikopdes2406425805be.repositories;

import apap.ti._6.sikopdes2406425805be.models.MutasiStok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MutasiStokRepository
        extends JpaRepository<MutasiStok, String> {

    List<MutasiStok> findByGerai_Id(String geraiId);

    List<MutasiStok> findByProduk_Id(String produkId);

    List<MutasiStok> findByReferensiId(String referensiId);

    List<MutasiStok> findByGerai_IdAndProduk_Id(
            String geraiId,
            String produkId
    );
}
