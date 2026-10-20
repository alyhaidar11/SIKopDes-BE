package apap.ti._6.sikopdes2406425805be.repositories;

import apap.ti._6.sikopdes2406425805be.models.StokGerai;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface StokGeraiRepository
        extends JpaRepository<StokGerai, String>, JpaSpecificationExecutor<StokGerai> {

    List<StokGerai> findByGerai_Id(String geraiId);

    List<StokGerai> findByProduk_Id(String produkId);

    @EntityGraph(attributePaths = {"gerai", "produk"})
    List<StokGerai> findByProduk_IdOrderByGerai_NamaAsc(String produkId);

    Optional<StokGerai> findByGerai_IdAndProduk_Id(String geraiId, String produkId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<StokGerai> findWithLockByGerai_IdAndProduk_Id(String geraiId, String produkId);

    boolean existsByGerai_IdAndProduk_Id(String geraiId, String produkId);

    long countByGerai_Id(String geraiId);

    long countByGerai_IdAndJumlahGreaterThan(String geraiId, Integer jumlah);

    Optional<StokGerai> findTopByOrderByIdDesc();

    @Override
    @EntityGraph(attributePaths = {"gerai", "produk"})
    List<StokGerai> findAll(Specification<StokGerai> spec, Sort sort);

    @Query("""
        select s from StokGerai s
        join fetch s.gerai g
        join fetch s.produk p
        order by s.jumlah asc, p.nama asc, g.nama asc
        """)
List<StokGerai> findStokTerendah(Pageable pageable);
}