package com.example.crud.service.usuario;

import com.example.crud.Model.Produto;
import com.example.crud.repository.ProdutoRepository;
import com.example.crud.slack.Slack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
        return ResponseEntity.ok(total);
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

    // Método para verificar e enviar alertas
    public boolean verificarAlertas() {
        ResponseEntity<Integer> responseEntity = getTotalProdutosEmEstoque();
        int total = responseEntity.getBody();

        List<Produto> produtos = produtoRepository.findAll();
        produtos.forEach(produto -> {
            int estoqueProduto = produto.getQtdEstoque();
            // Verifica se chegou a 50% do total
            if (estoqueProduto <= total / 2) {
                try {
                    Slack.sendMessage("{\"text\": \"Alerta: O produto " + produto.getNome() + " chegou na metade do estoque!\"}");
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // Verifica se chegou a 20% do total
            if (estoqueProduto <= total * 0.2) {
                try {
                    Slack.sendMessage("{\"text\": \"Alerta Crítico: O produto " + produto.getNome() + " chegou a 20% do estoque!\"}");
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return false;
    }

}
