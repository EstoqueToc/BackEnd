package com.example.crud.Controller;

import com.example.crud.GerenciadorArquivo.ProdutoCSV;
import com.example.crud.Helpers.ListaObj;
import com.example.crud.Model.Alerta;
import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaDto.ProdutoConsultaDto;
import com.example.crud.dto.consultaResposta.ProdutoRespostaDto;
import com.example.crud.dto.criacaoDto.ProdutoCriacaoDto;

import com.example.crud.repository.AlertaRepository;
import com.example.crud.service.ProdutoService;
import com.example.crud.service.usuario.EstoqueService;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private ModelMapper modelMapper;

    private ProdutoCSV produtoCSV;
    private EstoqueService estoqueService;


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
        List<ProdutoConsultaDto> listaDto = produtoService.getProdutos();
        return listaDto.isEmpty() ? status(204).build() : status(200).body(listaDto);
    }

    @Operation(summary = "Busca produtos com quantidade em estoque maior ou igual ao valor especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos filtrados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado com o estoque especificado", content = @Content)
    })
    @GetMapping("/estoque/{qtdEstoque}")
    public ResponseEntity<List<Produto>> buscarPorEstoque(
            @Parameter(description = "Quantidade de estoque para filtrar os produtos") @PathVariable int qtdEstoque) {
        List<Produto> produtos = produtoService.buscarPorEstoque(qtdEstoque);
        return produtos.isEmpty() ? status(204).build() : status(200).body(produtos);
    }

    @Operation(summary = "Busca um produto pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoConsultaDto> listarProdutoPorId(
            @Parameter(description = "ID do produto para busca") @PathVariable Long id) {
        Optional<ProdutoConsultaDto> produtoOpt = produtoService.listarProdutoPorId(id);
        return produtoOpt.map(ResponseEntity::ok)
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
        List<Produto> produtos = produtoService.getProdutosPorCategoria(categoria);
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
        List<Produto> produtosNaFaixa = produtoService.buscarPorFaixaPreco(precoMinimo, precoMaximo);
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
        Optional<Produto> produtoOpt = produtoService.adicionarEstoque(id, quantidadeAdicional);
        return produtoOpt.map(produto -> ok("Quantidade em estoque atualizada com sucesso."))
                .orElseGet(() -> status(404).body("Produto não encontrado."));
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
        Optional<Produto> produtoOpt = produtoService.alterarProduto(id, produtoAtualizado);
        return produtoOpt.map(produto -> status(200).body(produto))
                .orElseGet(() -> status(404).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do produto para exclusão") @PathVariable Long id) {
        if (produtoService.deletarProduto(id)) {
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
        List<Produto> listaOrdenada = produtoService.listarProdutos();
        return listaOrdenada.isEmpty() ? status(204).build() : status(200).body(listaOrdenada);
    }

    @Operation(summary = "Ordena os produtos por preço de venda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos ordenados por preço de venda com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto disponível para ordenação por preço", content = @Content)
    })
    @GetMapping("/ordenar-preco")
    public ResponseEntity<List<Produto>> ordenarPorPreco() {
        List<Produto> produtos = produtoService.ordenarPorPreco();
        return produtos.isEmpty() ? status(204).build() : status(200).body(produtos);
    }

    @Operation(summary = "Lista os produtos por data de validade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados por data de validade com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto disponível para listar por data de validade", content = @Content)
    })
    @GetMapping("/ordenar-validade")
    public ResponseEntity<List<Produto>> listarPorValidade() {
        List<Produto> produtos = produtoService.listarPorValidade();
        return produtos.isEmpty() ? status(204).build() : status(200).body(produtos);
    }

    @Operation(summary = "Lista os produtos por data de entrada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados por data de entrada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto disponível para listar por data de entrada", content = @Content)
    })
    @GetMapping("/ordenar-entrada")
    public ResponseEntity<List<Produto>> listarPorDataEntrada() {
        List<Produto> produtos = produtoService.listarPorDataEntrada();
        return produtos.isEmpty() ? status(204).build() : status(200).body(produtos);
    }

    @Operation(summary = "Lista os produtos por quantidade de estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados por quantidade de estoque com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto disponível para listar por quantidade de estoque", content = @Content)
    })
    @GetMapping("/ordenar-estoque")
    public ResponseEntity<List<Produto>> listarPorEstoque() {
        List<Produto> produtos = produtoService.listarPorEstoque();
        return produtos.isEmpty() ? status(204).build() : status(200).body(produtos);
    }

    @Operation(summary = "Pesquisa produtos por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados com o nome especificado"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado com o nome especificado", content = @Content)
    })
    @GetMapping("/pesquisa-produto/{nome}")
    public ResponseEntity<List<Produto>> pesquisarProdutoPorNome(@Parameter(description = "Nome do produto para pesquisa") @PathVariable String nome) {
        List<Produto> produtos = produtoService.pesquisarProdutoPorNome(nome);
        return produtos.isEmpty() ? status(204).build() : status(200).body(produtos);
    }

    // Endpoints para consumir as classes 'ProdutoCSV'
    @Operation(summary = "Grava arquivo CSV de Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo CSV de Produtos gravado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao gravar arquivo CSV de Produtos", content = @Content)
    })
    @PostMapping("/csv/produto")
    public ResponseEntity<String> gravaArquivoCsvProduto() {
        ListaObj<Produto> lista = new ListaObj<>(100);
        lista.adicionaLista(produtoService.getProdutos().stream()
                .map(dto -> modelMapper.map(dto, Produto.class))
                .collect(Collectors.toList()));
        ProdutoCSV.gravaArquivoCsv(lista, "produtos");
        return ok("Gravando arquivo CSV de Produtos");
    }

    @Operation(summary = "Lê arquivo CSV de Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo CSV de Produtos lido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao ler arquivo CSV de Produtos", content = @Content)
    })
    @GetMapping("/csv/produto")
    public ResponseEntity<String> leArquivoCsvProduto() {
        ProdutoCSV.lerArquivoCsv("produtos");
        return ok("Lendo arquivo CSV de Produtos");
    }

   /* @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarProduto(
            @Parameter(description = "ID do produto para atualização") @PathVariable Long id,
            @Parameter(description = "Objeto do produto com dados atualizados") @Valid @RequestBody Produto produtoAtualizado) {
        try {
            if (repository.existsById(id)) {
                produtoAtualizado.setId(id); // Garante que o ID do produto seja o mesmo do path da requisição
                repository.save(produtoAtualizado);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }*/

}