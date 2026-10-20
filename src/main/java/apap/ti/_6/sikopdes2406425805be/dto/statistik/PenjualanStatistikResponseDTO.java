package apap.ti._6.sikopdes2406425805be.dto.statistik;

import java.util.List;

public class PenjualanStatistikResponseDTO {

    private long jumlahTransaksi;
    private long totalOmzet;
    private long totalUnitTerjual;
    private ProdukTerlarisResponseDTO produkTerlaris;
    private List<ProdukTerlarisResponseDTO> limaProdukTerlaris;
    private List<OmzetPeriodeResponseDTO> omzetPeriode;

    public PenjualanStatistikResponseDTO() {
    }

    public PenjualanStatistikResponseDTO(
            long jumlahTransaksi,
            long totalOmzet,
            long totalUnitTerjual,
            ProdukTerlarisResponseDTO produkTerlaris,
            List<ProdukTerlarisResponseDTO> limaProdukTerlaris,
            List<OmzetPeriodeResponseDTO> omzetPeriode) {
        this.jumlahTransaksi = jumlahTransaksi;
        this.totalOmzet = totalOmzet;
        this.totalUnitTerjual = totalUnitTerjual;
        this.produkTerlaris = produkTerlaris;
        this.limaProdukTerlaris = limaProdukTerlaris;
        this.omzetPeriode = omzetPeriode;
    }

    public long getJumlahTransaksi() {
        return jumlahTransaksi;
    }

    public void setJumlahTransaksi(long jumlahTransaksi) {
        this.jumlahTransaksi = jumlahTransaksi;
    }

    public long getTotalOmzet() {
        return totalOmzet;
    }

    public void setTotalOmzet(long totalOmzet) {
        this.totalOmzet = totalOmzet;
    }

    public long getTotalUnitTerjual() {
        return totalUnitTerjual;
    }

    public void setTotalUnitTerjual(long totalUnitTerjual) {
        this.totalUnitTerjual = totalUnitTerjual;
    }

    public ProdukTerlarisResponseDTO getProdukTerlaris() {
        return produkTerlaris;
    }

    public void setProdukTerlaris(ProdukTerlarisResponseDTO produkTerlaris) {
        this.produkTerlaris = produkTerlaris;
    }

    public List<ProdukTerlarisResponseDTO> getLimaProdukTerlaris() {
        return limaProdukTerlaris;
    }

    public void setLimaProdukTerlaris(List<ProdukTerlarisResponseDTO> limaProdukTerlaris) {
        this.limaProdukTerlaris = limaProdukTerlaris;
    }

    public List<OmzetPeriodeResponseDTO> getOmzetPeriode() {
        return omzetPeriode;
    }

    public void setOmzetPeriode(List<OmzetPeriodeResponseDTO> omzetPeriode) {
        this.omzetPeriode = omzetPeriode;
    }
}