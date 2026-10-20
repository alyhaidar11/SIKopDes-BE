
package apap.ti._6.sikopdes2406425805be.repositories;

import apap.ti._6.sikopdes2406425805be.models.Produk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ProdukRepository extends JpaRepository<Produk, String> {

    Optional<Produk> findByKodeProduk(String kodeProduk);

    boolean existsByKodeProduk(String kodeProduk);

    List<Produk> findByStatusAktif(Boolean statusAktif);

    List<Produk> findByNamaContainingIgnoreCase(String nama);
}
