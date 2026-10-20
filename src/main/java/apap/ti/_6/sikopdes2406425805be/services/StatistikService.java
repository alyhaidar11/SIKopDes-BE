package apap.ti._6.sikopdes2406425805be.services;

import apap.ti._6.sikopdes2406425805be.dto.statistik.OmzetPeriodeResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.statistik.PenjualanStatistikResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.statistik.ProdukTerlarisResponseDTO;
import apap.ti._6.sikopdes2406425805be.exceptions.BadRequestException;
import apap.ti._6.sikopdes2406425805be.models.TransaksiPenjualan.StatusTransaksi;
import apap.ti._6.sikopdes2406425805be.repositories.DetailTransaksiRepository;
import apap.ti._6.sikopdes2406425805be.repositories.TransaksiPenjualanRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class StatistikService {

    private static final int MIN_YEAR = 2000;
    private static final int MAX_YEAR = 2100;
    private static final int TOP_LIMIT = 5;
    private static final String[] NAMA_BULAN = {
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
    };

    private final TransaksiPenjualanRepository transaksiPenjualanRepository;
    private final DetailTransaksiRepository detailTransaksiRepository;
    private final GeraiService geraiService;

    public StatistikService(
            TransaksiPenjualanRepository transaksiPenjualanRepository,
            DetailTransaksiRepository detailTransaksiRepository,
            GeraiService geraiService) {
        this.transaksiPenjualanRepository = transaksiPenjualanRepository;
        this.detailTransaksiRepository = detailTransaksiRepository;
        this.geraiService = geraiService;
    }

    @Transactional(readOnly = true)
    public PenjualanStatistikResponseDTO getStatistik(String period, Integer year, String geraiId) {
        boolean bulanan = parsePeriod(period);
        int tahun = resolveYear(year);
        String geraiFilter = resolveGeraiId(geraiId);

        LocalDateTime start = LocalDate.of(tahun, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(tahun + 1, 1, 1).atStartOfDay();
        StatusTransaksi status = StatusTransaksi.SELESAI;

        long jumlahTransaksi = transaksiPenjualanRepository.countByPeriode(status, start, end, geraiFilter);
        long totalOmzet = transaksiPenjualanRepository.sumTotalByPeriode(status, start, end, geraiFilter);
        long totalUnit = detailTransaksiRepository.sumJumlahByPeriode(status, start, end, geraiFilter);

        List<ProdukTerlarisResponseDTO> limaProdukTerlaris = detailTransaksiRepository
                .findProdukTerlaris(status, start, end, geraiFilter, PageRequest.of(0, TOP_LIMIT))
                .stream()
                .map(row -> new ProdukTerlarisResponseDTO(
                        (String) row[0],
                        (String) row[1],
                        ((Number) row[2]).longValue()))
                .toList();

        ProdukTerlarisResponseDTO produkTerlaris =
                limaProdukTerlaris.isEmpty() ? null : limaProdukTerlaris.get(0);

        long[] omzetPerBulan = new long[12];
        for (Object[] row : transaksiPenjualanRepository.sumTotalPerBulan(status, start, end, geraiFilter)) {
            int bulan = ((Number) row[0]).intValue();
            omzetPerBulan[bulan - 1] = ((Number) row[1]).longValue();
        }

        List<OmzetPeriodeResponseDTO> omzetPeriode = bulanan
                ? buildBulanan(omzetPerBulan)
                : buildTriwulanan(omzetPerBulan);

        return new PenjualanStatistikResponseDTO(
                jumlahTransaksi,
                totalOmzet,
                totalUnit,
                produkTerlaris,
                limaProdukTerlaris,
                omzetPeriode);
    }

    private List<OmzetPeriodeResponseDTO> buildBulanan(long[] omzetPerBulan) {
        List<OmzetPeriodeResponseDTO> hasil = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            hasil.add(new OmzetPeriodeResponseDTO(NAMA_BULAN[i], i + 1, omzetPerBulan[i]));
        }
        return hasil;
    }

    private List<OmzetPeriodeResponseDTO> buildTriwulanan(long[] omzetPerBulan) {
        long[] perTriwulan = new long[4];
        for (int i = 0; i < 12; i++) {
            perTriwulan[i / 3] += omzetPerBulan[i];
        }
        List<OmzetPeriodeResponseDTO> hasil = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            hasil.add(new OmzetPeriodeResponseDTO("Triwulan " + (i + 1), i + 1, perTriwulan[i]));
        }
        return hasil;
    }

    private boolean parsePeriod(String period) {
        if (period == null || period.isBlank()) {
            return true;
        }
        return switch (period.trim().toUpperCase(Locale.ROOT)) {
            case "BULANAN" -> true;
            case "TRIWULANAN" -> false;
            default -> throw new BadRequestException(
                    "Period tidak valid: " + period + ". Gunakan BULANAN atau TRIWULANAN");
        };
    }

    private int resolveYear(Integer year) {
        if (year == null) {
            return Year.now().getValue();
        }
        if (year < MIN_YEAR || year > MAX_YEAR) {
            throw new BadRequestException(
                    "Year tidak valid: " + year + ". Gunakan tahun antara " + MIN_YEAR + " dan " + MAX_YEAR);
        }
        return year;
    }

    private String resolveGeraiId(String geraiId) {
        if (geraiId == null || geraiId.isBlank()) {
            return null;
        }
        return geraiService.getGeraiById(geraiId.trim()).getId();
    }
}