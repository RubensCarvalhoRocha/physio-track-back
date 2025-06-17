package com.physiotrack.backend.repository;

import com.physiotrack.backend.model.atendimento.Atendimento;
import com.physiotrack.backend.model.avaliacao.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

}