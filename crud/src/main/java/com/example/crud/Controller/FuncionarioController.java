package com.example.crud.Controller;

import com.example.crud.Model.Funcionario;
import com.example.crud.repository.FuncionarioRepository;
import com.example.crud.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum funcionário disponível", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Funcionario>> listar() {
        var lista = repository.findAll();
        return lista.isEmpty()
                ? status(204).build()
                : status(200).body(lista);
    }

    @Operation(summary = "Pesquisa um funcionário pelo índice na lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content)
    })
    @GetMapping("/{indice}")
    public ResponseEntity<Funcionario> pesquisarFuncionario(
            @Parameter(description = "Índice do funcionário na lista") @PathVariable Long indice) {
        return of(repository.findById(indice));
    }

    @Operation(summary = "Cadastra um novo funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping
    public ResponseEntity<Funcionario> cadastrar(
            @Parameter(description = "Objeto do funcionário com dados para cadastro") @RequestBody @Valid Funcionario funcionarioNovo) {
        repository.save(funcionarioNovo);
        return status(201).body(funcionarioNovo);
    }

    @Operation(summary = "Atualiza os dados de um funcionário pelo índice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do funcionário atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(hidden = true)))
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content)
    })
    @DeleteMapping("/{indice}")
    public ResponseEntity<Void> removerFuncionario(
            @Parameter(description = "Índice do funcionário na lista para remoção") @PathVariable Long indice) {
        if (repository.existsById(indice)) {
            repository.deleteById(indice);
            return status(204).build();
        }
        return status(404).build();
    }

    @Operation(summary = "Lista os funcionários em ordem alfabética")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários listados em ordem alfabética com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum funcionário para listar", content = @Content)
    })
    @GetMapping("/lista-funcionario")
    public ResponseEntity<List<Funcionario>> listarFuncionariosOrdenados() {
        List<Funcionario> funcionarios = service.ordenacaoQuickSort(repository.findAll().toArray(new Funcionario[0])
                , 0, repository.findAll().size() - 1).getBody();
        return status(200).body(funcionarios);
    }

    @Operation(summary = "Ordena os funcionários por Função")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários ordenados por função com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum funcionário para ordenar por função", content = @Content)
    })
    @GetMapping("/lista-funcao")
    public ResponseEntity<List<Funcionario>> listarFuncionariosPorCargo() {
        List<Funcionario> funcionarios = repository.findAllByOrderByFuncaoAsc();
        return funcionarios.isEmpty()
                ? status(204).build()
                : status(200).body(funcionarios);
    }

    @Operation(summary = "Valida se o funcionário existe para login")
    @GetMapping("/login/{email}/{senha}")
    public ResponseEntity<Boolean> validarFuncionario(@PathVariable String email, @PathVariable String senha) {
        Boolean funcionario = repository.existsByEmailAndSenha(email, senha);

        return funcionario == false
                ? status(404).build()
                : status(200).body(funcionario);
    }

}
