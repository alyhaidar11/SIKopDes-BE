package apap.ti._6.sikopdes2406425805be.models;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "detail_transaksi")
public class DetailTransaksi {

    @Id
    @Column(length = 7, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "transaksiId",
        nullable = false,
        updatable = false
    )
    private TransaksiPenjualan transaksi;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "produkId",
        nullable = false,
        updatable = false
    )
    private Produk produk;

    @Column(nullable = false, updatable = false)
    private String namaProduk;

    @Column(nullable = false, updatable = false)
    private Integer jumlah;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private JenisHarga jenisHarga;

    @Column(nullable = false, updatable = false)
    private Long hargaSatuan;

    @Column(nullable = false, updatable = false)
    private Long subtotal;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public DetailTransaksi() {
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum JenisHarga {
        NORMAL,
        ANGGOTA
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransaksiPenjualan getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(TransaksiPenjualan transaksi) {
        this.transaksi = transaksi;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public JenisHarga getJenisHarga() {
        return jenisHarga;
    }

    public void setJenisHarga(JenisHarga jenisHarga) {
        this.jenisHarga = jenisHarga;
    }

    public Long getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(Long hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

