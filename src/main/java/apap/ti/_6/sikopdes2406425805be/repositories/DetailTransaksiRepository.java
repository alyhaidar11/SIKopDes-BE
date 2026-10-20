package apap.ti._6.sikopdes2406425805be.repositories;

import apap.ti._6.sikopdes2406425805be.models.DetailTransaksi;
import apap.ti._6.sikopdes2406425805be.models.TransaksiPenjualan;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DetailTransaksiRepository
        extends JpaRepository<DetailTransaksi, String> {

    List<DetailTransaksi> findByTransaksi_Id(String transaksiId);

    List<DetailTransaksi> findByProduk_Id(String produkId);

    @EntityGraph(attributePaths = "produk")
    List<DetailTransaksi> findByTransaksi_IdOrderByNamaProdukAsc(String transaksiId);

    Optional<DetailTransaksi> findTopByOrderByIdDesc();

    @Query("""
        select coalesce(sum(d.jumlah), 0L) from DetailTransaksi d
        where d.transaksi.status = :status
          and d.transaksi.tanggalTransaksi >= :start
          and d.transaksi.tanggalTransaksi < :end
          and (cast(:geraiId as string) is null or d.transaksi.gerai.id = :geraiId)
        """)
long sumJumlahByPeriode(
        @Param("status") TransaksiPenjualan.StatusTransaksi status,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        @Param("geraiId") String geraiId);

@Query("""
        select d.produk.id, max(d.namaProduk), sum(d.jumlah)
        from DetailTransaksi d
        where d.transaksi.status = :status
          and d.transaksi.tanggalTransaksi >= :start
          and d.transaksi.tanggalTransaksi < :end
          and (cast(:geraiId as string) is null or d.transaksi.gerai.id = :geraiId)
        group by d.produk.id
        order by sum(d.jumlah) desc, max(d.namaProduk) asc
        """)
List<Object[]> findProdukTerlaris(
        @Param("status") TransaksiPenjualan.StatusTransaksi status,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        @Param("geraiId") String geraiId,
        Pageable pageable);
}