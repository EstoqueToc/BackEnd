package com.example.crud.Controller;

import com.example.crud.Interface.IUpDate;
import com.example.crud.Model.Fornecedor;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FonecedorController implements IUpDate {
    private List<Fornecedor> fornecedores = new ArrayList<>();
    @Operation(summary = "Adiciona um novo fornecedor à lista")
    @PostMapping
    public ResponseEntity<Fornecedor> adicionarFornecedor(@Valid @RequestBody Fornecedor novoFornecedor) {
        fornecedores.add(novoFornecedor);
        return ResponseEntity.status(201).body(novoFornecedor);
    }

    @Operation(summary = "Retorna a lista de fornecedores")
    @GetMapping
    public ResponseEntity<List<Fornecedor>> getFornecedores() {
        if (fornecedores.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(fornecedores);
    }

    @Operation(summary = "Atualiza os dados de um fornecedor pelo índice")
    @PutMapping("/{indice}")
    public ResponseEntity<String> atualizarFornecedor(@PathVariable int indice, @Valid @RequestBody Fornecedor fornecedor) {
        fornecedores.set(indice, fornecedor);
        return ResponseEntity.status(200).body("Fornecedor atualizado com sucesso.");
    }

    @Operation(summary = "Remove um fornecedor da lista pelo índice")
    @DeleteMapping("/{indice}")
    public ResponseEntity<Fornecedor> delete(@PathVariable int indice) {
        if (indice >= 0 && indice < fornecedores.size()) {
            fornecedores.remove(indice);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    @Operation(summary = "Aplica um desconto ao preço de um fornecedor pelo índice")
    @Override
    @PutMapping("/desconto/{indice}")
    public ResponseEntity<String> aplicarDesconto(@PathVariable int indice, @RequestParam("percentualDesconto") double percentualDesconto) {
        if (indice >= 0 && indice < fornecedores.size() && percentualDesconto >= 0) {
            Fornecedor fornecedor = fornecedores.get(indice);
            double novoPreco = fornecedor.getPreco();

            if (novoPreco > 0) {
                novoPreco -= novoPreco * (percentualDesconto / 100.0);
                fornecedor.setPreco(novoPreco);
                return ResponseEntity.status(200).body("Desconto aplicado com sucesso.");
            } else {
                return ResponseEntity.status(400).body("O preço do fornecedor não é válido.");
            }
        } else {
            return ResponseEntity.status(404).body("Fornecedor não encontrado ou percentual de desconto inválido.");
        }
    }
}
