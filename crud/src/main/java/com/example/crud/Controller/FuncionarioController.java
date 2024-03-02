package com.example.crud.Controller;

import com.example.crud.Model.Funcionario;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioController {

    private List<Funcionario> funcionarios = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Funcionario>> listar() {
        return ResponseEntity.status(200).body(funcionarios);
    }

    @GetMapping("/{indice}")
    public ResponseEntity<Funcionario> pesquisarFuncionario(@PathVariable int indice){
        if (indice >= 0 && indice < funcionarios.size()){
            return ResponseEntity.status(200).body(funcionarios.get(indice));
        }
        return ResponseEntity.status(200).build();
    }

    @PostMapping
    public ResponseEntity<Funcionario> cadastrar(@RequestBody @Valid Funcionario funcionarioNovo) {
        funcionarios.add(funcionarioNovo);
        return ResponseEntity.status(201).body(funcionarioNovo);
    }

    @PutMapping("/{indice}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable int indice, @RequestBody @Valid Funcionario funcionarioAtualizado){
        if (indice >=0 && indice < funcionarios.size()) {
            funcionarios.set(indice, funcionarioAtualizado);
            return ResponseEntity.status(200).body(funcionarioAtualizado);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{indice}")
    public ResponseEntity<Funcionario> removerFuncionario(@PathVariable int indice){
        if (indice >= 0 && indice < funcionarios.size()){
            funcionarios.remove(indice);
            return ResponseEntity.status(200).body(funcionarios.get(indice));
        }
        return ResponseEntity.status(404).build();
    }

    /*@PatchMapping("/{indice}")
    public ResponseEntity<Funcionario> atualizarFuncioario(@PathVariable int indice, @RequestBody @Valid ){

    }*/

}
