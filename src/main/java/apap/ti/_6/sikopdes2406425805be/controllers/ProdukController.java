package apap.ti._6.sikopdes2406425805be.controllers;

import apap.ti._6.sikopdes2406425805be.dto.produk.ProdukCreateRequestDTO;
import apap.ti._6.sikopdes2406425805be.dto.produk.ProdukDetailResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.produk.ProdukResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.produk.ProdukUpdateRequestDTO;
import apap.ti._6.sikopdes2406425805be.services.ProdukService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/produk")
public class ProdukController {

    private final ProdukService produkService;

    public ProdukController(ProdukService produkService) {
        this.produkService = produkService;
    }

    @GetMapping
    public ResponseEntity<List<ProdukResponseDTO>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(produkService.getAll(search, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdukDetailResponseDTO> getDetail(@PathVariable String id) {
        return ResponseEntity.ok(produkService.getDetail(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ProdukDetailResponseDTO> create(
            @Valid @RequestBody ProdukCreateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produkService.create(request));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ProdukDetailResponseDTO> update(
            @PathVariable String id,
            @Valid @RequestBody ProdukUpdateRequestDTO request) {
        return ResponseEntity.ok(produkService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProdukResponseDTO> nonaktifkan(@PathVariable String id) {
        return ResponseEntity.ok(produkService.nonaktifkan(id));
    }
}