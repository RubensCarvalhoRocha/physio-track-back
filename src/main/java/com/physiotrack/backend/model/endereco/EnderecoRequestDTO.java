package com.physiotrack.backend.model.endereco;

import com.physiotrack.backend.model.cidade.Cidade;
import com.physiotrack.backend.model.estado.Estado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnderecoRequestDTO {

    private String rua;
    private String cep;
    private Long estadoId;
    private Long cidadeId;
}
