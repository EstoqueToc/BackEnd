package com.example.crud.service;

import com.example.crud.Model.Produto;

import com.example.crud.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EstoqueService {
    @Autowired
    private ProdutoRepository produtoRepository;

    // Método para obter a quantidade total de produtos no estoque
    public ResponseEntity<Integer> getTotalProdutosEmEstoque() {
        List<Produto> produtos = produtoRepository.findAll();
        int total = produtos.stream().mapToInt(Produto::getQtdEstoque).sum();
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    // Método para obter a quantidade de produtos por categoria
    public ResponseEntity<Map<String, Integer>> getProdutosPorCategoria() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<String, Integer> produtosPorCategoria = produtos.stream().collect(Collectors.groupingBy(
                produto -> produto.getCategoria().getNome(),
                Collectors.summingInt(Produto::getQtdEstoque)
        ));
        return new ResponseEntity<>(produtosPorCategoria, HttpStatus.OK);
    }

    // Método para obter a quantidade de produtos por fornecedor
    public ResponseEntity<Map<String, Integer>> getProdutosPorFornecedor() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<String, Integer> produtosPorFornecedor = produtos.stream().collect(Collectors.groupingBy(
                produto -> produto.getFornecedor().getNome(),
                Collectors.summingInt(Produto::getQtdEstoque)
        ));
        return new ResponseEntity<>(produtosPorFornecedor, HttpStatus.OK);
    }

    // Método para obter a quantidade de produtos por data de entrada
    public ResponseEntity<Map<LocalDate, Integer>> getProdutosPorDataDeEntrada() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<LocalDate, Integer> produtosPorDataDeEntrada = produtos.stream().collect(Collectors.groupingBy(
                Produto::getDataDeEntrada,
                Collectors.summingInt(Produto::getQtdEstoque)
        ));
        return new ResponseEntity<>(produtosPorDataDeEntrada, HttpStatus.OK);
    }

    // Método para obter a quantidade de produtos por data de validade
    public ResponseEntity<Map<LocalDate, Integer>> getProdutosPorDataDeValidade() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<LocalDate, Integer> produtosPorDataDeValidade = produtos.stream().collect(Collectors.groupingBy(
                Produto::getDataDeValidade,
                Collectors.summingInt(Produto::getQtdEstoque)
        ));
        return new ResponseEntity<>(produtosPorDataDeValidade, HttpStatus.OK);
    }
}
