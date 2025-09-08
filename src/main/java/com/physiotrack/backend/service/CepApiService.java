package com.physiotrack.backend.service;

import com.physiotrack.backend.model.cepApi.dto.EnderecoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CepApiService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    public EnderecoDto buscarCep(String cep) {
        return restTemplate.getForObject(VIA_CEP_URL, EnderecoDto.class, cep);
    }
}
