package apap.ti._6.sikopdes2406425805be.models;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaksi_penjualan")
public class TransaksiPenjualan {

    @Id
    @Column(length = 8, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "geraiId",
        nullable = false,
        updatable = false
    )
    private Gerai gerai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anggotaId")
    private Anggota anggota;

    @Column(nullable = false, updatable = false)
    private LocalDateTime tanggalTransaksi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTransaksi status;

    @Column(nullable = false)
    private Long total;

    @Column
    private LocalDateTime tanggalPembatalan;

    @Column(length = 500)
    private String alasanPembatalan;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public TransaksiPenjualan() {
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        this.tanggalTransaksi = now;
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum StatusTransaksi {
        SELESAI,
        DIBATALKAN
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Gerai getGerai() {
        return gerai;
    }

    public void setGerai(Gerai gerai) {
        this.gerai = gerai;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }

    public LocalDateTime getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public StatusTransaksi getStatus() {
        return status;
    }

    public void setStatus(StatusTransaksi status) {
        this.status = status;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public LocalDateTime getTanggalPembatalan() {
        return tanggalPembatalan;
    }

    public void setTanggalPembatalan(LocalDateTime tanggalPembatalan) {
        this.tanggalPembatalan = tanggalPembatalan;
    }

    public String getAlasanPembatalan() {
        return alasanPembatalan;
    }

    public void setAlasanPembatalan(String alasanPembatalan) {
        this.alasanPembatalan = alasanPembatalan;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
