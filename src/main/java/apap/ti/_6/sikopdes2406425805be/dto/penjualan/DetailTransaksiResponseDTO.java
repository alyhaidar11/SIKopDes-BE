package apap.ti._6.sikopdes2406425805be.dto.penjualan;

import apap.ti._6.sikopdes2406425805be.models.DetailTransaksi;
import apap.ti._6.sikopdes2406425805be.models.DetailTransaksi.JenisHarga;

public class DetailTransaksiResponseDTO {

    private String namaProduk;
    private Integer jumlah;
    private JenisHarga jenisHarga;
    private Long hargaSatuan;
    private Long subtotal;

    public DetailTransaksiResponseDTO() {
    }

    public DetailTransaksiResponseDTO(
            String namaProduk,
            Integer jumlah,
            JenisHarga jenisHarga,
            Long hargaSatuan,
            Long subtotal) {
        this.namaProduk = namaProduk;
        this.jumlah = jumlah;
        this.jenisHarga = jenisHarga;
        this.hargaSatuan = hargaSatuan;
        this.subtotal = subtotal;
    }

    public static DetailTransaksiResponseDTO fromEntity(DetailTransaksi detail) {
        return new DetailTransaksiResponseDTO(
                detail.getNamaProduk(),
                detail.getJumlah(),
                detail.getJenisHarga(),
                detail.getHargaSatuan(),
                detail.getSubtotal());
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public JenisHarga getJenisHarga() {
        return jenisHarga;
    }

    public void setJenisHarga(JenisHarga jenisHarga) {
        this.jenisHarga = jenisHarga;
    }

    public Long getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(Long hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }
}