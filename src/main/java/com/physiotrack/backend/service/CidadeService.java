package com.physiotrack.backend.service;

import com.physiotrack.backend.model.cidade.Cidade;
import com.physiotrack.backend.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public List<Cidade> buscarPorEstado(Long estadoId) {
        return cidadeRepository.findByEstadoId(estadoId);
    }
}