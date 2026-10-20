package apap.ti._6.sikopdes2406425805be.dto.stok;

import apap.ti._6.sikopdes2406425805be.models.StokGerai;

public class StokGeraiResponseDTO {

    private String geraiId;
    private String kodeGerai;
    private String namaGerai;
    private Integer jumlah;

    public StokGeraiResponseDTO() {
    }

    public StokGeraiResponseDTO(
            String geraiId,
            String kodeGerai,
            String namaGerai,
            Integer jumlah) {
        this.geraiId = geraiId;
        this.kodeGerai = kodeGerai;
        this.namaGerai = namaGerai;
        this.jumlah = jumlah;
    }

    // Mapper dari entity ke DTO
    public static StokGeraiResponseDTO fromEntity(StokGerai stok) {
        return new StokGeraiResponseDTO(
                stok.getGerai().getId(),
                stok.getGerai().getKodeGerai(),
                stok.getGerai().getNama(),
                stok.getJumlah());
    }

    public String getGeraiId() {
        return geraiId;
    }

    public void setGeraiId(String geraiId) {
        this.geraiId = geraiId;
    }

    public String getKodeGerai() {
        return kodeGerai;
    }

    public void setKodeGerai(String kodeGerai) {
        this.kodeGerai = kodeGerai;
    }

    public String getNamaGerai() {
        return namaGerai;
    }

    public void setNamaGerai(String namaGerai) {
        this.namaGerai = namaGerai;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }
}