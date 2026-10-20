package apap.ti._6.sikopdes2406425805be.dto.statistik;

public class OmzetPeriodeResponseDTO {

    private String periode;
    private int nomorPeriode;
    private long totalOmzet;

    public OmzetPeriodeResponseDTO() {
    }

    public OmzetPeriodeResponseDTO(
            String periode,
            int nomorPeriode,
            long totalOmzet) {
        this.periode = periode;
        this.nomorPeriode = nomorPeriode;
        this.totalOmzet = totalOmzet;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public int getNomorPeriode() {
        return nomorPeriode;
    }

    public void setNomorPeriode(int nomorPeriode) {
        this.nomorPeriode = nomorPeriode;
    }

    public long getTotalOmzet() {
        return totalOmzet;
    }

    public void setTotalOmzet(long totalOmzet) {
        this.totalOmzet = totalOmzet;
    }
}