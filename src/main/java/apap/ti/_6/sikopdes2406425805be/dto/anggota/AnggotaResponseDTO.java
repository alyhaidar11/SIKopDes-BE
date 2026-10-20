package apap.ti._6.sikopdes2406425805be.dto.anggota;

import apap.ti._6.sikopdes2406425805be.models.Anggota;
import apap.ti._6.sikopdes2406425805be.models.Anggota.StatusKeanggotaan;

public class AnggotaResponseDTO {

    private String id;
    private String nomorAnggota;
    private String nama;
    private String email;
    private String telepon;
    private StatusKeanggotaan statusKeanggotaan;

    public AnggotaResponseDTO() {
    }

    public AnggotaResponseDTO(
            String id,
            String nomorAnggota,
            String nama,
            String email,
            String telepon,
            StatusKeanggotaan statusKeanggotaan) {
        this.id = id;
        this.nomorAnggota = nomorAnggota;
        this.nama = nama;
        this.email = email;
        this.telepon = telepon;
        this.statusKeanggotaan = statusKeanggotaan;
    }
    
    public static AnggotaResponseDTO fromEntity(Anggota anggota) {
        return new AnggotaResponseDTO(
                anggota.getId(),
                anggota.getNomorAnggota(),
                anggota.getNama(),
                anggota.getEmail(),
                anggota.getTelepon(),
                anggota.getStatusKeanggotaan());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomorAnggota() {
        return nomorAnggota;
    }

    public void setNomorAnggota(String nomorAnggota) {
        this.nomorAnggota = nomorAnggota;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public StatusKeanggotaan getStatusKeanggotaan() {
        return statusKeanggotaan;
    }

    public void setStatusKeanggotaan(StatusKeanggotaan statusKeanggotaan) {
        this.statusKeanggotaan = statusKeanggotaan;
    }
}