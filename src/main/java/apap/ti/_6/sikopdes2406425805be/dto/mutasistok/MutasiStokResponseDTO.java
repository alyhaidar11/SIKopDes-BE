package apap.ti._6.sikopdes2406425805be.dto.mutasistok;

import java.time.LocalDateTime;

import apap.ti._6.sikopdes2406425805be.models.MutasiStok;
import apap.ti._6.sikopdes2406425805be.models.MutasiStok.JenisMutasi;
import apap.ti._6.sikopdes2406425805be.models.MutasiStok.SumberMutasi;

public class MutasiStokResponseDTO {

    private String id;
    private LocalDateTime tanggalMutasi;

    private String kodeGerai;
    private String namaGerai;

    private String kodeProduk;
    private String namaProduk;

    private JenisMutasi jenis;
    private Integer jumlah;
    private Integer stokSebelum;
    private Integer stokSesudah;

    private SumberMutasi sumber;
    private String referensiId;
    private String keterangan;

    public MutasiStokResponseDTO() {
    }

    public MutasiStokResponseDTO(
            String id,
            LocalDateTime tanggalMutasi,
            String kodeGerai,
            String namaGerai,
            String kodeProduk,
            String namaProduk,
            JenisMutasi jenis,
            Integer jumlah,
            Integer stokSebelum,
            Integer stokSesudah,
            SumberMutasi sumber,
            String referensiId,
            String keterangan) {
        this.id = id;
        this.tanggalMutasi = tanggalMutasi;
        this.kodeGerai = kodeGerai;
        this.namaGerai = namaGerai;
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.jenis = jenis;
        this.jumlah = jumlah;
        this.stokSebelum = stokSebelum;
        this.stokSesudah = stokSesudah;
        this.sumber = sumber;
        this.referensiId = referensiId;
        this.keterangan = keterangan;
    }

    // Mapper dari entity ke DTO.
    // Relasi gerai dan produk bersifat LAZY, jadi dipanggil di dalam transaksi
    // (@Transactional) atau gunakan JOIN FETCH di repository.
    public static MutasiStokResponseDTO fromEntity(MutasiStok mutasi) {
        return new MutasiStokResponseDTO(
                mutasi.getId(),
                mutasi.getTanggalMutasi(),
                mutasi.getGerai().getKodeGerai(),
                mutasi.getGerai().getNama(),
                mutasi.getProduk().getKodeProduk(),
                mutasi.getProduk().getNama(),
                mutasi.getJenis(),
                mutasi.getJumlah(),
                mutasi.getStokSebelum(),
                mutasi.getStokSesudah(),
                mutasi.getSumber(),
                mutasi.getReferensiId(),
                mutasi.getKeterangan());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTanggalMutasi() {
        return tanggalMutasi;
    }

    public void setTanggalMutasi(LocalDateTime tanggalMutasi) {
        this.tanggalMutasi = tanggalMutasi;
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

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public JenisMutasi getJenis() {
        return jenis;
    }

    public void setJenis(JenisMutasi jenis) {
        this.jenis = jenis;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getStokSebelum() {
        return stokSebelum;
    }

    public void setStokSebelum(Integer stokSebelum) {
        this.stokSebelum = stokSebelum;
    }

    public Integer getStokSesudah() {
        return stokSesudah;
    }

    public void setStokSesudah(Integer stokSesudah) {
        this.stokSesudah = stokSesudah;
    }

    public SumberMutasi getSumber() {
        return sumber;
    }

    public void setSumber(SumberMutasi sumber) {
        this.sumber = sumber;
    }

    public String getReferensiId() {
        return referensiId;
    }

    public void setReferensiId(String referensiId) {
        this.referensiId = referensiId;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}