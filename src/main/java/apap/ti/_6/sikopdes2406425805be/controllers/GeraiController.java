package apap.ti._6.sikopdes2406425805be.controllers;

import apap.ti._6.sikopdes2406425805be.dto.gerai.GeraiCreateRequestDTO;
import apap.ti._6.sikopdes2406425805be.dto.gerai.GeraiDetailResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.gerai.GeraiResponseDTO;
import apap.ti._6.sikopdes2406425805be.dto.gerai.GeraiUpdateRequestDTO;
import apap.ti._6.sikopdes2406425805be.services.GeraiService;
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
@RequestMapping("/api/gerai")
public class GeraiController {

    private final GeraiService geraiService;

    public GeraiController(GeraiService geraiService) {
        this.geraiService = geraiService;
    }

    @GetMapping
    public ResponseEntity<List<GeraiResponseDTO>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(geraiService.getAll(search, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeraiDetailResponseDTO> getDetail(@PathVariable String id) {
        return ResponseEntity.ok(geraiService.getDetail(id));
    }

    @PostMapping("/create")
    public ResponseEntity<GeraiDetailResponseDTO> create(
            @Valid @RequestBody GeraiCreateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(geraiService.create(request));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<GeraiDetailResponseDTO> update(
            @PathVariable String id,
            @Valid @RequestBody GeraiUpdateRequestDTO request) {
        return ResponseEntity.ok(geraiService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeraiResponseDTO> nonaktifkan(@PathVariable String id) {
        return ResponseEntity.ok(geraiService.nonaktifkan(id));
    }
}