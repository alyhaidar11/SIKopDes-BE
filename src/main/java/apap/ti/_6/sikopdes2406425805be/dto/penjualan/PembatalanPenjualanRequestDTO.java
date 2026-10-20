package apap.ti._6.sikopdes2406425805be.dto.penjualan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PembatalanPenjualanRequestDTO {

    @NotBlank(message = "Alasan pembatalan wajib diisi")
    @Size(max = 500, message = "Alasan pembatalan maksimal 500 karakter")
    private String alasanPembatalan;

    public PembatalanPenjualanRequestDTO() {
    }

    public PembatalanPenjualanRequestDTO(String alasanPembatalan) {
        this.alasanPembatalan = alasanPembatalan;
    }

    public String getAlasanPembatalan() {
        return alasanPembatalan;
    }

    public void setAlasanPembatalan(String alasanPembatalan) {
        this.alasanPembatalan = alasanPembatalan;
    }
}