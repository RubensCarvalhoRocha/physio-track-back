package com.physiotrack.backend.service;

import com.physiotrack.backend.model.atendimento.Atendimento;
import com.physiotrack.backend.model.avaliacao.Avaliacao;
import com.physiotrack.backend.model.avaliacao.AvaliacaoRequestDTO;
import com.physiotrack.backend.model.user.User;
import com.physiotrack.backend.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    private final AtendimentoService atendimentoService;

    private final UserService userService;

    private final ModelMapper mapper;

    public Avaliacao register (AvaliacaoRequestDTO dto){
        User user = userService.getLoggedUser();
        Atendimento atendimento = atendimentoService.findLast(user.getId());
        Avaliacao avaliacao = new Avaliacao();
        mapper.map(dto, avaliacao);
        avaliacao.setAtendimento(atendimento);
        insetAvaliacao(avaliacao);
        return avaliacao;
    }

    public void insetAvaliacao(Avaliacao avaliacao){
        avaliacaoRepository.save(avaliacao);
    }

}