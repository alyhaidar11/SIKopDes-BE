package apap.ti._6.sikopdes2406425805be.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "gerai",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_gerai_kode_gerai",
            columnNames = "kodeGerai"
        )
    }
)
public class Gerai {

    @Id
    @Column(length = 7, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, unique = true, updatable = false)
    private String kodeGerai;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false)
    private String alamat;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Boolean statusAktif;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Gerai() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKodeGerai() {
        return kodeGerai;
    }

    public void setKodeGerai(String kodeGerai) {
        this.kodeGerai = kodeGerai;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

