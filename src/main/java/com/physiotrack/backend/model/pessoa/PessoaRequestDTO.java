package com.physiotrack.backend.model.pessoa;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private EnderecoRequestDTO endereco; //TODO Verificar a possibilidade de alterar endereço
    private Boolean isPaciente; // preenchido automaticamente ao chamar o endpoint de cadastar paciente
    private String email;

}
