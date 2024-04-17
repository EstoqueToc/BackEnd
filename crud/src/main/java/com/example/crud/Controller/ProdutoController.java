package com.example.crud.Controller;

import com.example.crud.Model.Produto;
import com.example.crud.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @Operation(summary = "Cria um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@Parameter(description = "Objeto do produto a ser criado") @RequestBody @Valid Produto novoProduto) {
        repository.save(novoProduto);
        return ResponseEntity.status(201).body(novoProduto);
    }

    @Operation(summary = "Retorna todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto disponível", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Produto>> getProdutos() {
        var lista = repository.findAll();
        return lista.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(lista);
    }

    @Operation(summary = "Busca produtos com quantidade em estoque maior ou igual ao valor especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos filtrados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado com o estoque especificado", content = @Content)
    })
    @GetMapping("/estoque/{qtdEstoque}")
    public ResponseEntity<List<Produto>> buscarPorEstoque(
            @Parameter(description = "Quantidade de estoque para filtrar os produtos") @PathVariable int qtdEstoque) {
        var produtos = repository.findByQtdEstoqueGreaterThanEqual(qtdEstoque);
        return produtos.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(produtos);
    }

    @Operation(summary = "Busca um produto pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Produto> listarProdutoPorId(
            @Parameter(description = "ID do produto para busca") @PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @Operation(summary = "Busca produtos por uma categoria específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado na categoria especificada", content = @Content)
    })
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Produto>> getProdutosPorCategoria(
            @Parameter(description = "Nome da categoria para filtrar os produtos") @PathVariable String categoria) {
        List<Produto> produtos = repository.findByCategoriaNomeIgnoreCase(categoria);
        return produtos.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(produtos);
    }

    @Operation(summary = "Busca produtos dentro de uma faixa de preço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados dentro da faixa de preço"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado dentro da faixa de preço especificada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados de preço inválidos ou inconsistências nos valores fornecidos", content = @Content)
    })
    @GetMapping("/preco")
    public ResponseEntity<List<Produto>> buscarPorFaixaPreco(
            @Parameter(description = "Preço mínimo para a filtragem de produtos") @RequestParam("minimo") @PositiveOrZero Double precoMinimo,
            @Parameter(description = "Preço máximo para a filtragem de produtos") @RequestParam("maximo") @PositiveOrZero Double precoMaximo) {
        if (precoMinimo == null || precoMaximo == null || precoMinimo > precoMaximo) {
            return ResponseEntity.status(400).build();
        }
        List<Produto> produtosNaFaixa = repository.findByPrecoDeVendaBetween(precoMinimo, precoMaximo);
        return produtosNaFaixa.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(produtosNaFaixa);
    }

    @Operation(summary = "Adiciona estoque ao produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade em estoque atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    @PutMapping("/{id}/estoque")
    public ResponseEntity<String> adicionarEstoque(
            @Parameter(description = "ID do produto para adicionar estoque") @PathVariable Long id,
            @Parameter(description = "Quantidade de estoque a ser adicionada") @RequestParam("qtdEstoque") @NotNull @PositiveOrZero Integer quantidadeAdicional) {
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Produto> alterarProduto(
            @Parameter(description = "ID do produto para atualização") @PathVariable Long id,
            @Parameter(description = "Objeto do produto com dados atualizados") @Valid @RequestBody Produto produtoAtualizado) {
        if (repository.existsById(id)) {
            produtoAtualizado.setId(id);
            repository.save(produtoAtualizado);
            return ResponseEntity.status(200).body(produtoAtualizado);
        }
        return ResponseEntity.status(404).build();
    }

    @Operation(summary = "Deleta um produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do produto para exclusão") @PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
