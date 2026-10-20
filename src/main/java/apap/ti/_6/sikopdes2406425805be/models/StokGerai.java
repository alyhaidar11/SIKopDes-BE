package apap.ti._6.sikopdes2406425805be.models;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "stok_gerai",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_stok_gerai_produk",
            columnNames = {"geraiId", "produkId"}
        )
    }
)
public class StokGerai {

    @Id
    @Column(length = 7, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "geraiId", nullable = false)
    private Gerai gerai;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produkId", nullable = false)
    private Produk produk;

    @Column(nullable = false)
    private Integer jumlah;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Constructor kosong, dibutuhkan oleh JPA
    public StokGerai() {
    }

    // Mengisi timestamp ketika data pertama kali disimpan
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    // Memperbarui updatedAt ketika data diubah
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
