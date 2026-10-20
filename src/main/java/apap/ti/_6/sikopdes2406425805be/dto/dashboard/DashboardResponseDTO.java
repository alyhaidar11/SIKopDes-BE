package apap.ti._6.sikopdes2406425805be.dto.dashboard;

import java.util.List;

public class DashboardResponseDTO {

    private long jumlahGeraiAktif;
    private long jumlahProdukAktif;
    private long jumlahTransaksiBulanIni;
    private long totalOmzetBulanIni;
    private List<StokTerendahResponseDTO> stokTerendah;

    public DashboardResponseDTO() {
    }

    public DashboardResponseDTO(
            long jumlahGeraiAktif,
            long jumlahProdukAktif,
            long jumlahTransaksiBulanIni,
            long totalOmzetBulanIni,
            List<StokTerendahResponseDTO> stokTerendah) {
        this.jumlahGeraiAktif = jumlahGeraiAktif;
        this.jumlahProdukAktif = jumlahProdukAktif;
        this.jumlahTransaksiBulanIni = jumlahTransaksiBulanIni;
        this.totalOmzetBulanIni = totalOmzetBulanIni;
        this.stokTerendah = stokTerendah;
    }

    public long getJumlahGeraiAktif() {
        return jumlahGeraiAktif;
    }

    public void setJumlahGeraiAktif(long jumlahGeraiAktif) {
        this.jumlahGeraiAktif = jumlahGeraiAktif;
    }

    public long getJumlahProdukAktif() {
        return jumlahProdukAktif;
    }

    public void setJumlahProdukAktif(long jumlahProdukAktif) {
        this.jumlahProdukAktif = jumlahProdukAktif;
    }

    public long getJumlahTransaksiBulanIni() {
        return jumlahTransaksiBulanIni;
    }

    public void setJumlahTransaksiBulanIni(long jumlahTransaksiBulanIni) {
        this.jumlahTransaksiBulanIni = jumlahTransaksiBulanIni;
    }

    public long getTotalOmzetBulanIni() {
        return totalOmzetBulanIni;
    }

    public void setTotalOmzetBulanIni(long totalOmzetBulanIni) {
        this.totalOmzetBulanIni = totalOmzetBulanIni;
    }

    public List<StokTerendahResponseDTO> getStokTerendah() {
        return stokTerendah;
    }

    public void setStokTerendah(List<StokTerendahResponseDTO> stokTerendah) {
        this.stokTerendah = stokTerendah;
    }
}