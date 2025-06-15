package com.physiotrack.backend.service;


import com.physiotrack.backend.model.pessoa.Pessoa;
import com.physiotrack.backend.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public void insertPessoa (Pessoa pessoa){
        pessoaRepository.save(pessoa);
    }

}