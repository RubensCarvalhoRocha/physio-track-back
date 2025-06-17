package com.physiotrack.backend.model.avaliacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvaliacaoResponseDTO {


    private LocalDateTime data;

    private Double altura;
    private Double peso;
    private Double imc;

    private String esporte;
    private String queixas;
    private String historicoSaude;
    private String medicamentos;
    private String cirurgia;
    private String tratamentoAnterior;
    private String exameImagem;
    private String diagnosticoMedico;
    private String objTratamento;
    private String obsGerais;

    private Double perimetriaMedida1D;
    private Double perimetriaMedida2D;
    private Double perimetriaMedida3D;
    private Double perimetriaPanturrilhaD;

    private Double perimetriaAssimetriaMedida1E;
    private Double perimetriaAssimetriaMedida2E;
    private Double perimetriaAssimetriaMedida3E;
    private Double perimetriaPanturrilhaE;

    private Double perimetriaMedidaAss1;
    private Double perimetriaMedidaAss2;
    private Double perimetriaMedidaAss3;
    private Double perimetriaPanturrilhaAss;

    private Double lungeD;
    private Double lungeE;
    private Double lungeAss;

    private Double rotQuadInterD;
    private Double rotQuadInterE;
    private Double rotQuadInterAss;

    private Double rotQuadExterD;
    private Double rotQuadExterE;
    private Double rotQuadExterAss;

    private Double extJoelhoD;
    private Double extJoelhoE;
    private Double extJoelhoAss;

    private Double flexJoelhoD;
    private Double flexJoelhoE;
    private Double flexJoelhoAss;

    private Double shTest1D;
    private Double shTest2D;
    private Double shTest3D;
    private Double shTestMediaD;

    private Double shTest1E;
    private Double shTest2E;
    private Double shTest3E;
    private Double shTestMediaE;

    private Double shTestScore;
    private Double slbTestD;
    private Double slbTestE;

}
