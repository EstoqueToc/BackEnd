package com.example.crud.Controller;

import com.example.crud.Model.Produto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private List<Produto> produtos = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Produto> criarProduto(@Valid @RequestBody Produto novoProduto) {
        produtos.add(novoProduto);
        return ResponseEntity.status(201).body(novoProduto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> getProdutos() {
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos);
    }


    @GetMapping("/{indice}")
    public ResponseEntity<Produto> get(@PathVariable int indice) {
        if (indice >=0 && indice < produtos.size()) {
            return ResponseEntity.status(200)
                    .body(produtos.get(indice));
        }
        return ResponseEntity.status(404).build();
    }


    @GetMapping("/estoque/{qtdEstoque}")
    public List<Produto> buscarPorEstoque(
            @PathVariable int qtdEstoque) {
        return produtos
                .stream().
                filter(produtodaVez -> produtodaVez.getQtdEstoque() >= qtdEstoque)
                .toList();
    }

    @GetMapping("/grupo/{grupo}")
    public List<Produto> getProdutosPorGrupo(@PathVariable String grupo) {
        return produtos.stream()
                .filter(produto -> produto.getGrupo().equalsIgnoreCase(grupo))
                .collect(Collectors.toList());
    }



    @PutMapping("/{indice}")
    public String atualizar(@PathVariable int indice, @Valid @RequestBody Produto produto){
        produtos.set(indice, produto);
        return "Produto atualizado";
    }


    //validaçoes


}

