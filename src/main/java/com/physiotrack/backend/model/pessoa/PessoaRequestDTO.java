package com.physiotrack.backend.model.pessoa;

import com.physiotrack.backend.model.endereco.EnderecoRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PessoaRequestDTO {

    private String nome;
    private String cpf;
    private String telefone;
    private EnderecoRequestDTO endereco;

}
