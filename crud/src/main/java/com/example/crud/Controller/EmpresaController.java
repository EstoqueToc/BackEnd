package com.example.crud.Controller;

import com.example.crud.Model.Empresa;
import com.example.crud.repository.EmpresaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    EmpresaRepository repository;

    @Operation(summary = "Retorna todas as empresas")
    @GetMapping
    public ResponseEntity<List<Empresa>> getEmpresa() {
        var lista = repository.findAll();

        return lista.isEmpty() ? status(204).build() : status(200).body(lista);
    }

    @Operation(summary = "Retorna uma empresa pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresaById(@Parameter(description = "ID da empresa para busca") @PathVariable Long id) {
        return of(repository.findById(id));
    }

    @Operation(summary = "Cria uma nova empresa")
    @PostMapping
    public ResponseEntity<Empresa> criarEmpresa(@Parameter(description = "Objeto da empresa a ser criado") @RequestBody @Valid Empresa novaEmpresa) {
        repository.save(novaEmpresa);
        return status(201).body(novaEmpresa);
    }

    @Operation(summary = "Atualiza uma empresa pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizarEmpresa(@Parameter(description = "ID do produto para atualização") @PathVariable Long id, @RequestBody @Valid Empresa empresaAtualizada) {
        if (!repository.existsById(id)) {
            return status(404).build();
        }
        empresaAtualizada.setEmpresa_id(id);
        repository.save(empresaAtualizada);
        return status(200).body(empresaAtualizada);
    }

    @Operation(summary = "Deleta uma empresa pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@Parameter(description = "ID da empresa para exclusão") @PathVariable Long id) {
        if (!repository.existsById(id)) {
            return status(404).build();
        }
        repository.deleteById(id);
        return status(204).build();
    }
}
