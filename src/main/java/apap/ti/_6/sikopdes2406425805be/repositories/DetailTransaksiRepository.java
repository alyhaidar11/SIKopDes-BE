
package apap.ti._6.sikopdes2406425805be.repositories;

import apap.ti._6.sikopdes2406425805be.models.DetailTransaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailTransaksiRepository
        extends JpaRepository<DetailTransaksi, String> {

    List<DetailTransaksi> findByTransaksi_Id(String transaksiId);

    List<DetailTransaksi> findByProduk_Id(String produkId);
}
