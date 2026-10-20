package apap.ti._6.sikopdes2406425805be.dto.produk;

import apap.ti._6.sikopdes2406425805be.models.Produk;

public class ProdukResponseDTO {

    private String id;
    private String kodeProduk;
    private String nama;
    private Long hargaNormal;
    private Long hargaAnggota;
    private boolean statusAktif;

    public ProdukResponseDTO() {
    }

    public ProdukResponseDTO(
            String id,
            String kodeProduk,
            String nama,
            Long hargaNormal,
            Long hargaAnggota,
            boolean statusAktif) {
        this.id = id;
        this.kodeProduk = kodeProduk;
        this.nama = nama;
        this.hargaNormal = hargaNormal;
        this.hargaAnggota = hargaAnggota;
        this.statusAktif = statusAktif;
    }

    public static ProdukResponseDTO fromEntity(Produk produk) {
        return new ProdukResponseDTO(
                produk.getId(),
                produk.getKodeProduk(),
                produk.getNama(),
                produk.getHargaNormal(),
                produk.getHargaAnggota(),
                Boolean.TRUE.equals(produk.getStatusAktif()));
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
}