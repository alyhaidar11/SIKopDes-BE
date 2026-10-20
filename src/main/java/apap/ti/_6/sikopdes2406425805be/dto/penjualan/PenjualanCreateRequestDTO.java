package apap.ti._6.sikopdes2406425805be.dto.penjualan;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class PenjualanCreateRequestDTO {

    @NotBlank(message = "Gerai wajib dipilih")
    private String geraiId;

    // Opsional: null jika pembeli umum (bukan Anggota)
    private String anggotaId;

    // @Valid agar anotasi di PenjualanItemRequestDTO ikut divalidasi
    @NotEmpty(message = "Minimal satu item harus dipilih")
    @Valid
    private List<PenjualanItemRequestDTO> items;

    public PenjualanCreateRequestDTO() {
    }

    public PenjualanCreateRequestDTO(
            String geraiId,
            String anggotaId,
            List<PenjualanItemRequestDTO> items) {
        this.geraiId = geraiId;
        this.anggotaId = anggotaId;
        this.items = items;
    }

    public String getGeraiId() {
        return geraiId;
    }

    public void setGeraiId(String geraiId) {
        this.geraiId = geraiId;
    }

    public String getAnggotaId() {
        return anggotaId;
    }

    public void setAnggotaId(String anggotaId) {
        this.anggotaId = anggotaId;
    }

    public List<PenjualanItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<PenjualanItemRequestDTO> items) {
        this.items = items;
    }
}