package com.example.crud.Controller;

import com.example.crud.Model.Produto;
import com.example.crud.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;
    @Operation(summary = "Cria um novo produto e o adiciona ao repositório")
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody @Valid Produto novoProduto) {
        repository.save(novoProduto);
        return ResponseEntity.status(201).body(novoProduto);
    }

    @Operation(summary = "Retorna todos os produtos")
    @GetMapping
    public ResponseEntity<List<Produto>> getProdutos() {
        var lista = repository.findAll();
        return lista.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Busca produtos com quantidade em estoque maior ou igual ao valor especificado")
    @GetMapping("/estoque/{qtdEstoque}")
    public ResponseEntity<List<Produto>> buscarPorEstoque(
            @PathVariable int qtdEstoque) {
        var produtos = repository.findByQtdEstoqueGreaterThanEqual(qtdEstoque);
        return produtos.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(produtos);
    }

    @Operation(summary = "Busca um produto pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<Produto> listarProdutoPorId(@PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @Operation(summary = "Busca produtos por uma categoria específica, ignorando diferenças entre maiúsculas e minúsculas")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Produto>> getProdutosPorCategoria(@PathVariable String categoria) {
        List<Produto> produtos = repository.findByCategoriaNomeIgnoreCase(categoria);
        return produtos.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(produtos);
    }

    @Operation(summary = "Busca produtos dentro de uma faixa de preço especificada")
    @GetMapping("/preco")
    public ResponseEntity<List<Produto>> buscarPorFaixaPreco(
            @RequestParam("minimo") @PositiveOrZero Double precoMinimo,
            @RequestParam("maximo") @PositiveOrZero Double precoMaximo) {
        if (precoMinimo == null || precoMaximo == null || precoMinimo > precoMaximo) {
            return ResponseEntity.status(400).build();
        }
        List<Produto> produtosNaFaixa = repository.findByPrecoDeVendaBetween(precoMinimo, precoMaximo);
        return produtosNaFaixa.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(produtosNaFaixa);
    }

    @Operation(summary = "Adiciona estoque ao produto pelo ID")
    @PutMapping("/{id}/estoque")
    public ResponseEntity<String> adicionarEstoque(@PathVariable Long id,
                                                   @RequestParam("qtdEstoque") @NotNull @PositiveOrZero Integer quantidadeAdicional) {
        var produtoOpt = repository.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            int quantidadeAtual = produto.getQtdEstoque();
            produto.setQtdEstoque(quantidadeAtual + quantidadeAdicional);
            repository.save(produto);
            return ResponseEntity.ok("Quantidade em estoque atualizada com sucesso.");
        }
        return ResponseEntity.status(404).body("Produto não encontrado.");
    }

    @Operation(summary = "Atualiza os dados de um produto pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<Produto> alterarProduto(@PathVariable Long id, @Valid @RequestBody Produto produtoAtualizado) {
        if (repository.existsById(id)) {
            produtoAtualizado.setId(id);
            repository.save(produtoAtualizado);
            return ResponseEntity.status(200).body(produtoAtualizado);
        }
        return ResponseEntity.status(404).build();
    }

    @Operation(summary = "Deleta um produto pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}

