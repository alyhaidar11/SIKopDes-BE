package apap.ti._6.sikopdes2406425805be.services;

import apap.ti._6.sikopdes2406425805be.dto.dashboard.DashboardResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.dashboard.StokTerendahResponseDTO;
import apap.ti._6.sikopdes2406425805be.models.TransaksiPenjualan.StatusTransaksi;
import apap.ti._6.sikopdes2406425805be.repositories.GeraiRepository;
import apap.ti._6.sikopdes2406425805be.repositories.ProdukRepository;
import apap.ti._6.sikopdes2406425805be.repositories.StokGeraiRepository;
import apap.ti._6.sikopdes2406425805be.repositories.TransaksiPenjualanRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    private static final int STOK_TERENDAH_LIMIT = 5;

    private final GeraiRepository geraiRepository;
    private final ProdukRepository produkRepository;
    private final TransaksiPenjualanRepository transaksiPenjualanRepository;
    private final StokGeraiRepository stokGeraiRepository;

    public DashboardService(
            GeraiRepository geraiRepository,
            ProdukRepository produkRepository,
            TransaksiPenjualanRepository transaksiPenjualanRepository,
            StokGeraiRepository stokGeraiRepository) {
        this.geraiRepository = geraiRepository;
        this.produkRepository = produkRepository;
        this.transaksiPenjualanRepository = transaksiPenjualanRepository;
        this.stokGeraiRepository = stokGeraiRepository;
    }

    @Transactional(readOnly = true)
    public DashboardResponseDTO getDashboard() {
        LocalDate hariIni = LocalDate.now();
        LocalDateTime awalBulan = hariIni.withDayOfMonth(1).atStartOfDay();
        LocalDateTime awalBulanDepan = awalBulan.plusMonths(1);
        StatusTransaksi status = StatusTransaksi.SELESAI;

        long jumlahGeraiAktif = geraiRepository.countByStatusAktifTrue();
        long jumlahProdukAktif = produkRepository.countByStatusAktifTrue();
        long jumlahTransaksi = transaksiPenjualanRepository
                .countByPeriode(status, awalBulan, awalBulanDepan, null);
        long totalOmzet = transaksiPenjualanRepository
                .sumTotalByPeriode(status, awalBulan, awalBulanDepan, null);

        List<StokTerendahResponseDTO> stokTerendah = stokGeraiRepository
                .findStokTerendah(PageRequest.of(0, STOK_TERENDAH_LIMIT))
                .stream()
                .map(StokTerendahResponseDTO::fromEntity)
                .toList();

        return new DashboardResponseDTO(
                jumlahGeraiAktif,
                jumlahProdukAktif,
                jumlahTransaksi,
                totalOmzet,
                stokTerendah);
    }
}