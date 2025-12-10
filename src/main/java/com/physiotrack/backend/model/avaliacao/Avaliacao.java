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

    @Column(name = "data")
    private LocalDate data;

    // Dados Gerais
    private Double altura;
    private Double peso;
    private Double imc;

    private String esporte;
    private String queixas;

    @Column(name = "historico_saude")
    private String historicoSaude;

    private String medicamentos;
    private String cirurgia;

    @Column(name = "tratamento_anterior")
    private String tratamentoAnterior;

    @Column(name = "exame_imagem")
    private String exameImagem;

    @Column(name = "diagnostico_medico")
    private String diagnosticoMedico;

    @Column(name = "obj_tratamento")
    private String objTratamento;

    @Column(name = "obs_gerais")
    private String obsGerais;

    // Perimetria
    @Column(name = "perimetria_medida1d")
    private Double perimetriaMedida1D;

    @Column(name = "perimetria_medida2d")
    private Double perimetriaMedida2D;

    @Column(name = "perimetria_medida3d")
    private Double perimetriaMedida3D;

    @Column(name = "perimetria_panturrilhad")
    private Double perimetriaPanturrilhaD;

    @Column(name = "perimetria_assimetria_medida1e")
    private Double perimetriaAssimetriaMedida1E;

    @Column(name = "perimetria_assimetria_medida2e")
    private Double perimetriaAssimetriaMedida2E;

    @Column(name = "perimetria_assimetria_medida3e")
    private Double perimetriaAssimetriaMedida3E;

    @Column(name = "perimetria_panturrilhae")
    private Double perimetriaPanturrilhaE;

    @Column(name = "perimetria_medida_ass1")
    private Double perimetriaMedidaAss1;

    @Column(name = "perimetria_medida_ass2")
    private Double perimetriaMedidaAss2;

    @Column(name = "perimetria_medida_ass3")
    private Double perimetriaMedidaAss3;

    @Column(name = "perimetria_panturrilha_ass")
    private Double perimetriaPanturrilhaAss;

    // Lunge
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

    // ADM Rotadores Quadril
    @Column(name = "rot_quad_interd")
    private Double rotQuadInterD;

    @Column(name = "rot_quad_intere")
    private Double rotQuadInterE;

    @Column(name = "rot_quad_inter_ass")
    private Double rotQuadInterAss;

    @Column(name = "rot_quad_exterd")
    private Double rotQuadExterD;

    @Column(name = "rot_quad_extere")
    private Double rotQuadExterE;

    @Column(name = "rot_quad_exter_ass")
    private Double rotQuadExterAss;

    // Extensão/Flexão Joelho
    @Column(name = "ext_joelhod")
    private Double extJoelhoD;

    @Column(name = "ext_joelhoe")
    private Double extJoelhoE;

    @Column(name = "ext_joelho_ass")
    private Double extJoelhoAss;

    @Column(name = "flex_joelhod")
    private Double flexJoelhoD;

    @Column(name = "flex_joelhoe")
    private Double flexJoelhoE;

    @Column(name = "flex_joelho_ass")
    private Double flexJoelhoAss;

    // Single Hop
    @Column(name = "sh_test1d")
    private Double shTest1D;

    @Column(name = "sh_test2d")
    private Double shTest2D;

    @Column(name = "sh_test3d")
    private Double shTest3D;

    @Column(name = "sh_test_mediad")
    private Double shTestMediaD;

    @Column(name = "sh_test1e")
    private Double shTest1E;

    @Column(name = "sh_test2e")
    private Double shTest2E;

    @Column(name = "sh_test3e")
    private Double shTest3E;

    @Column(name = "sh_test_mediae")
    private Double shTestMediaE;

    @Column(name = "sh_test_score")
    private Double shTestScore;

    // Side Hop
    @Column(name = "sdh_test1d")
    private Double sdhTest1D;

    @Column(name = "sdh_test2d")
    private Double sdhTest2D;

    @Column(name = "sdh_test3d")
    private Double sdhTest3D;

    @Column(name = "sdh_test_mediad")
    private Double sdhTestMediaD;

    @Column(name = "sdh_test1e")
    private Double sdhTest1E;

    @Column(name = "sdh_test2e")
    private Double sdhTest2E;

    @Column(name = "sdh_test3e")
    private Double sdhTest3E;

    @Column(name = "sdh_test_mediae")
    private Double sdhTestMediaE;

    // Single Leg Bridge
    @Column(name = "slb_testd")
    private Double slbTestD;

    @Column(name = "slb_teste")
    private Double slbTestE;

    private String observacao;
}
