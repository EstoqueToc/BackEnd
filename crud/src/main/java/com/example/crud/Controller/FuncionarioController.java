package com.example.crud.Controller;

import com.example.crud.Model.Funcionario;
import com.example.crud.repository.FuncionarioRepository;
import com.example.crud.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    FuncionarioService service = new FuncionarioService();

    @Operation(summary = "Lista todos os funcionários")
    @GetMapping
    public ResponseEntity<List<Funcionario>> listar() {
        var lista = repository.findAll();
        return lista.isEmpty()
                ? status(204).build()
                : status(200).body(lista);
    }

    @Operation(summary = "Pesquisa um funcionário pelo índice na lista")
    @GetMapping("/{indice}")
    public ResponseEntity<Funcionario> pesquisarFuncionario(
            @Parameter(description = "Índice do funcionário na lista") @PathVariable Long indice) {
        return of(repository.findById(indice));
    }

    @Operation(summary = "Cadastra um novo funcionário")
    @PostMapping
    public ResponseEntity<Funcionario> cadastrar(
            @Parameter(description = "Objeto do funcionário com dados para cadastro") @RequestBody @Valid Funcionario funcionarioNovo) {
        repository.save(funcionarioNovo);
        return status(201).body(funcionarioNovo);
    }

    @Operation(summary = "Atualiza os dados de um funcionário pelo índice")
    @PutMapping("/{indice}")
    public ResponseEntity<Funcionario> atualizarFuncionario(
            @Parameter(description = "Índice do funcionário na lista") @PathVariable Long indice,
            @Parameter(description = "Objeto do funcionário com dados atualizados") @RequestBody @Valid Funcionario funcionarioAtualizado) {
        if (repository.existsById(indice)) {
            funcionarioAtualizado.setId(indice);
            repository.save(funcionarioAtualizado);
            return status(200).body(funcionarioAtualizado);
        }
        return status(404).build();
    }

    @Operation(summary = "Remove um funcionário da lista pelo índice")
    @DeleteMapping("/{indice}")
    public ResponseEntity<Void> removerFuncionario(
            @Parameter(description = "Índice do funcionário na lista para remoção") @PathVariable Long indice) {
        if (repository.existsById(indice)) {
            repository.deleteById(indice);
            return status(204).build();
        }
        return status(404).build();
    }

    @GetMapping("/lista-funcionario")
    public ResponseEntity<List<Funcionario>> listarFuncionariosOrdenados() {
        List<Funcionario> funcionarios = service.obterFuncionariosOrdenados(repository.findAll());

        return ResponseEntity.status(200).body(funcionarios);
    }

}

