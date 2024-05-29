package com.example.crud.service.usuario;

import com.example.crud.Model.Alerta;
import com.example.crud.Model.Produto;
import com.example.crud.repository.AlertaRepository;
import com.example.crud.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class EstoqueService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private AlertaRepository alertaRepository;

    private static final Logger logger = Logger.getLogger(EstoqueService.class.getName());

    public ResponseEntity<Integer> getTotalProdutosEmEstoque() {
        List<Produto> produtos = produtoRepository.findAll();
        int total = produtos.stream().mapToInt(Produto::getQtdEstoque).sum();
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Integer>> getProdutosPorCategoria() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<String, Integer> produtosPorCategoria = produtos.stream().collect(Collectors.groupingBy(
                produto -> produto.getCategoria().getNome(),
                Collectors.summingInt(Produto::getQtdEstoque)
        ));
        return new ResponseEntity<>(produtosPorCategoria, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Integer>> getProdutosPorFornecedor() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<String, Integer> produtosPorFornecedor = produtos.stream().collect(Collectors.groupingBy(
                produto -> produto.getFornecedor().getNome(),
                Collectors.summingInt(Produto::getQtdEstoque)
        ));
        return new ResponseEntity<>(produtosPorFornecedor, HttpStatus.OK);
    }

    public ResponseEntity<Map<LocalDate, Integer>> getProdutosPorDataDeEntrada() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<LocalDate, Integer> produtosPorDataDeEntrada = produtos.stream().collect(Collectors.groupingBy(
                Produto::getDataDeEntrada,
                Collectors.summingInt(Produto::getQtdEstoque)
        ));
        return new ResponseEntity<>(produtosPorDataDeEntrada, HttpStatus.OK);
    }

    public ResponseEntity<Map<LocalDate, Integer>> getProdutosPorDataDeValidade() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<LocalDate, Integer> produtosPorDataDeValidade = produtos.stream().collect(Collectors.groupingBy(
                Produto::getDataDeValidade,
                Collectors.summingInt(Produto::getQtdEstoque)
        ));
        return new ResponseEntity<>(produtosPorDataDeValidade, HttpStatus.OK);
    }

//    public List<Produto> getProdutosCriticos() {
//        List<Produto> produtosCriticos = produtoRepository.findAll().stream()
//                .filter(produto -> produto.getQtdEstoque() <= 2)
//                .collect(Collectors.toList());
//        logger.info("Produtos críticos encontrados: " + produtosCriticos.size());
//        for (Produto produto : produtosCriticos) {
//            logger.info("Produto crítico: " + produto.getNome() + " - Quantidade: " + produto.getQtdEstoque());
//        }
//        return produtosCriticos;
//    }


    public List<Produto> getProdutosModerados() {
        Alerta alerta = alertaRepository.findFirstByOrderByIdAsc();
        int alertaModerado = alerta.getAlertaModerado();

        List<Produto> produtosModerados = produtoRepository.findAll().stream()
                .filter(produto -> produto.getQtdEstoque() <= alertaModerado)
                .collect(Collectors.toList());
        logger.info("Produtos moderados encontrados: " + produtosModerados.size());
        for (Produto produto : produtosModerados) {
            logger.info("Produto moderado: " + produto.getNome() + " - Quantidade: " + produto.getQtdEstoque());
        }
        return produtosModerados;
    }

    public List<Produto> getProdutosCriticos() {
        Alerta alerta = alertaRepository.findFirstByOrderByIdAsc();
        int alertaGrave = alerta.getAlertaGrave();

        List<Produto> produtosCriticos = produtoRepository.findAll().stream()
                .filter(produto -> produto.getQtdEstoque() <= alertaGrave)
                .collect(Collectors.toList());
        logger.info("Produtos críticos encontrados: " + produtosCriticos.size());
        for (Produto produto : produtosCriticos) {
            logger.info("Produto crítico: " + produto.getNome() + " - Quantidade: " + produto.getQtdEstoque());
        }
        return produtosCriticos;
    }

}
