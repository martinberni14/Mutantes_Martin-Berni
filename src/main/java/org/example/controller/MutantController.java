package org.example.controller;

import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Mutant Detector", description = "API para detectar mutantes")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @PostMapping("/mutant")
    @Operation(summary = "Detectar mutante", description = "Devuelve 200 si es mutante, 403 si es humano")
    @ApiResponse(responseCode = "200", description = "Es Mutante")
    @ApiResponse(responseCode = "403", description = "No es Mutante (Humano)")
    public ResponseEntity<Void> isMutant(@Valid @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.analyze(request.getDna());
        if (isMutant) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/stats")
    @Operation(summary = "Obtener estadísticas")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }
    @GetMapping("/")
    public RedirectView home() {
        return new RedirectView("/swagger-ui.html");
    }
}