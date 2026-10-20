package apap.ti._6.sikopdes2406425805be.dto.gerai;

import apap.ti._6.sikopdes2406425805be.models.Gerai;

public class GeraiResponseDTO {

    private String id;
    private String kodeGerai;
    private String nama;
    private String alamat;
    private boolean statusAktif;

    public GeraiResponseDTO() {
    }

    public GeraiResponseDTO(
            String id,
            String kodeGerai,
            String nama,
            String alamat,
            boolean statusAktif) {
        this.id = id;
        this.kodeGerai = kodeGerai;
        this.nama = nama;
        this.alamat = alamat;
        this.statusAktif = statusAktif;
    }

    public static GeraiResponseDTO fromEntity(Gerai gerai) {
        return new GeraiResponseDTO(
                gerai.getId(),
                gerai.getKodeGerai(),
                gerai.getNama(),
                gerai.getAlamat(),
                Boolean.TRUE.equals(gerai.getStatusAktif()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKodeGerai() {
        return kodeGerai;
    }

    public void setKodeGerai(String kodeGerai) {
        this.kodeGerai = kodeGerai;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public boolean isStatusAktif() {
        return statusAktif;
    }

    public void setStatusAktif(boolean statusAktif) {
        this.statusAktif = statusAktif;
    }
}