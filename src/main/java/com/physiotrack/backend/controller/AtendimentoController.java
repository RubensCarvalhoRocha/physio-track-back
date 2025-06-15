package com.physiotrack.backend.controller;

import com.physiotrack.backend.model.atendimento.AtendimentoRequestDTO;
import com.physiotrack.backend.service.AtendimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
