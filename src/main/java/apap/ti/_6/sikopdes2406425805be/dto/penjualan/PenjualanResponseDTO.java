package apap.ti._6.sikopdes2406425805be.dto.penjualan;

import java.time.LocalDateTime;

import apap.ti._6.sikopdes2406425805be.models.Anggota;
import apap.ti._6.sikopdes2406425805be.models.TransaksiPenjualan;
import apap.ti._6.sikopdes2406425805be.models.TransaksiPenjualan.StatusTransaksi;

public class PenjualanResponseDTO {

    private String id;
    private String kodeGerai;
    private String namaGerai;
    private String namaAnggota;
    private String nomorAnggota;
    private LocalDateTime tanggalTransaksi;
    private Long total;
    private StatusTransaksi status;

    public PenjualanResponseDTO() {
    }

    public PenjualanResponseDTO(
            String id,
            String kodeGerai,
            String namaGerai,
            String namaAnggota,
            String nomorAnggota,
            LocalDateTime tanggalTransaksi,
            Long total,
            StatusTransaksi status) {
        this.id = id;
        this.kodeGerai = kodeGerai;
        this.namaGerai = namaGerai;
        this.namaAnggota = namaAnggota;
        this.nomorAnggota = nomorAnggota;
        this.tanggalTransaksi = tanggalTransaksi;
        this.total = total;
        this.status = status;
    }

    // Mapper dari entity ke DTO.
    // Relasi gerai dan anggota bersifat LAZY, jadi panggil di dalam transaksi
    // (@Transactional) atau gunakan JOIN FETCH / LEFT JOIN FETCH di repository.
    public static PenjualanResponseDTO fromEntity(TransaksiPenjualan transaksi) {
        Anggota anggota = transaksi.getAnggota();

        return new PenjualanResponseDTO(
                transaksi.getId(),
                transaksi.getGerai().getKodeGerai(),
                transaksi.getGerai().getNama(),
                anggota != null ? anggota.getNama() : null,
                anggota != null ? anggota.getNomorAnggota() : null,
                transaksi.getTanggalTransaksi(),
                transaksi.getTotal(),
                transaksi.getStatus());
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

    public LocalDateTime getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(LocalDateTime tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public StatusTransaksi getStatus() {
        return status;
    }

    public void setStatus(StatusTransaksi status) {
        this.status = status;
    }
}