package com.physiotrack.backend.service;

import com.physiotrack.backend.model.cidade.Cidade;
import com.physiotrack.backend.model.cidade.dto.MunicipioDTO;
import com.physiotrack.backend.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;
    private static final String IBGE_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/{UF}/municipios";

    private final RestTemplate restTemplate;

    public List<MunicipioDTO> getMunicipiosPorEstadoIBGE(String uf) {
        String url = IBGE_API_URL.replace("{UF}", uf);

        MunicipioDTO[] municipios = restTemplate.getForObject(url, MunicipioDTO[].class);

        return Arrays.asList(municipios != null ? municipios : new MunicipioDTO[0]);
    }

    public List<Cidade> buscarPorEstado(Long estadoId) {
        return cidadeRepository.findByEstadoId(estadoId);
    }

    public void insertCidade (Cidade cidade){
        cidadeRepository.save(cidade);
    }


}