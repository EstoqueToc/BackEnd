package com.example.crud.Controller;

import com.example.crud.Model.Funcionario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private List<Funcionario> funcionarios = new ArrayList<>();

    @Operation(summary = "Lista todos os funcionários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum funcionário disponível", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Funcionario>> listar() {
        if (funcionarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(funcionarios);
    }

    @Operation(summary = "Pesquisa um funcionário pelo índice na lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content)
    })
    @GetMapping("/{indice}")
    public ResponseEntity<Funcionario> pesquisarFuncionario(
            @Parameter(description = "Índice do funcionário na lista") @PathVariable int indice) {
        if (indice >= 0 && indice < funcionarios.size()) {
            return ResponseEntity.status(200).body(funcionarios.get(indice));
        }
        return ResponseEntity.status(404).build();
    }

    @Operation(summary = "Cadastra um novo funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping
    public ResponseEntity<Funcionario> cadastrar(
            @Parameter(description = "Objeto do funcionário com dados para cadastro") @RequestBody @Valid Funcionario funcionarioNovo) {
        funcionarios.add(funcionarioNovo);
        return ResponseEntity.status(201).body(funcionarioNovo);
    }

    @Operation(summary = "Atualiza os dados de um funcionário pelo índice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do funcionário atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(hidden = true)))
    })
    @PutMapping("/{indice}")
    public ResponseEntity<Funcionario> atualizarFuncionario(
            @Parameter(description = "Índice do funcionário na lista") @PathVariable int indice,
            @Parameter(description = "Objeto do funcionário com dados atualizados") @RequestBody @Valid Funcionario funcionarioAtualizado) {
        if (indice >= 0 && indice < funcionarios.size()) {
            funcionarios.set(indice, funcionarioAtualizado);
            return ResponseEntity.status(200).body(funcionarioAtualizado);
        }
        return ResponseEntity.status(404).build();
    }

    @Operation(summary = "Remove um funcionário da lista pelo índice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content)
    })
    @DeleteMapping("/{indice}")
    public ResponseEntity<Void> removerFuncionario(
            @Parameter(description = "Índice do funcionário na lista para remoção") @PathVariable int indice) {
        if (indice >= 0 && indice < funcionarios.size()) {
            funcionarios.remove(indice);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }
}
