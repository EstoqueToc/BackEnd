package com.example.crud.Controller;

import com.example.crud.Model.Produto;
import com.example.crud.repository.ProdutoRepository;
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
    private List<Produto> produtos = new ArrayList<>();


    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody @Valid Produto novoProduto) {
        repository.save(novoProduto);
        return ResponseEntity.status(201).body(novoProduto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> getProdutos() {
        var lista = repository.findAll();
        return lista.isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(lista);
    }


    @GetMapping("/estoque/{qtdEstoque}")
    public List<Produto> buscarPorEstoque(
            @PathVariable int qtdEstoque) {
        return produtos
                .stream().
                filter(produtodaVez -> produtodaVez.getQtdEstoque() >= qtdEstoque).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> listarProdutoPorId(@PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Produto>> getProdutosPorCategoria(@PathVariable String categoria) {
        List<Produto> produtos = repository.findByCategoriaNomeIgnoreCase(categoria);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/preco")
    public ResponseEntity<List<Produto>> buscarPorFaixaPreco(@RequestParam("minimo") @PositiveOrZero Double precoMinimo,
                                                             @RequestParam("maximo") @PositiveOrZero Double precoMaximo) {
        if (precoMinimo == null || precoMaximo == null || precoMinimo > precoMaximo) {
            return ResponseEntity.status(400).build(); // HTTP 400 Bad Request
        }

        List<Produto> produtosNaFaixa = repository.findByPrecoDeVendaBetween(precoMinimo, precoMaximo);
        if (produtosNaFaixa.isEmpty()) {
            return ResponseEntity.status(204).build(); // HTTP 204 No Content
        }
        return ResponseEntity.status(200).body(produtosNaFaixa); // HTTP 200 OK
    }

    @PutMapping("/{id}/estoque")
    public ResponseEntity<String> adicionarEstoque(@PathVariable Long id,
                                                   @RequestParam("qtdEstoque") @NotNull @PositiveOrZero Integer quantidadeAdicional) {
        var produtoOpt = repository.findById(id);

        if (!produtoOpt.isPresent()) {
            return ResponseEntity.status(404).body("Produto não encontrado.");
        }

        Produto produto = produtoOpt.get();
        int quantidadeAtual = produto.getQtdEstoque();
        produto.setQtdEstoque(quantidadeAtual + quantidadeAdicional);
        repository.save(produto);

        return ResponseEntity.ok("Quantidade em estoque atualizada com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> alterarProduto(@PathVariable Long id, @Valid @RequestBody Produto produtoAtualizado) {
        if (repository.existsById(id)) {
            produtoAtualizado.setId(id);
            repository.save(produtoAtualizado);
            return ResponseEntity.status(200).body(produtoAtualizado);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

}

