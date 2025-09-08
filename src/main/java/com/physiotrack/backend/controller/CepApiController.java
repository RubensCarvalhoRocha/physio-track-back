package com.physiotrack.backend.controller;

import com.physiotrack.backend.model.cepApi.dto.EnderecoDto;
import com.physiotrack.backend.service.CepApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cep")
@RequiredArgsConstructor
public class CepApiController {

    private final CepApiService cepService;

    @GetMapping("/{cep}")
    public EnderecoDto getEndereco(@PathVariable String cep) {
        return cepService.buscarCep(cep);
    }
}

