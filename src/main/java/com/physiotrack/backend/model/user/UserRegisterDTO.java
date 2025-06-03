package com.physiotrack.backend.model.user;

import com.physiotrack.backend.model.pessoa.PessoaRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegisterDTO {

    private String email;
    private String password;
    private PessoaRequestDTO pessoa;

}
