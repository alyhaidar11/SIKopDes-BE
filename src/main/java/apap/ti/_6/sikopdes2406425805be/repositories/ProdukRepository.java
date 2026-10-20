package apap.ti._6.sikopdes2406425805be.repositories;

import apap.ti._6.sikopdes2406425805be.models.Produk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdukRepository
        extends JpaRepository<Produk, String>, JpaSpecificationExecutor<Produk> {

    Optional<Produk> findByKodeProduk(String kodeProduk);

    boolean existsByKodeProduk(String kodeProduk);

    boolean existsByKodeProdukIgnoreCase(String kodeProduk);

    List<Produk> findByStatusAktif(Boolean statusAktif);

    List<Produk> findByNamaContainingIgnoreCase(String nama);

    Optional<Produk> findTopByOrderByIdDesc();

    long countByStatusAktifTrue(); 
}