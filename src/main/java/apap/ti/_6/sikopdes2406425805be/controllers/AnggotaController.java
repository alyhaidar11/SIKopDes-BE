package apap.ti._6.sikopdes2406425805be.controllers;

import apap.ti._6.sikopdes2406425805be.dto.anggota.AnggotaResponseDTO;
import apap.ti._6.sikopdes2406425805be.services.AnggotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/anggota")
public class AnggotaController {

    private final AnggotaService anggotaService;

    public AnggotaController(AnggotaService anggotaService) {
        this.anggotaService = anggotaService;
    }

    @GetMapping
    public ResponseEntity<List<AnggotaResponseDTO>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(anggotaService.getAll(search, status));
    }
}