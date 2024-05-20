package com.example.crud.service;

import com.example.crud.Model.Produto;
import com.example.crud.excecoes.RecursoNaoEncontradoException;
import com.example.crud.excecoes.ValidacaoException;
import com.example.crud.repository.ProdutoRepository;
import com.example.crud.slack.Slack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EstoqueService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public EstoqueService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Método para obter a quantidade total de produtos no estoque
    public ResponseEntity<Integer> getTotalProdutosEmEstoque() {
        List<Produto> produtos = produtoRepository.findAll();
        int total = produtos.stream()
                .mapToInt(produto -> produto.getQtdEstoque() != null ? produto.getQtdEstoque() : 0) // Tratar quantidade nula como 0
                .sum();
        return ResponseEntity.ok(total);
    }

    // Método para obter a quantidade de produtos por categoria
    public ResponseEntity<Map<String, Integer>> getProdutosPorCategoria() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<String, Integer> produtosPorCategoria = produtos.stream().filter(produto -> produto.getCategoria() != null).collect(Collectors.groupingBy(
                produto -> produto.getCategoria().getNome(),
                Collectors.summingInt(Produto::getQtdEstoque)
        ));
        return new ResponseEntity<>(produtosPorCategoria, HttpStatus.OK);
    }

    // Método para obter a quantidade de produtos por fornecedor
    public ResponseEntity<Map<String, Integer>> getProdutosPorFornecedor() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<String, Integer> produtosPorFornecedor = produtos.stream()
                .filter(produto -> produto.getFornecedor() != null) // Filtrar produtos com fornecedor não nulo
                .collect(Collectors.groupingBy(
                        produto -> produto.getFornecedor().getNome(),
                        Collectors.summingInt(Produto::getQtdEstoque)
                ));
        return new ResponseEntity<>(produtosPorFornecedor, HttpStatus.OK);
    }


    // Método para obter a quantidade de produtos por data de entrada
    public ResponseEntity<Map<LocalDate, Integer>> getProdutosPorDataDeEntrada() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<LocalDate, Integer> produtosPorDataDeEntrada = produtos.stream()
                .filter(produto -> produto.getDataDeEntrada() != null) // Filtrar produtos com data de entrada não nula
                .collect(Collectors.groupingBy(
                        Produto::getDataDeEntrada,
                        Collectors.summingInt(Produto::getQtdEstoque)
                ));
        return new ResponseEntity<>(produtosPorDataDeEntrada, HttpStatus.OK);
    }


    // Método para obter a quantidade de produtos por data de validade
    public ResponseEntity<Map<LocalDate, Integer>> getProdutosPorDataDeValidade() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<LocalDate, Integer> produtosPorDataDeValidade = produtos.stream()
                .filter(produto -> produto.getDataDeValidade() != null) // Filtrar produtos com data de validade não nula
                .collect(Collectors.groupingBy(
                        Produto::getDataDeValidade,
                        Collectors.summingInt(Produto::getQtdEstoque)
                ));
        return new ResponseEntity<>(produtosPorDataDeValidade, HttpStatus.OK);
    }


    // Método para verificar e enviar alertas
    public boolean verificarAlertas() {
        List<Produto> produtos = produtoRepository.findAll();

        if (produtos.isEmpty()) {
            throw new RuntimeException("A lista de produtos está vazia");
        }

        ResponseEntity<Integer> responseEntity = getTotalProdutosEmEstoque();
        int total = responseEntity.getBody();

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

    public void validarCodigoProduto(Long codigo) {
        if (!produtoRepository.existsById(codigo)) {
            throw new RecursoNaoEncontradoException("Produto", codigo);
        }
    }

   /* public void criar(UsuarioCriacaoDto usuarioCriacaoDto) {
        final Produto produto = ProdutoMapper.toEntity(ProdutoCriacaoDto);
        this.produtoRepository.save(produto);
    }*/

    public Produto getUm(Long codigo) {
        validarCodigoProduto(codigo);
        return produtoRepository.findById(codigo).get();
    }

    public void excluirUm(Long codigo) {
        validarCodigoProduto(codigo);
        produtoRepository.deleteById(codigo);
    }

    public List<Produto> getAll() {
        List<Produto> lista = produtoRepository.findAll();

        if (lista.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }

        return lista;
    }

    public void atualizarProduto(Produto produto) {
        if (produto.getId() == null) {
            throw new ValidacaoException("ID do produto não pode ser nulo");
        }

        if (!produtoRepository.existsById(produto.getId())) {
            throw new RecursoNaoEncontradoException("Produto", produto.getId());
        }

        Produto produtoExistente = produtoRepository.findById(produto.getId()).get();
        produtoExistente.setNome(produto.getNome());
        produtoExistente.setPrecoDeVenda(produto.getPrecoDeVenda());
        produtoExistente.setPrecoDeCompra(produto.getPrecoDeCompra());
        produtoExistente.setDataDeEntrada(produto.getDataDeEntrada());
        produtoExistente.setUnidadeDeMedida(produto.getUnidadeDeMedida());
        produtoExistente.setDescricao(produto.getDescricao());
        produtoExistente.setCategoria(produto.getCategoria());
        produtoExistente.setFornecedor(produto.getFornecedor());
        produtoExistente.setQtdEstoque(produto.getQtdEstoque());
        produtoExistente.setDataDeValidade(produto.getDataDeValidade());

        produtoRepository.save(produtoExistente);
    }
}
