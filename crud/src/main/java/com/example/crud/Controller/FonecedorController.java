package com.example.crud.Controller;

import com.example.crud.Interface.IUpDate;
import com.example.crud.Model.Categoria;
import com.example.crud.Model.Fornecedor;
import com.example.crud.repository.FornecedorRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

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
    @PostMapping
    public ResponseEntity<Fornecedor> adicionarFornecedor(
            @Parameter(description = "Objeto de fornecedor com os dados para criação") @Valid @RequestBody Fornecedor novoFornecedor) {
        fornecedores.add(novoFornecedor);
        return status(201).body(novoFornecedor);
    }

    @Operation(summary = "Retorna a lista de fornecedores")
    @GetMapping
    public ResponseEntity<List<Fornecedor>> getFornecedores() {
        if (fornecedores.isEmpty()) {
            return status(204).build();
        }
        return status(200).body(fornecedores);
    }

    @Operation(summary = "Atualiza os dados de um fornecedor pelo índice")
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
    @GetMapping("/fornecedor/{nome}")
    public ResponseEntity<List<Fornecedor>> getFornecedorPorNome(@PathVariable @Parameter(description = "Nome do fornecedor para pesquisa") String nome) {
        List<Fornecedor> fornecedores = repository.findByNomeContainsIgnoreCase(nome);
        return fornecedores.isEmpty() ? status(204).build() : status(200).body(fornecedores);
    }

    @Operation(summary = "Ordena os fornecedores por nome")
    @GetMapping("/ordenar-fornecedor")
    public ResponseEntity<List<Fornecedor>> listarFornecedor() {
        var lista = repository.findAllByOrderByNomeAsc();
        return lista.isEmpty() ? status(204).build() : status(200).body(lista);
    }

    @Operation(summary = "Retorna um fornecedor pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable @Parameter(description = "ID do fornecedor para busca") Long id) {
        return of(repository.findById(id));
    }

}

