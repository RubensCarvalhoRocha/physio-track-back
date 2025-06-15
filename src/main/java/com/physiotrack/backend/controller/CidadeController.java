package com.physiotrack.backend.controller;

import com.physiotrack.backend.model.cidade.Cidade;
import com.physiotrack.backend.model.cidade.dto.MunicipioDTO;
import com.physiotrack.backend.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cidade")
@CrossOrigin(origins = "*")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    /*
    *Cidades cadastradas ao cadastrar usuario, interessante para ter facil acesso a estatistica de cidades/usuarios
     */
    @GetMapping
    public ResponseEntity<List<Cidade>> buscarPorEstado(@RequestParam("estadoId") Long estadoId) {
        return ResponseEntity.ok(cidadeService.buscarPorEstado(estadoId));
    }

    /*
    * Busca cidades direto da API do IBGE, não relacionadas ao BD.
    * */
    @GetMapping("ibge/{uf}")
    public List<Map<String, String>> getMunicipiosPorEstado(@PathVariable String uf) {
        List<MunicipioDTO> municipios = cidadeService.getMunicipiosPorEstadoIBGE(uf.toUpperCase());
        String ufUpper = uf.toUpperCase();
        return municipios.stream()
                .map(m -> {
                    Map<String, String> municipioMap = new HashMap<>();
                    municipioMap.put("nome", m.getNome());
                    municipioMap.put("sigla_estado", ufUpper);
                    return municipioMap;
                })
                .collect(Collectors.toList());
    }
}
