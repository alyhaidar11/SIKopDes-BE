package apap.ti._6.sikopdes2406425805be.repositories;

import apap.ti._6.sikopdes2406425805be.models.TransaksiPenjualan;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransaksiPenjualanRepository
        extends JpaRepository<TransaksiPenjualan, String>, JpaSpecificationExecutor<TransaksiPenjualan> {

    List<TransaksiPenjualan> findByGerai_Id(String geraiId);

    List<TransaksiPenjualan> findByAnggota_Id(String anggotaId);

    List<TransaksiPenjualan> findByStatus(TransaksiPenjualan.StatusTransaksi status);

    List<TransaksiPenjualan> findByGerai_IdAndStatus(
            String geraiId,
            TransaksiPenjualan.StatusTransaksi status);

    @EntityGraph(attributePaths = {"gerai", "anggota"})
    List<TransaksiPenjualan> findTop5ByGerai_IdOrderByTanggalTransaksiDescIdDesc(String geraiId);

    Optional<TransaksiPenjualan> findTopByOrderByIdDesc();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<TransaksiPenjualan> findWithLockById(String id);

    @Override
    @EntityGraph(attributePaths = {"gerai", "anggota"})
    List<TransaksiPenjualan> findAll(Specification<TransaksiPenjualan> spec, Sort sort);

    @Query("""
        select count(t) from TransaksiPenjualan t
        where t.status = :status
          and t.tanggalTransaksi >= :start
          and t.tanggalTransaksi < :end
          and (cast(:geraiId as string) is null or t.gerai.id = :geraiId)
        """)
long countByPeriode(
        @Param("status") TransaksiPenjualan.StatusTransaksi status,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        @Param("geraiId") String geraiId);

@Query("""
        select coalesce(sum(t.total), 0L) from TransaksiPenjualan t
        where t.status = :status
        and t.tanggalTransaksi >= :start
        and t.tanggalTransaksi < :end
        and (cast(:geraiId as string) is null or t.gerai.id = :geraiId)
        """)
long sumTotalByPeriode(
        @Param("status") TransaksiPenjualan.StatusTransaksi status,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        @Param("geraiId") String geraiId);

@Query("""
        select month(t.tanggalTransaksi), coalesce(sum(t.total), 0L)
        from TransaksiPenjualan t
        where t.status = :status
        and t.tanggalTransaksi >= :start
        and t.tanggalTransaksi < :end
        and (cast(:geraiId as string) is null or t.gerai.id = :geraiId)
        group by month(t.tanggalTransaksi)
        """)
List<Object[]> sumTotalPerBulan(
        @Param("status") TransaksiPenjualan.StatusTransaksi status,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        @Param("geraiId") String geraiId);
}