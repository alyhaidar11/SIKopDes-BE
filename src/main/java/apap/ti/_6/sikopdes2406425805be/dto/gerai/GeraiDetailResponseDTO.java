package apap.ti._6.sikopdes2406425805be.dto.gerai;

import java.time.LocalDateTime;
import java.util.List;

import apap.ti._6.sikopdes2406425805be.dto.penjualan.PenjualanResponseDTO;
import apap.ti._6.sikopdes2406425805be.models.Gerai;

public class GeraiDetailResponseDTO {

    // Seluruh informasi Gerai
    private String id;
    private String kodeGerai;
    private String nama;
    private String alamat;
    private Double latitude;
    private Double longitude;
    private boolean statusAktif;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Ringkasan persediaan
    private long jumlahRecordStok;
    private long jumlahProdukDenganStok;

    // Maksimal lima transaksi terbaru
    private List<PenjualanResponseDTO> transaksiTerbaru;

    public GeraiDetailResponseDTO() {
    }

    public GeraiDetailResponseDTO(
            String id,
            String kodeGerai,
            String nama,
            String alamat,
            Double latitude,
            Double longitude,
            boolean statusAktif,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            long jumlahRecordStok,
            long jumlahProdukDenganStok,
            List<PenjualanResponseDTO> transaksiTerbaru) {
        this.id = id;
        this.kodeGerai = kodeGerai;
        this.nama = nama;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.statusAktif = statusAktif;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.jumlahRecordStok = jumlahRecordStok;
        this.jumlahProdukDenganStok = jumlahProdukDenganStok;
        this.transaksiTerbaru = transaksiTerbaru;
    }

    // Mapper dari entity ke DTO, data ringkasan dihitung di service
    public static GeraiDetailResponseDTO fromEntity(
            Gerai gerai,
            long jumlahRecordStok,
            long jumlahProdukDenganStok,
            List<PenjualanResponseDTO> transaksiTerbaru) {
        return new GeraiDetailResponseDTO(
                gerai.getId(),
                gerai.getKodeGerai(),
                gerai.getNama(),
                gerai.getAlamat(),
                gerai.getLatitude(),
                gerai.getLongitude(),
                Boolean.TRUE.equals(gerai.getStatusAktif()),
                gerai.getCreatedAt(),
                gerai.getUpdatedAt(),
                jumlahRecordStok,
                jumlahProdukDenganStok,
                transaksiTerbaru);
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

    public boolean isStatusAktif() {
        return statusAktif;
    }

    public void setStatusAktif(boolean statusAktif) {
        this.statusAktif = statusAktif;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getJumlahRecordStok() {
        return jumlahRecordStok;
    }

    public void setJumlahRecordStok(long jumlahRecordStok) {
        this.jumlahRecordStok = jumlahRecordStok;
    }

    public long getJumlahProdukDenganStok() {
        return jumlahProdukDenganStok;
    }

    public void setJumlahProdukDenganStok(long jumlahProdukDenganStok) {
        this.jumlahProdukDenganStok = jumlahProdukDenganStok;
    }

    public List<PenjualanResponseDTO> getTransaksiTerbaru() {
        return transaksiTerbaru;
    }

    public void setTransaksiTerbaru(List<PenjualanResponseDTO> transaksiTerbaru) {
        this.transaksiTerbaru = transaksiTerbaru;
    }
}