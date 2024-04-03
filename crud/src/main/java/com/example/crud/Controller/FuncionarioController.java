package com.example.crud.Controller;

import com.example.crud.Model.Funcionario;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private List<Funcionario> funcionarios = new ArrayList<>();

    @Operation(summary = "Lista todos os funcionários")
    @GetMapping
    public ResponseEntity<List<Funcionario>> listar() {
        if (funcionarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(funcionarios);
    }

    @Operation(summary = "Pesquisa um funcionário pelo índice na lista")
    @GetMapping("/{indice}")
    public ResponseEntity<Funcionario> pesquisarFuncionario(@PathVariable int indice){
        if (indice >= 0 && indice < funcionarios.size()){
            return ResponseEntity.status(200).body(funcionarios.get(indice));
        }
        return ResponseEntity.status(404).build();
    }

    @Operation(summary = "Cadastra um novo funcionário")
    @PostMapping
    public ResponseEntity<Funcionario> cadastrar(@RequestBody @Valid Funcionario funcionarioNovo) {
        funcionarios.add(funcionarioNovo);
        return ResponseEntity.status(201).body(funcionarioNovo);
    }

    @Operation(summary = "Atualiza os dados de um funcionário pelo índice")
    @PutMapping("/{indice}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable int indice, @RequestBody @Valid Funcionario funcionarioAtualizado){
        if (indice >=0 && indice < funcionarios.size()) {
            funcionarios.set(indice, funcionarioAtualizado);
            return ResponseEntity.status(200).body(funcionarioAtualizado);
        }
        return ResponseEntity.status(404).build();
    }

    @Operation(summary = "Remove um funcionário da lista pelo índice")
    @DeleteMapping("/{indice}")
    public ResponseEntity<Funcionario> removerFuncionario(@PathVariable int indice){
        if (indice >= 0 && indice < funcionarios.size()){
            funcionarios.remove(indice);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }
}

