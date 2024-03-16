package com.example.crud.Controller;

import com.example.crud.Interface.IUpDate;
import com.example.crud.Model.Fornecedor;
import com.example.crud.Model.Produto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class FonecedorController implements IUpDate {
    private List<Fornecedor> fornecedores = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Fornecedor> adicionarFornecedor(@Valid @RequestBody Fornecedor novoFornecedor) {
        fornecedores.add(novoFornecedor);
        return ResponseEntity.status(201).body(novoFornecedor);
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor>> getFornecedores() {
        if (fornecedores.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(fornecedores);
    }

    @PutMapping("/{indice}")
    public ResponseEntity<String> atualizarFornecedor(@PathVariable int indice,@Valid @RequestBody Fornecedor fornecedor) {
        fornecedores.set(indice, fornecedor);
        return ResponseEntity.status(200).body("Fornecedor atualizado com sucesso.");
    }

    @DeleteMapping("/{indice}")
    public Fornecedor delete(@PathVariable int indice){
        return fornecedores.remove(indice);
    }


    @Override
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
