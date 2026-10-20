package apap.ti._6.sikopdes2406425805be.dto.penjualan;

import java.time.LocalDateTime;
import java.util.List;

import apap.ti._6.sikopdes2406425805be.models.Anggota;
import apap.ti._6.sikopdes2406425805be.models.TransaksiPenjualan;
import apap.ti._6.sikopdes2406425805be.models.TransaksiPenjualan.StatusTransaksi;

public class PenjualanDetailResponseDTO {

    private String id;
    private LocalDateTime tanggalTransaksi;
    private StatusTransaksi status;

    private String kodeGerai;
    private String namaGerai;

    private String namaAnggota;
    private String nomorAnggota;

    private Long total;

    private LocalDateTime tanggalPembatalan;
    private String alasanPembatalan;

    private List<DetailTransaksiResponseDTO> detailTransaksi;

    public PenjualanDetailResponseDTO() {
    }

    public PenjualanDetailResponseDTO(
            String id,
            LocalDateTime tanggalTransaksi,
            StatusTransaksi status,
            String kodeGerai,
            String namaGerai,
            String namaAnggota,
            String nomorAnggota,
            Long total,
            LocalDateTime tanggalPembatalan,
            String alasanPembatalan,
            List<DetailTransaksiResponseDTO> detailTransaksi) {
        this.id = id;
        this.tanggalTransaksi = tanggalTransaksi;
        this.status = status;
        this.kodeGerai = kodeGerai;
        this.namaGerai = namaGerai;
        this.namaAnggota = namaAnggota;
        this.nomorAnggota = nomorAnggota;
        this.total = total;
        this.tanggalPembatalan = tanggalPembatalan;
        this.alasanPembatalan = alasanPembatalan;
        this.detailTransaksi = detailTransaksi;
    }

    public static PenjualanDetailResponseDTO fromEntity(
            TransaksiPenjualan transaksi,
            List<DetailTransaksiResponseDTO> detailTransaksi) {
        Anggota anggota = transaksi.getAnggota();

        return new PenjualanDetailResponseDTO(
                transaksi.getId(),
                transaksi.getTanggalTransaksi(),
                transaksi.getStatus(),
                transaksi.getGerai().getKodeGerai(),
                transaksi.getGerai().getNama(),
                anggota != null ? anggota.getNama() : null,
                anggota != null ? anggota.getNomorAnggota() : null,
                transaksi.getTotal(),
                transaksi.getTanggalPembatalan(),
                transaksi.getAlasanPembatalan(),
                detailTransaksi);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(LocalDateTime tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public StatusTransaksi getStatus() {
        return status;
    }

    public void setStatus(StatusTransaksi status) {
        this.status = status;
    }

    public String getKodeGerai() {
        return kodeGerai;
    }

    public void setKodeGerai(String kodeGerai) {
        this.kodeGerai = kodeGerai;
    }

    public String getNamaGerai() {
        return namaGerai;
    }

    public void setNamaGerai(String namaGerai) {
        this.namaGerai = namaGerai;
    }

    public String getNamaAnggota() {
        return namaAnggota;
    }

    public void setNamaAnggota(String namaAnggota) {
        this.namaAnggota = namaAnggota;
    }

    public String getNomorAnggota() {
        return nomorAnggota;
    }

    public void setNomorAnggota(String nomorAnggota) {
        this.nomorAnggota = nomorAnggota;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public LocalDateTime getTanggalPembatalan() {
        return tanggalPembatalan;
    }

    public void setTanggalPembatalan(LocalDateTime tanggalPembatalan) {
        this.tanggalPembatalan = tanggalPembatalan;
    }

    public String getAlasanPembatalan() {
        return alasanPembatalan;
    }

    public void setAlasanPembatalan(String alasanPembatalan) {
        this.alasanPembatalan = alasanPembatalan;
    }

    public List<DetailTransaksiResponseDTO> getDetailTransaksi() {
        return detailTransaksi;
    }

    public void setDetailTransaksi(List<DetailTransaksiResponseDTO> detailTransaksi) {
        this.detailTransaksi = detailTransaksi;
    }
}