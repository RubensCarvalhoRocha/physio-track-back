package com.physiotrack.backend.controller;

import com.physiotrack.backend.model.atendimento.Atendimento;
import com.physiotrack.backend.model.atendimento.AtendimentoRequestDTO;
import com.physiotrack.backend.service.AtendimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atendimento")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody AtendimentoRequestDTO dto) {
        atendimentoService.registerAtendimento(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Atendimento>> listarAtendimentos() {
        List<Atendimento> atendimentos = atendimentoService.listarAtendimentos();
        return ResponseEntity.ok(atendimentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atendimento> obterAtendimento(@PathVariable Long id) {
        Atendimento atendimento = atendimentoService.obterAtendimento(id);
        return ResponseEntity.ok(atendimento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atendimento> atualizarAtendimento(
            @PathVariable Long id,
            @RequestBody AtendimentoRequestDTO dto
    ) {
        Atendimento atualizado = atendimentoService.atualizarAtendimento(id, dto);
        return ResponseEntity.ok(atualizado);
    }
}
