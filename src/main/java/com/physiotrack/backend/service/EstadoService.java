package com.physiotrack.backend.service;

import com.physiotrack.backend.exceptions.ObjectNotFoundException;
import com.physiotrack.backend.model.estado.Estado;
import com.physiotrack.backend.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public List<Estado> listarTodos() {
        return estadoRepository.findAll();
    }

    public Estado findById(Long id){
        return estadoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Estado não encontrado"));
    }
}