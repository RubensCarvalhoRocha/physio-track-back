package com.physiotrack.backend.model.imagem;

import com.physiotrack.backend.model.atendimento.Atendimento;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Data
@Entity
@Table(name = "imagem")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    private String descricao;

    @Column(nullable = false)
    private String tipoImagem; //TODO Enum


    @ManyToOne
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;
}