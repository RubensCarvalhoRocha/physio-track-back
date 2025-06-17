package com.physiotrack.backend.model.pessoa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PessoaPutRequestDTO {
    private String nome;
    private String cpf;
    private String telefone;
}
