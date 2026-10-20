package apap.ti._6.sikopdes2406425805be.models;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "produk",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_produk_kode_produk",
            columnNames = "kodeProduk"
        )
    }
)
public class Produk {

    @Id
    @Column(length = 7, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, unique = true, updatable = false)
    private String kodeProduk;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false)
    private Long hargaNormal;

    @Column(nullable = false)
    private Long hargaAnggota;

    @Column(nullable = false)
    private Boolean statusAktif;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Constructor kosong, dibutuhkan oleh JPA
    public Produk() {
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

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Long getHargaNormal() {
        return hargaNormal;
    }

    public void setHargaNormal(Long hargaNormal) {
        this.hargaNormal = hargaNormal;
    }

    public Long getHargaAnggota() {
        return hargaAnggota;
    }

    public void setHargaAnggota(Long hargaAnggota) {
        this.hargaAnggota = hargaAnggota;
    }

    public Boolean getStatusAktif() {
        return statusAktif;
    }

    public void setStatusAktif(Boolean statusAktif) {
        this.statusAktif = statusAktif;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
