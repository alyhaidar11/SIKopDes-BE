package apap.ti._6.sikopdes2406425805be.repositories;

import apap.ti._6.sikopdes2406425805be.models.MutasiStok;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MutasiStokRepository
        extends JpaRepository<MutasiStok, String>, JpaSpecificationExecutor<MutasiStok> {

    List<MutasiStok> findByGerai_Id(String geraiId);

    List<MutasiStok> findByProduk_Id(String produkId);

    List<MutasiStok> findByReferensiId(String referensiId);

    List<MutasiStok> findByGerai_IdAndProduk_Id(String geraiId, String produkId);

    Optional<MutasiStok> findTopByOrderByIdDesc();

    @Override
    @EntityGraph(attributePaths = {"gerai", "produk"})
    List<MutasiStok> findAll(Specification<MutasiStok> spec, Sort sort);
}