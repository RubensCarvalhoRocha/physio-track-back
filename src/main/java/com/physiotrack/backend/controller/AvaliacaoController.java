package com.physiotrack.backend.controller;

import com.physiotrack.backend.model.avaliacao.AvaliacaoRequestDTO;
import com.physiotrack.backend.service.AvaliacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
