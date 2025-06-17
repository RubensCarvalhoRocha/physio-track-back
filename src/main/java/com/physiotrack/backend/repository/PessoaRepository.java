package com.physiotrack.backend.repository;

import com.physiotrack.backend.model.pessoa.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query(value = """
    SELECT * FROM pessoa 
    WHERE id = :id 
    AND ativo = true
    """, nativeQuery = true)
    Optional<Pessoa> findById(@Param("id") Long id);

    @Query(value = """
    SELECT * FROM pessoa 
    WHERE ativo = true
    """, nativeQuery = true)
    List<Pessoa> findAll();

}