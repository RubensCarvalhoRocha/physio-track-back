package com.physiotrack.backend.repository;

import com.physiotrack.backend.model.atendimento.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    @Query(value = """
    SELECT * FROM atendimento 
    WHERE usuario_id = :userId 
    ORDER BY data_atendimento DESC 
    LIMIT 1
    """, nativeQuery = true)
    Atendimento findLast(@Param("userId") Long userId);

    @Query(value = """
    SELECT * FROM atendimento 
    WHERE paciente_id = :pacienteId 
    ORDER BY data_atendimento DESC
    """, nativeQuery = true)
    List<Atendimento> findAtendimentosPorPessoa(@Param("pacienteId") Long pacienteId);

}