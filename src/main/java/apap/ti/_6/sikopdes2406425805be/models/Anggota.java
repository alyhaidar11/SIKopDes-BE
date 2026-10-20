package apap.ti._6.sikopdes2406425805be.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "anggota",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_anggota_nomor_anggota",
            columnNames = "nomorAnggota"
        )
    }
)
public class Anggota {

    @Id
    @Column(length = 7, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String nomorAnggota;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telepon;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusKeanggotaan statusKeanggotaan;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Anggota() {
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum StatusKeanggotaan {
        AKTIF,
        TIDAK_AKTIF
    }

    // Getters dan setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomorAnggota() {
        return nomorAnggota;
    }

    public void setNomorAnggota(String nomorAnggota) {
        this.nomorAnggota = nomorAnggota;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public StatusKeanggotaan getStatusKeanggotaan() {
        return statusKeanggotaan;
    }

    public void setStatusKeanggotaan(
            StatusKeanggotaan statusKeanggotaan) {
        this.statusKeanggotaan = statusKeanggotaan;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
