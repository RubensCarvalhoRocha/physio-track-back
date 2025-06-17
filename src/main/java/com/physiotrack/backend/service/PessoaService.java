package com.physiotrack.backend.service;


import com.physiotrack.backend.exceptions.ObjectNotFoundException;
import com.physiotrack.backend.model.cidade.Cidade;
import com.physiotrack.backend.model.endereco.Endereco;
import com.physiotrack.backend.model.estado.Estado;
import com.physiotrack.backend.model.pessoa.Pessoa;
import com.physiotrack.backend.model.pessoa.PessoaRequestDTO;
import com.physiotrack.backend.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final CidadeService cidadeService;
    private final EstadoService estadoService;
    private final EnderecoService enderecoService;

    public void insertPessoa (Pessoa pessoa){
        pessoaRepository.save(pessoa);
    }

    public Pessoa register (PessoaRequestDTO dto){
        //
        Estado estado = estadoService.findById(dto.getEndereco().getEstadoId());
        //
        Cidade cidade = new Cidade();
        cidade.setNome(dto.getEndereco().getCidade());
        cidade.setEstado(estado);
        cidadeService.insertCidade(cidade);
        //
        Endereco endereco = new Endereco();
        endereco.setRua(dto.getEndereco().getRua());
        endereco.setCep(dto.getEndereco().getCep());
        endereco.setEstado(estado.getSigla());
        endereco.setCidade(cidade);
        enderecoService.insertEdereco(endereco);
        //
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setCpf(dto.getCpf());
        pessoa.setTelefone(dto.getTelefone());
        pessoa.setEndereco(endereco);
        pessoa.setAtivo(true);
        //
        insertPessoa(pessoa);
        //
        return pessoa;
    }

    public List<Pessoa> findPessoas (){
        return pessoaRepository.findAll();
    }

    public Pessoa findById(Long id){
        return pessoaRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException("Pessoa não encontrada"));
    }

    public void excluirPessoa(Long id) {
        Pessoa pessoa = findById(id); // Garante que lança exceção se não existir
        pessoa.setAtivo(false);
        pessoaRepository.save(pessoa);
    }
}