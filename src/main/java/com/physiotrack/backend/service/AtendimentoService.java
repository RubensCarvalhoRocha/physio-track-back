package com.physiotrack.backend.service;

import com.physiotrack.backend.model.atendimento.Atendimento;
import com.physiotrack.backend.model.atendimento.AtendimentoRequestDTO;
import com.physiotrack.backend.model.pessoa.Pessoa;
import com.physiotrack.backend.model.user.User;
import com.physiotrack.backend.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;
    private final UserService userService;
    private final PessoaService pessoaService;

    @Transactional
    public Atendimento registerAtendimento (AtendimentoRequestDTO dto){
        //
        User user = userService.getLoggedUser();
        //
        Pessoa paciente = pessoaService.findById(dto.getPacienteId());
        //
        Atendimento atendimento = new Atendimento();
        atendimento.setUsuario(user);
        atendimento.setPaciente(paciente);
        atendimento.setTipoAtendimento(dto.getTipoAtendimento());
        atendimento.setDataAtendimento(LocalDateTime.now());
        atendimento.setDescricao(dto.getDescricao());
        //
        insertAtendimento(atendimento);
        return atendimento;
    }

    public void insertAtendimento(Atendimento atendimento){
        atendimentoRepository.save(atendimento);
    }

    public  Atendimento findLast(Long id){
        return atendimentoRepository.findLast(id);
    }

    public List<Atendimento> listarAtendimentos() {
        return atendimentoRepository.findAll();
    }

    public Atendimento obterAtendimento(Long id) {
        return atendimentoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Atendimento não encontrado com ID: " + id));
    }

    @Transactional
    public Atendimento atualizarAtendimento(Long id, AtendimentoRequestDTO dto) {
        Atendimento atendimento = obterAtendimento(id);
        Pessoa paciente = pessoaService.findById(dto.getPacienteId());

        atendimento.setPaciente(paciente);
        atendimento.setTipoAtendimento(dto.getTipoAtendimento());
        atendimento.setDescricao(dto.getDescricao());

        // Atualiza a data, se fornecida
        if (dto.getDataAtendimento() != null) {
            atendimento.setDataAtendimento(dto.getDataAtendimento());
        }

        return atendimentoRepository.save(atendimento);
    }
}
