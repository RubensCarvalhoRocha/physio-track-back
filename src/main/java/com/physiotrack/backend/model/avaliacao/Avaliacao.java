package com.physiotrack.backend.model.avaliacao;

import com.physiotrack.backend.model.atendimento.Atendimento;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    private LocalDate data;

    // Dados Gerais
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

    // Perimetria
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

    // Lunge (mapeamento ajustado)
    @Column(name = "lunged")
    private Double lungeD;

    @Column(name = "lungee")
    private Double lungeE;

    @Column(name = "lunge_ass")
    private Double lungeAss;

    @Column(name = "lunged_2")
    private Double lungeD2;

    @Column(name = "lungee_2")
    private Double lungeE2;

    @Column(name = "lunge_ass_2")
    private Double lungeAss2;

    // ADM Rotadores de quadril
    private Double rotQuadInterD;
    private Double rotQuadInterE;
    private Double rotQuadInterAss;

    private Double rotQuadExterD;
    private Double rotQuadExterE;
    private Double rotQuadExterAss;

    // Extensão/Flexão de Joelho
    private Double extJoelhoD;
    private Double extJoelhoE;
    private Double extJoelhoAss;

    private Double flexJoelhoD;
    private Double flexJoelhoE;
    private Double flexJoelhoAss;

    // Single Hop
    private Double shTest1D;
    private Double shTest2D;
    private Double shTest3D;
    private Double shTestMediaD;

    private Double shTest1E;
    private Double shTest2E;
    private Double shTest3E;
    private Double shTestMediaE;

    private Double shTestScore;

    // Side Hop
    private Double sdhTest1D;
    private Double sdhTest2D;
    private Double sdhTest3D;
    private Double sdhTestMediaD;

    private Double sdhTest1E;
    private Double sdhTest2E;
    private Double sdhTest3E;
    private Double sdhTestMediaE;

    private Double sdhTestScore;

    // Single Leg Bridge
    private Double slbTestD;
    private Double slbTestE;

    // Observação
    private String observacao;
}
