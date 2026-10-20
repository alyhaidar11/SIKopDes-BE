package apap.ti._6.sikopdes2406425805be.dto.statistik;

public class ProdukTerlarisResponseDTO {

    private String produkId;
    private String namaProduk;
    private long totalUnitTerjual;

    public ProdukTerlarisResponseDTO() {
    }

    public ProdukTerlarisResponseDTO(
            String produkId,
            String namaProduk,
            long totalUnitTerjual) {
        this.produkId = produkId;
        this.namaProduk = namaProduk;
        this.totalUnitTerjual = totalUnitTerjual;
    }

    public String getProdukId() {
        return produkId;
    }

    public void setProdukId(String produkId) {
        this.produkId = produkId;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public long getTotalUnitTerjual() {
        return totalUnitTerjual;
    }

    public void setTotalUnitTerjual(long totalUnitTerjual) {
        this.totalUnitTerjual = totalUnitTerjual;
    }
}