package apap.ti._6.sikopdes2406425805be.dto.produk;

import java.time.LocalDateTime;
import java.util.List;

import apap.ti._6.sikopdes2406425805be.dto.stok.StokGeraiResponseDTO;
import apap.ti._6.sikopdes2406425805be.models.Produk;

public class ProdukDetailResponseDTO {

    private String id;
    private String kodeProduk;
    private String nama;
    private Long hargaNormal;
    private Long hargaAnggota;
    private boolean statusAktif;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<StokGeraiResponseDTO> daftarStok;

    public ProdukDetailResponseDTO() {
    }

    public ProdukDetailResponseDTO(
            String id,
            String kodeProduk,
            String nama,
            Long hargaNormal,
            Long hargaAnggota,
            boolean statusAktif,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<StokGeraiResponseDTO> daftarStok) {
        this.id = id;
        this.kodeProduk = kodeProduk;
        this.nama = nama;
        this.hargaNormal = hargaNormal;
        this.hargaAnggota = hargaAnggota;
        this.statusAktif = statusAktif;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.daftarStok = daftarStok;
    }

    public static ProdukDetailResponseDTO fromEntity(
            Produk produk,
            List<StokGeraiResponseDTO> daftarStok) {
        return new ProdukDetailResponseDTO(
                produk.getId(),
                produk.getKodeProduk(),
                produk.getNama(),
                produk.getHargaNormal(),
                produk.getHargaAnggota(),
                Boolean.TRUE.equals(produk.getStatusAktif()),
                produk.getCreatedAt(),
                produk.getUpdatedAt(),
                daftarStok);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
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

    public boolean isStatusAktif() {
        return statusAktif;
    }

    public void setStatusAktif(boolean statusAktif) {
        this.statusAktif = statusAktif;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<StokGeraiResponseDTO> getDaftarStok() {
        return daftarStok;
    }

    public void setDaftarStok(List<StokGeraiResponseDTO> daftarStok) {
        this.daftarStok = daftarStok;
    }
}