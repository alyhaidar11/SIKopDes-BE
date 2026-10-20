package apap.ti._6.sikopdes2406425805be.dto.penjualan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PenjualanItemRequestDTO {

    @NotBlank(message = "Produk wajib dipilih")
    private String produkId;

    @NotNull(message = "Jumlah wajib diisi")
    @Positive(message = "Jumlah harus lebih dari 0")
    private Integer jumlah;

    public PenjualanItemRequestDTO() {
    }

    public PenjualanItemRequestDTO(String produkId, Integer jumlah) {
        this.produkId = produkId;
        this.jumlah = jumlah;
    }

    public String getProdukId() {
        return produkId;
    }

    public void setProdukId(String produkId) {
        this.produkId = produkId;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }
}