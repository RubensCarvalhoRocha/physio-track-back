package com.physiotrack.backend.model.atendimento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AtendimentoRequestDTO {

    private Long pacienteId;
    private String tipoAtendimento;
    private LocalDateTime dataAtendimento;
    private String descricao;
}
