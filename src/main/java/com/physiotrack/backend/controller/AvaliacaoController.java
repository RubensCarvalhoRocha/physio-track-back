package com.physiotrack.backend.controller;

import com.physiotrack.backend.model.avaliacao.AvaliacaoRequestDTO;
import com.physiotrack.backend.service.AvaliacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/avaliacao")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping("/register/{id}")
    public ResponseEntity<Void> register(@RequestBody AvaliacaoRequestDTO dto,
                                         @PathVariable Long id) {
        avaliacaoService.register(dto,id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<byte[]> gerarRelatorio(@PathVariable Long id) throws Exception {
        byte[] pdf = avaliacaoService.gerarPdf(id);

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=relatorio.pdf") // ou "attachment" para download
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/reports")
    public ResponseEntity<byte[]> gerarRelatorios(
            @RequestParam Long pacienteId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial
    ) throws Exception {

        byte[] pdf = avaliacaoService.gerarPdfs(pacienteId, dataInicial);

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=relatorio.pdf") // "attachment" → download direto
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
