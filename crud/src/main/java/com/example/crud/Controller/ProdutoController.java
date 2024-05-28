package com.example.crud.Controller;

import com.example.crud.GerenciadorArquivo.ProdutoCSV;
import com.example.crud.Helpers.ListaObj;
import com.example.crud.Model.Alerta;
import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaDto.ProdutoConsultaDto;
import com.example.crud.dto.consultaResposta.ProdutoRespostaDto;
import com.example.crud.dto.criacaoDto.ProdutoCriacaoDto;
import com.example.crud.repository.AlertaRepository;
import com.example.crud.repository.ProdutoRepository;
import com.example.crud.service.usuario.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private ModelMapper modelMapper;

    private ProdutoCSV produtoCSV;

    @Autowired
    private ProdutoService produtoService;


    @Operation(summary = "Cria um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProdutoRespostaDto> criarProduto(@Parameter(description = "Objeto do produto a ser criado") @RequestBody @Valid ProdutoCriacaoDto novoProdutoDto) {
        ProdutoRespostaDto produtoCriadoDto = produtoService.criarProduto(novoProdutoDto);
        return status(201).body(produtoCriadoDto);
    }



    @Operation(summary = "Retorna todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto disponível", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ProdutoConsultaDto>> getProdutos() {
        var lista = repository.findAll();
        List<ProdutoConsultaDto> listaDto = lista.stream()
                .map(produto -> modelMapper.map(produto, ProdutoConsultaDto.class))
                .collect(Collectors.toList());
        return lista.isEmpty() ? status(204).build() : status(200).body(listaDto);
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
        return produtos.isEmpty() ? status(204).build() : status(200).body(produtos);
    }

    @Operation(summary = "Busca um produto pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    public ResponseEntity<ProdutoConsultaDto> listarProdutoPorId(
            @Parameter(description = "ID do produto para busca") @PathVariable Long id) {
        var produtoOpt = repository.findById(id);
        return produtoOpt.map(produto -> status(200).body(modelMapper.map(produto, ProdutoConsultaDto.class)))
                .orElseGet(() -> status(404).build());
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
        return produtos.isEmpty() ? status(204).build() : status(200).body(produtos);
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
            return status(400).build();
        }
        List<Produto> produtosNaFaixa = repository.findByPrecoDeVendaBetween(precoMinimo, precoMaximo);
        return produtosNaFaixa.isEmpty() ? status(204).build() : status(200).body(produtosNaFaixa);
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
            return ok("Quantidade em estoque atualizada com sucesso.");
        }
        return status(404).body("Produto não encontrado.");
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
            return status(200).body(produtoAtualizado);
        }
        return status(404).build();
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
            return status(204).build();
        }
        return status(404).build();
    }

    @Operation(summary = "Lista os produtos em ordem alfabética")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados em ordem alfabética com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto disponível para listar", content = @Content)
    })
    @GetMapping("/lista-produto")
    public ResponseEntity<List<Produto>> listarProdutos() {
        var listaOrdenada = repository.findAllByOrderByNomeAsc();
        return listaOrdenada.isEmpty() ? status(204).build() : status(200).body(listaOrdenada);
    }

    @Operation(summary = "Ordena os produtos por preço de venda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos ordenados por preço de venda com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto disponível para ordenação por preço", content = @Content)
    })
    @GetMapping("/ordenar-preco")
    public ResponseEntity<List<Produto>> ordenarPorPreco() {
        List<Produto> produtos = repository.findAllByOrderByPrecoDeVendaAsc();
        return produtos.isEmpty() ? status(204).build() : status(200).body(produtos);
    }

    @Operation(summary = "Lista os produtos por data de validade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados por data de validade com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto disponível para listar por data de validade", content = @Content)
    })
    @GetMapping("/ordenar-validade")
    public ResponseEntity<List<Produto>> listarPorValidade() {
        List<Produto> produtos = repository.findAllByOrderByDataDeValidadeAsc();
        return produtos.isEmpty() ? status(204).build() : status(200).body(produtos);
    }

    @Operation(summary = "Lista os produtos por data de entrada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados por data de entrada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto disponível para listar por data de entrada", content = @Content)
    })
    @GetMapping("/ordenar-entrada")
    public ResponseEntity<List<Produto>> listarPorDataEntrada() {
        List<Produto> produtos = repository.findAllByOrderByDataDeEntradaAsc();
        return produtos.isEmpty() ? status(204).build() : status(200).body(produtos);
    }

    @Operation(summary = "Lista os produtos por quantidade de estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados por quantidade de estoque com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto disponível para listar por quantidade de estoque", content = @Content)
    })
    @GetMapping("/ordenar-estoque")
    public ResponseEntity<List<Produto>> listarPorEstoque() {
        List<Produto> produtos = repository.findAllByOrderByQtdEstoqueAsc();
        return produtos.isEmpty() ? status(204).build() : status(200).body(produtos);
    }

    @Operation(summary = "Pesquisa produtos por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados com o nome especificado"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado com o nome especificado", content = @Content)
    })
    @GetMapping("/pesquisa-produto/{nome}")
    public ResponseEntity<List<Produto>> pesquisarProdutoPorNome(@Parameter(description = "Nome do produto para pesquisa") @PathVariable String nome) {
        List<Produto> produtos = repository.findByNomeContainsIgnoreCase(nome);
        return produtos.isEmpty() ? status(204).build() : status(200).body(produtos);
    }

    //endpoints para consumir as classes 'UsuarioCSV'
    @Operation(summary = "Grava arquivo CSV de Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo CSV de Produtos gravado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao gravar arquivo CSV de Produtos", content = @Content)
    })
    @PostMapping("/csv/produto")
    public ResponseEntity<String> gravaArquivoCsvUsuario() {
        ListaObj<Produto> lista = new ListaObj<>(100);
        lista.adicionaLista(repository.findAll());
        ProdutoCSV.gravaArquivoCsv(lista, "produtos");
        return ok("Gravando arquivo CSV de Produtos");
    }

    @Operation(summary = "Lê arquivo CSV de Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo CSV de Produtos lido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao ler arquivo CSV de Produtos", content = @Content)
    })
    @GetMapping("/csv/produto")
    public ResponseEntity<String> leArquivoCsvUsuario() {
        ProdutoCSV.lerArquivoCsv("produtos");
        return ok("Lendo arquivo CSV de Produtos");
    }

}