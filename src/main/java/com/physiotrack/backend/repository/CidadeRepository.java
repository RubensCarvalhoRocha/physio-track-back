package com.physiotrack.backend.repository;

import com.physiotrack.backend.model.cidade.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    @Query(value = "SELECT * FROM cidade WHERE estado_id = :estadoId", nativeQuery = true)
    List<Cidade> findByEstadoId(@Param("estadoId") Long estadoId);

}