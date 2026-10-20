package apap.ti._6.sikopdes2406425805be.controllers;

import apap.ti._6.sikopdes2406425805be.dto.penjualan.PembatalanPenjualanRequestDTO;
import apap.ti._6.sikopdes2406425805be.dto.penjualan.PenjualanCreateRequestDTO;
import apap.ti._6.sikopdes2406425805be.dto.penjualan.PenjualanDetailResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.penjualan.PenjualanResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.statistik.PenjualanStatistikResponseDTO;
import apap.ti._6.sikopdes2406425805be.services.PenjualanService;
import apap.ti._6.sikopdes2406425805be.services.StatistikService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/penjualan")
public class PenjualanController {

    private final PenjualanService penjualanService;
    private final StatistikService statistikService;

    public PenjualanController(
            PenjualanService penjualanService,
            StatistikService statistikService) {
        this.penjualanService = penjualanService;
        this.statistikService = statistikService;
    }

    @GetMapping
    public ResponseEntity<List<PenjualanResponseDTO>> getAll(
            @RequestParam(required = false) String geraiId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long minTotal,
            @RequestParam(required = false) Long maxTotal,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(penjualanService.getAll(
                geraiId, status, startDate, endDate, minTotal, maxTotal, search));
    }

    @GetMapping("/statistik")
    public ResponseEntity<PenjualanStatistikResponseDTO> getStatistik(
            @RequestParam(required = false) String period,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String geraiId) {
        return ResponseEntity.ok(statistikService.getStatistik(period, year, geraiId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PenjualanDetailResponseDTO> getDetail(@PathVariable String id) {
        return ResponseEntity.ok(penjualanService.getDetail(id));
    }

    @PostMapping
    public ResponseEntity<PenjualanDetailResponseDTO> checkout(
            @Valid @RequestBody PenjualanCreateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(penjualanService.checkout(request));
    }

    @PutMapping("/{id}/batal")
    public ResponseEntity<PenjualanDetailResponseDTO> batal(
            @PathVariable String id,
            @Valid @RequestBody PembatalanPenjualanRequestDTO request) {
        return ResponseEntity.ok(penjualanService.batal(id, request));
    }
}