package apap.ti._6.sikopdes2406425805be.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mutasi_stok")
public class MutasiStok {

    @Id
    @Column(length = 7, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "geraiId", nullable = false, updatable = false)
    private Gerai gerai;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produkId", nullable = false, updatable = false)
    private Produk produk;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private JenisMutasi jenis;

    @Column(nullable = false, updatable = false)
    private Integer jumlah;

    @Column(nullable = false, updatable = false)
    private Integer stokSebelum;

    @Column(nullable = false, updatable = false)
    private Integer stokSesudah;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private SumberMutasi sumber;

    @Column(updatable = false)
    private String referensiId;

    @Column(length = 500, updatable = false)
    private String keterangan;

    @Column(nullable = false, updatable = false)
    private LocalDateTime tanggalMutasi;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructor kosong, dibutuhkan oleh JPA
    public MutasiStok() {
    }

    // Mengisi timestamp otomatis ketika data dibuat
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.tanggalMutasi = now;
        this.createdAt = now;
    }

    // Enum untuk jenis mutasi
    public enum JenisMutasi {
        MASUK,
        KELUAR,
        PENYESUAIAN
    }

    // Enum untuk sumber mutasi
    public enum SumberMutasi {
        MANUAL,
        PENJUALAN,
        PEMBATALAN_PENJUALAN
    }

    // Getters dan setters
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

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public JenisMutasi getJenis() {
        return jenis;
    }

    public void setJenis(JenisMutasi jenis) {
        this.jenis = jenis;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getStokSebelum() {
        return stokSebelum;
    }

    public void setStokSebelum(Integer stokSebelum) {
        this.stokSebelum = stokSebelum;
    }

    public Integer getStokSesudah() {
        return stokSesudah;
    }

    public void setStokSesudah(Integer stokSesudah) {
        this.stokSesudah = stokSesudah;
    }

    public SumberMutasi getSumber() {
        return sumber;
    }

    public void setSumber(SumberMutasi sumber) {
        this.sumber = sumber;
    }

    public String getReferensiId() {
        return referensiId;
    }

    public void setReferensiId(String referensiId) {
        this.referensiId = referensiId;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public LocalDateTime getTanggalMutasi() {
        return tanggalMutasi;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

