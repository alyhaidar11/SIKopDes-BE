package apap.ti._6.sikopdes2406425805be.controllers;

import apap.ti._6.sikopdes2406425805be.dto.mutasistok.MutasiStokCreateRequestDTO;
import apap.ti._6.sikopdes2406425805be.dto.mutasistok.MutasiStokResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.stok.StokGeraiResponseDTO;
import apap.ti._6.sikopdes2406425805be.services.StokService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class StokController {

    private final StokService stokService;

    public StokController(StokService stokService) {
        this.stokService = stokService;
    }

    @GetMapping("/api/stok-gerai")
    public ResponseEntity<List<StokGeraiResponseDTO>> getPersediaan(
            @RequestParam(required = false) String geraiId,
            @RequestParam(required = false) String produkId,
            @RequestParam(required = false) String sort) {
        return ResponseEntity.ok(stokService.getPersediaan(geraiId, produkId, sort));
    }

    @GetMapping("/api/mutasi-stok")
    public ResponseEntity<List<MutasiStokResponseDTO>> getRiwayatMutasi(
            @RequestParam(required = false) String geraiId,
            @RequestParam(required = false) String produkId,
            @RequestParam(required = false) String jenis,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(
                stokService.getRiwayatMutasi(geraiId, produkId, jenis, startDate, endDate));
    }

    @PostMapping("/api/mutasi-stok")
    public ResponseEntity<MutasiStokResponseDTO> catatMutasi(
            @Valid @RequestBody MutasiStokCreateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stokService.catatMutasi(request));
    }
}