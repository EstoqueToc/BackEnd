package com.example.crud.Controller;

import com.example.crud.Interface.IUpDate;
import com.example.crud.Model.Produto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/produtos")
public class ProdutoController implements IUpDate {
    private List<Produto> produtos = new ArrayList<>();


    //criando um novo produto
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@Valid @RequestBody Produto novoProduto) {
        produtos.add(novoProduto);
        return ResponseEntity.status(201).body(novoProduto);
    }

    // obtendo todos os produtos da lista
    @GetMapping
    public ResponseEntity<List<Produto>> getProdutos() {
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos);
    }


    // vai obter produtos que tenha no estoque o valor escolhido pelo usuário
    @GetMapping("/estoque/{qtdEstoque}")
    public List<Produto> buscarPorEstoque(
            @PathVariable int qtdEstoque) {
        return produtos
                .stream().
                filter(produtodaVez -> produtodaVez.getQtdEstoque() >= qtdEstoque).toList();
    }

    //obter o produto pelo indice
    @GetMapping("/{indice}")
    public ResponseEntity<Produto> get(@PathVariable int indice) {
        if (indice >=0 && indice < produtos.size()) {
            return ResponseEntity.status(200).body(produtos.get(indice));
        }
        return ResponseEntity.status(404).build();
    }

    //obtendo os produtos por categoria por exemplo o usuário escolhe Ferramentas e irá aparecer martelos,furadeira,serras.
    @GetMapping("/categoria/{categoria}")
    public List<Produto> getProdutosPorCategoria(@PathVariable String categoria) {
        return produtos.stream()
                .filter(produto -> produto.getCategoria().equals(categoria)).collect(Collectors.toList());
    }


    //obtendo produtos com base no preço filtrado pelo usuário
    @GetMapping("/preco")
    public ResponseEntity<List<Produto>> buscarPorFaixaPreco(@RequestParam("minimo") @PositiveOrZero Double precoMinimo,
            @RequestParam("maximo") @PositiveOrZero Double precoMaximo) {
        if (precoMinimo == null || precoMaximo == null || precoMinimo > precoMaximo) {

            return ResponseEntity.status(400).build();
        }

        List<Produto> produtosNaFaixa = produtos.stream()
                .filter(produto -> produto.getPrecoDeVenda() >= precoMinimo && produto.getPrecoDeVenda() <= precoMaximo)
                .collect(Collectors.toList());

        if (produtosNaFaixa.isEmpty()) {

            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(produtosNaFaixa);
    }


    //adicionando produtos no estoque de um produto existente
    @PutMapping("/{indice}/estoque")
    public ResponseEntity<String> adicionarEstoque(@PathVariable int indice,
                                                   @RequestParam("qtdEstoque") @NotNull @PositiveOrZero Integer quantidadeAdicional) {
        if (indice >= 0 && indice < produtos.size()) {
            Produto produto = produtos.get(indice);
            int quantidadeAtual = produto.getQtdEstoque();
            produto.setQtdEstoque(quantidadeAtual + quantidadeAdicional);
            return ResponseEntity.status(200).body("Quantidade em estoque atualizada com sucesso.");
        } else {
            return ResponseEntity.status(404).body("Produto não encontrado.");
        }
    }


    //IDÉIA PARA DEVOLUÇÃO
    // SE QUEBROU, VENCEU, PERDA DE PRODUTO

//    @PutMapping("/{indice}/estoque")
//    public ResponseEntity<String> tirarEstoque(@PathVariable int indice,
//                                                   @RequestParam("qtdEstoque") @NotNull @PositiveOrZero Integer quantidadeAdicional) {
//        if (indice >= 0 && indice < produtos.size()) {
//            Produto produto = produtos.get(indice);
//            int quantidadeAtual = produto.getQtdEstoque();
//            produto.setQtdEstoque(quantidadeAtual + quantidadeAdicional);
//            return ResponseEntity.status(200).body("Quantidade em estoque atualizada com sucesso.");
//        } else {
//            return ResponseEntity.status(404).body("Produto não encontrado.");
//        }
//    }


    //atualizando informações do produto
    @PutMapping("/{indice}")
    public ResponseEntity<String> atualizarProduto(@PathVariable int indice,@Valid @RequestBody Produto produto) {
            produtos.set(indice, produto);
            return ResponseEntity.status(200).body("Produto atualizado com sucesso.");
    }

    //deletando produto
    @DeleteMapping("/{indice}")
    public Produto delete(@PathVariable int indice){
        return produtos.remove(indice);
    }

    //Atualizar o valor/desconto
    @Override
    public ResponseEntity<String> aplicarDesconto(@PathVariable int indice, @RequestParam ("percentualDesconto") double percentualDesconto) {
        if (indice >= 0 && indice < produtos.size() && percentualDesconto >= 0) {
            Produto produto = produtos.get(indice);
            double precoComDesconto = produto.getPreceDeVenda() * (1 - percentualDesconto / 100);
            produto.setPrecoDeVenda(precoComDesconto);
            return ResponseEntity.status(200).body("Desconto aplicado com sucesso.");
        } else {
            return ResponseEntity.status(404).body("Produto não encontrado ou percentual de desconto inválido.");
        }
    }
}

