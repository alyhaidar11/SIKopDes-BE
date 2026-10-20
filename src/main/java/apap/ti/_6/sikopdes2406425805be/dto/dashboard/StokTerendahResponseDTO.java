package apap.ti._6.sikopdes2406425805be.dto.dashboard;

import apap.ti._6.sikopdes2406425805be.models.StokGerai;

public class StokTerendahResponseDTO {

    private String namaGerai;
    private String kodeProduk;
    private String namaProduk;
    private Integer jumlah;

    public StokTerendahResponseDTO() {
    }

    public StokTerendahResponseDTO(
            String namaGerai,
            String kodeProduk,
            String namaProduk,
            Integer jumlah) {
        this.namaGerai = namaGerai;
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.jumlah = jumlah;
    }

    public static StokTerendahResponseDTO fromEntity(StokGerai stok) {
        return new StokTerendahResponseDTO(
                stok.getGerai().getNama(),
                stok.getProduk().getKodeProduk(),
                stok.getProduk().getNama(),
                stok.getJumlah());
    }

    public String getNamaGerai() {
        return namaGerai;
    }

    public void setNamaGerai(String namaGerai) {
        this.namaGerai = namaGerai;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
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
}