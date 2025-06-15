package com.physiotrack.backend.service;


import com.physiotrack.backend.model.endereco.Endereco;
import com.physiotrack.backend.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public void insertEdereco (Endereco endereco){
        enderecoRepository.save(endereco);
    }

}