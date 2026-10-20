package apap.ti._6.sikopdes2406425805be.dto.stok;

import apap.ti._6.sikopdes2406425805be.models.StokGerai;

import java.time.LocalDateTime;

public class StokGeraiResponseDTO {

    private String id;
    private String geraiId;
    private String kodeGerai;
    private String namaGerai;
    private String produkId;
    private String kodeProduk;
    private String namaProduk;
    private Integer jumlah;
    private LocalDateTime updatedAt;

    public StokGeraiResponseDTO() {
    }

    public StokGeraiResponseDTO(
            String id,
            String geraiId,
            String kodeGerai,
            String namaGerai,
            String produkId,
            String kodeProduk,
            String namaProduk,
            Integer jumlah,
            LocalDateTime updatedAt) {
        this.id = id;
        this.geraiId = geraiId;
        this.kodeGerai = kodeGerai;
        this.namaGerai = namaGerai;
        this.produkId = produkId;
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.jumlah = jumlah;
        this.updatedAt = updatedAt;
    }

    public static StokGeraiResponseDTO fromEntity(StokGerai stok) {
        return new StokGeraiResponseDTO(
                stok.getId(),
                stok.getGerai().getId(),
                stok.getGerai().getKodeGerai(),
                stok.getGerai().getNama(),
                stok.getProduk().getId(),
                stok.getProduk().getKodeProduk(),
                stok.getProduk().getNama(),
                stok.getJumlah(),
                stok.getUpdatedAt());
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getGeraiId() { return geraiId; }
    public void setGeraiId(String geraiId) { this.geraiId = geraiId; }

    public String getKodeGerai() { return kodeGerai; }
    public void setKodeGerai(String kodeGerai) { this.kodeGerai = kodeGerai; }

    public String getNamaGerai() { return namaGerai; }
    public void setNamaGerai(String namaGerai) { this.namaGerai = namaGerai; }

    public String getProdukId() { return produkId; }
    public void setProdukId(String produkId) { this.produkId = produkId; }

    public String getKodeProduk() { return kodeProduk; }
    public void setKodeProduk(String kodeProduk) { this.kodeProduk = kodeProduk; }

    public String getNamaProduk() { return namaProduk; }
    public void setNamaProduk(String namaProduk) { this.namaProduk = namaProduk; }

    public Integer getJumlah() { return jumlah; }
    public void setJumlah(Integer jumlah) { this.jumlah = jumlah; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}