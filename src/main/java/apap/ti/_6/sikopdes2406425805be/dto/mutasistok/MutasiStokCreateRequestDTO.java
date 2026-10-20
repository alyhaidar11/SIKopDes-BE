package apap.ti._6.sikopdes2406425805be.dto.mutasistok;

import apap.ti._6.sikopdes2406425805be.models.MutasiStok.JenisMutasi;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MutasiStokCreateRequestDTO {

    @NotNull(message = "Jenis mutasi wajib diisi")
    private JenisMutasi jenis;

    @NotBlank(message = "Gerai wajib dipilih")
    private String geraiId;

    @NotBlank(message = "Produk wajib dipilih")
    private String produkId;

    private Integer jumlah;

    private Integer stokFisikAktual;

    @Size(max = 500, message = "Keterangan maksimal 500 karakter")
    private String keterangan;

    public MutasiStokCreateRequestDTO() {
    }

    public MutasiStokCreateRequestDTO(
            JenisMutasi jenis,
            String geraiId,
            String produkId,
            Integer jumlah,
            Integer stokFisikAktual,
            String keterangan) {
        this.jenis = jenis;
        this.geraiId = geraiId;
        this.produkId = produkId;
        this.jumlah = jumlah;
        this.stokFisikAktual = stokFisikAktual;
        this.keterangan = keterangan;
    }

    @AssertTrue(message = "Jumlah wajib diisi dan harus lebih dari 0")
    private boolean isJumlahValid() {
        if (jenis == JenisMutasi.MASUK || jenis == JenisMutasi.KELUAR) {
            return jumlah != null && jumlah > 0;
        }
        return true;
    }

    @AssertTrue(message = "Stok fisik aktual wajib diisi dan minimal 0")
    private boolean isStokFisikAktualValid() {
        if (jenis == JenisMutasi.PENYESUAIAN) {
            return stokFisikAktual != null && stokFisikAktual >= 0;
        }
        return true;
    }

    @AssertTrue(message = "Keterangan wajib diisi untuk mutasi KELUAR dan PENYESUAIAN")
    private boolean isKeteranganValid() {
        if (jenis == JenisMutasi.KELUAR || jenis == JenisMutasi.PENYESUAIAN) {
            return keterangan != null && !keterangan.isBlank();
        }
        return true;
    }

    public JenisMutasi getJenis() {
        return jenis;
    }

    public void setJenis(JenisMutasi jenis) {
        this.jenis = jenis;
    }

    public String getGeraiId() {
        return geraiId;
    }

    public void setGeraiId(String geraiId) {
        this.geraiId = geraiId;
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

    public Integer getStokFisikAktual() {
        return stokFisikAktual;
    }

    public void setStokFisikAktual(Integer stokFisikAktual) {
        this.stokFisikAktual = stokFisikAktual;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}