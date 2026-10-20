
package apap.ti._6.sikopdes2406425805be.repositories;

import apap.ti._6.sikopdes2406425805be.models.TransaksiPenjualan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaksiPenjualanRepository
        extends JpaRepository<TransaksiPenjualan, String> {

    List<TransaksiPenjualan> findByGerai_Id(String geraiId);

    List<TransaksiPenjualan> findByAnggota_Id(String anggotaId);

    List<TransaksiPenjualan> findByStatus(
            TransaksiPenjualan.StatusTransaksi status
    );

    List<TransaksiPenjualan> findByGerai_IdAndStatus(
            String geraiId,
            TransaksiPenjualan.StatusTransaksi status
    );
}
