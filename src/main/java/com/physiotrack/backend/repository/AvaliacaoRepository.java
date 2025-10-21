package com.physiotrack.backend.repository;

import com.physiotrack.backend.model.atendimento.Atendimento;
import com.physiotrack.backend.model.avaliacao.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    @Query(value = """
    SELECT * FROM avaliacao 
    WHERE atendimento_id = :atendimentoId 
    ORDER BY data DESC 
    LIMIT 1
    """, nativeQuery = true)
    Optional<Avaliacao> findLast(@Param("atendimentoId") Long userId);

    @Query(value = """
    SELECT a.*
    FROM avaliacao a
    JOIN atendimento atd ON atd.id = a.atendimento_id
    WHERE atd.paciente_id = :pacienteId
    ORDER BY atd.data_atendimento DESC
    LIMIT 10
    """, nativeQuery = true)
    List<Avaliacao> findAll(@Param("pacienteId") Long pacienteId);
    //Limitado em 10 por questões de performance

    @Query(value = """
    SELECT a.*
    FROM avaliacao a
    JOIN atendimento atd ON atd.id = a.atendimento_id
    WHERE atd.paciente_id = :pacienteId
      AND atd.data_atendimento BETWEEN :dataInicio AND CURRENT_DATE
    ORDER BY atd.data_atendimento DESC
    """, nativeQuery = true)
    List<Avaliacao> findAllByPeriodoAndPaciente(
            @Param("pacienteId") Long pacienteId,
            @Param("dataInicio") LocalDate dataInicio
    );


}