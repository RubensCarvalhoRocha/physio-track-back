package com.physiotrack.backend.controller;

import com.physiotrack.backend.model.cidade.Cidade;
import com.physiotrack.backend.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cidade")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<Cidade>> buscarPorEstado(@RequestParam("estadoId") Long estadoId) {
        return ResponseEntity.ok(cidadeService.buscarPorEstado(estadoId));
    }
}
