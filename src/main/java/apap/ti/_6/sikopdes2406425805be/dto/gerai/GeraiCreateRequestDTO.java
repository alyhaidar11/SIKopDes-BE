package apap.ti._6.sikopdes2406425805be.dto.gerai;

import apap.ti._6.sikopdes2406425805be.models.Gerai;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GeraiCreateRequestDTO {

    @NotBlank(message = "Kode Gerai wajib diisi")
    private String kodeGerai;

    @NotBlank(message = "Nama Gerai wajib diisi")
    private String nama;

    @NotBlank(message = "Alamat wajib diisi")
    private String alamat;

    @NotNull(message = "Latitude wajib diisi")
    @DecimalMin(value = "-90.0", message = "Latitude minimal -90")
    @DecimalMax(value = "90.0", message = "Latitude maksimal 90")
    private Double latitude;

    @NotNull(message = "Longitude wajib diisi")
    @DecimalMin(value = "-180.0", message = "Longitude minimal -180")
    @DecimalMax(value = "180.0", message = "Longitude maksimal 180")
    private Double longitude;

    public GeraiCreateRequestDTO() {
    }

    public GeraiCreateRequestDTO(
            String kodeGerai,
            String nama,
            String alamat,
            Double latitude,
            Double longitude) {
        this.kodeGerai = kodeGerai;
        this.nama = nama;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Mapper dari DTO ke entity. id dan statusAktif diisi di sini / di service
    public Gerai toEntity(String id) {
        Gerai gerai = new Gerai();
        gerai.setId(id);
        gerai.setKodeGerai(this.kodeGerai);
        gerai.setNama(this.nama);
        gerai.setAlamat(this.alamat);
        gerai.setLatitude(this.latitude);
        gerai.setLongitude(this.longitude);
        gerai.setStatusAktif(true);
        return gerai;
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
}