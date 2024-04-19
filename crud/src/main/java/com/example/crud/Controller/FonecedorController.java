package com.example.crud.Controller;

import com.example.crud.Interface.IUpDate;
import com.example.crud.Model.Fornecedor;
import com.example.crud.repository.FornecedorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/fornecedores")
public class FonecedorController implements IUpDate {
    private List<Fornecedor> fornecedores = new ArrayList<>();

    @Autowired
    private FornecedorRepository repository;

    @Operation(summary = "Adiciona um novo fornecedor à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fornecedor adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Fornecedor> adicionarFornecedor(
            @Parameter(description = "Objeto de fornecedor com os dados para criação") @Valid @RequestBody Fornecedor novoFornecedor) {
        fornecedores.add(novoFornecedor);
        return status(201).body(novoFornecedor);
    }

    @Operation(summary = "Retorna a lista de fornecedores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de fornecedores recuperada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum fornecedor disponível", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Fornecedor>> getFornecedores() {
        if (fornecedores.isEmpty()) {
            return status(204).build();
        }
        return status(200).body(fornecedores);
    }

    @Operation(summary = "Atualiza os dados de um fornecedor pelo índice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Índice inválido", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PutMapping("/{indice}")
    public ResponseEntity<String> atualizarFornecedor(
            @Parameter(description = "Índice do fornecedor na lista") @PathVariable int indice,
            @Parameter(description = "Dados do fornecedor para atualização") @Valid @RequestBody Fornecedor fornecedor) {
        if (indice >= 0 && indice < fornecedores.size()) {
            fornecedores.set(indice, fornecedor);
            return status(200).body("Fornecedor atualizado com sucesso.");
        } else {
            return status(404).body("Índice fora dos limites da lista.");
        }
    }

    @Operation(summary = "Remove um fornecedor da lista pelo índice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Índice inválido", content = @Content)
    })
    @DeleteMapping("/{indice}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Índice do fornecedor na lista para remoção") @PathVariable int indice) {
        if (indice >= 0 && indice < fornecedores.size()) {
            fornecedores.remove(indice);
            return status(200).build();
        } else {
            return status(404).build();
        }
    }

    @Operation(summary = "Aplica um desconto ao preço de um fornecedor pelo índice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Desconto aplicado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado ou percentual de desconto inválido", content = @Content)
    })
    @PutMapping("/desconto/{indice}")
    public ResponseEntity<String> aplicarDesconto(
            @Parameter(description = "Índice do fornecedor na lista") @PathVariable int indice,
            @Parameter(description = "Percentual de desconto a ser aplicado ao preço do fornecedor") @RequestParam("percentualDesconto") double percentualDesconto) {
        if (indice >= 0 && indice < fornecedores.size() && percentualDesconto >= 0) {
            Fornecedor fornecedor = fornecedores.get(indice);
            double novoPreco = fornecedor.getPreco() - (fornecedor.getPreco() * (percentualDesconto / 100.0));
            fornecedor.setPreco(novoPreco);
            return status(200).body("Desconto aplicado com sucesso.");
        } else {
            return status(404).body("Fornecedor não encontrado ou percentual de desconto inválido.");
        }
    }

    @Operation(summary = "Pesquisa fornecedores por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedores encontrados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum fornecedor encontrado com o nome fornecido", content = @Content)
    })
    @GetMapping("/fornecedor/{nome}")
    public ResponseEntity<List<Fornecedor>> getFornecedorPorNome(@PathVariable @Parameter(description = "Nome do fornecedor para pesquisa") String nome) {
        List<Fornecedor> fornecedores = repository.findByNomeContainsIgnoreCase(nome);
        return fornecedores.isEmpty() ? status(204).build() : status(200).body(fornecedores);
    }

    @Operation(summary = "Ordena os fornecedores por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedores ordenados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum fornecedor disponível para ordenação", content = @Content)
    })
    @GetMapping("/ordenar-fornecedor")
    public ResponseEntity<List<Fornecedor>> listarFornecedor() {
        var lista = repository.findAllByOrderByNomeAsc();
        return lista.isEmpty() ? status(204).build() : status(200).body(lista);
    }

    @Operation(summary = "Retorna um fornecedor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fornecedor com o ID fornecido não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable @Parameter(description = "ID do fornecedor para busca") Long id) {
        return of(repository.findById(id));
    }
}
