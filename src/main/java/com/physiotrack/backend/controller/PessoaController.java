package com.physiotrack.backend.controller;


import com.physiotrack.backend.model.estado.Estado;
import com.physiotrack.backend.model.pessoa.Pessoa;
import com.physiotrack.backend.model.pessoa.PessoaRequestDTO;
import com.physiotrack.backend.model.user.UserRegisterDTO;
import com.physiotrack.backend.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoa")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PessoaController {

   @Autowired
   private PessoaService pessoaService;

   /*
   * Endpoint utilizado somente para adicionar pacientes, o profissional é registrado como user
    */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody PessoaRequestDTO dto) {
        pessoaService.register(dto);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<List<Pessoa>> listarTodos() {
        return ResponseEntity.ok(pessoaService.findPessoas());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPessoa(@PathVariable Long id) {
        pessoaService.excluirPessoa(id);
        return ResponseEntity.noContent().build();
    }
}
