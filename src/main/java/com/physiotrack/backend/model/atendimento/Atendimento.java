package com.physiotrack.backend.model.atendimento;

import com.physiotrack.backend.model.pessoa.Pessoa;
import com.physiotrack.backend.model.user.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "atendimento")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Pessoa paciente;

    @Column(nullable = false)
    private String tipoAtendimento; //TODO Enum

    @Column(nullable = false)
    private LocalDateTime dataAtendimento;

    private String descricao;
}