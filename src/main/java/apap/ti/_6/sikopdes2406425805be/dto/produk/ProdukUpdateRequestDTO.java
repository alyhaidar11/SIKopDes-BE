package apap.ti._6.sikopdes2406425805be.dto.produk;

import apap.ti._6.sikopdes2406425805be.models.Produk;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProdukUpdateRequestDTO {

    @NotBlank(message = "Nama Produk wajib diisi")
    private String nama;

    @NotNull(message = "Harga normal wajib diisi")
    @Positive(message = "Harga normal harus lebih dari 0")
    private Long hargaNormal;

    @NotNull(message = "Harga anggota wajib diisi")
    @Positive(message = "Harga anggota harus lebih dari 0")
    private Long hargaAnggota;

    public ProdukUpdateRequestDTO() {
    }

    public ProdukUpdateRequestDTO(
            String nama,
            Long hargaNormal,
            Long hargaAnggota) {
        this.nama = nama;
        this.hargaNormal = hargaNormal;
        this.hargaAnggota = hargaAnggota;
    }

    @AssertTrue(message = "Harga anggota tidak boleh melebihi harga normal")
    private boolean isHargaAnggotaValid() {
        if (hargaNormal == null || hargaAnggota == null) {
            return true;
        }
        return hargaAnggota <= hargaNormal;
    }

    public void applyTo(Produk produk) {
        produk.setNama(this.nama);
        produk.setHargaNormal(this.hargaNormal);
        produk.setHargaAnggota(this.hargaAnggota);
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Long getHargaNormal() {
        return hargaNormal;
    }

    public void setHargaNormal(Long hargaNormal) {
        this.hargaNormal = hargaNormal;
    }

    public Long getHargaAnggota() {
        return hargaAnggota;
    }

    public void setHargaAnggota(Long hargaAnggota) {
        this.hargaAnggota = hargaAnggota;
    }
}