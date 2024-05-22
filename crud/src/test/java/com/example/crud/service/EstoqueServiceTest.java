package com.example.crud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.crud.Model.Categoria;
import com.example.crud.Model.Fornecedor;
import com.example.crud.Model.Produto;
import com.example.crud.excecoes.RecursoNaoEncontradoException;
import com.example.crud.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class EstoqueServiceTest {

    EstoqueService estoqueService;
    @Mock
    ProdutoRepository produtoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        estoqueService = new EstoqueService(produtoRepository);
    }

    private List<Produto> mockProdutos() {
        Categoria categoriaEletronicos = new Categoria("Eletrônicos");
        Categoria categoriaRoupas = new Categoria("Roupas");
        Fornecedor fornecedorA = new Fornecedor("Fornecedor A");
        Fornecedor fornecedorB = new Fornecedor("Fornecedor B");

        Produto produto1 = new Produto(1L, "Produto 1", 10, categoriaEletronicos, fornecedorA, LocalDate.now(), LocalDate.now().plusDays(30));
        Produto produto2 = new Produto(2L, "Produto 2", 20, categoriaRoupas, fornecedorB, LocalDate.now(), LocalDate.now().plusDays(30));
        Produto produto3 = new Produto(3L, "Produto 3", 15, categoriaEletronicos, fornecedorA, LocalDate.now().minusDays(1), LocalDate.now().plusDays(30));

        return Arrays.asList(produto1, produto2, produto3);
    }

    @DisplayName("Se o id existir, deve retornar o produto")
    @Test
    public void testGetUm() {
        Long codigo = 1L;
        Produto esperado = mock(Produto.class);

        when(produtoRepository.existsById(codigo)).thenReturn(true);
        when(produtoRepository.findById(codigo)).thenReturn(Optional.of(esperado));

        Produto resultado = estoqueService.getUm(codigo);
        assertEquals(esperado, resultado);
    }

    @DisplayName("Se o id não existir, deve lançar exceção")
    @Test
    public void testGetUmNaoEncontrado() {
        Long codigo = 51L;
        assertThrows(RecursoNaoEncontradoException.class, () -> estoqueService.getUm(codigo));
    }

    @DisplayName("Se o id existir, ao tentar excluir deve funcionar")
    @Test
    public void testExcluirUmOk() {
        Long codigo = 1L;

        when(produtoRepository.existsById(codigo)).thenReturn(true);
        assertDoesNotThrow(() -> estoqueService.excluirUm(codigo));
    }

    @DisplayName("Se o id não existir, ao tentar excluir deve lançar exceção")
    @Test
    public void testExcluirUmNaoEncontrado() {
        Long codigo = 51L;
        assertThrows(RecursoNaoEncontradoException.class, () -> estoqueService.excluirUm(codigo));
    }

    @DisplayName("Caso existam produtos, deve retornar a lista")
    @Test
    public void testGetAllComValores() {
        List<Produto> listaEsperada = mockProdutos();

        when(produtoRepository.findAll()).thenReturn(listaEsperada);

        List<Produto> resultado = estoqueService.getAll();

        assertEquals(listaEsperada.size(), resultado.size());
        for (int i = 0; i < listaEsperada.size(); i++) {
            assertEquals(listaEsperada.get(i), resultado.get(i));
        }
    }

    @DisplayName("Deve retornar o total de produtos em estoque")
    @Test
    public void testGetTotalProdutosEmEstoque() {
        // Mock do repository
        when(produtoRepository.findAll()).thenReturn(mockProdutos());

        // Chamada ao serviço
        ResponseEntity<Integer> response = estoqueService.getTotalProdutosEmEstoque();

        // Verificação do status da resposta
        assertEquals(200, response.getStatusCodeValue()); // Verifica se o status da resposta é 200 (OK)

        // Verificação do corpo da resposta
        Integer totalProdutosEmEstoque = response.getBody(); // Obtenha o corpo da resposta
        assertNotNull(totalProdutosEmEstoque); // Verifica se a resposta não é nula
        assertEquals(3, totalProdutosEmEstoque.intValue()); // Verifica se o total de produtos em estoque é 3

        verify(produtoRepository, times(1)).findAll(); // Verifica se o método foi chamado
    }


    @Test
    public void testGetProdutosPorCategoria() {
        // Mock do repository
        List<Produto> produtos = mockProdutos();
        when(produtoRepository.findAll()).thenReturn(produtos);

        // Chamada ao serviço
        ResponseEntity<Map<String, Integer>> response = estoqueService.getProdutosPorCategoria();

        // Verificação
        Map<String, Integer> produtosPorCategoria = response.getBody();
        assertNotNull(produtosPorCategoria); // Verifica se a resposta não é nula
        assertEquals(2, produtosPorCategoria.size()); // Verifica se há 2 categorias

        // Verifica os valores para cada categoria
        assertEquals(3, produtosPorCategoria.get("Categoria 1").intValue());
        assertEquals(2, produtosPorCategoria.get("Categoria 2").intValue());

        verify(produtoRepository, times(1)).findAll(); // Verifica se o método foi chamado
    }

    public ResponseEntity<Map<String, Integer>> getProdutosPorFornecedor() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<String, Integer> produtosPorFornecedor = produtos.stream()
                .filter(produto -> produto.getFornecedor() != null) // Filtra produtos com fornecedor não nulo
                .collect(Collectors.groupingBy(produto -> produto.getFornecedor().getNome(), Collectors.summingInt(produto -> 1)));
        return ResponseEntity.ok(produtosPorFornecedor);
    }

    @Test
    public ResponseEntity<Map<LocalDate, Integer>> getProdutosPorDataDeEntrada() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<LocalDate, Integer> produtosPorDataDeEntrada = produtos.stream()
                .filter(produto -> produto.getDataDeEntrada() != null) // Filtra produtos com data de entrada não nula
                .collect(Collectors.groupingBy(Produto::getDataDeEntrada, Collectors.summingInt(produto -> 1)));
        return ResponseEntity.ok(produtosPorDataDeEntrada);
    }

    @Test
    public void testGetProdutosPorDataDeValidade() {
        // Mock do repository
        List<Produto> mockProdutos = mockProdutos();
        when(produtoRepository.findAll()).thenReturn(mockProdutos);

        // Chamada ao serviço
        ResponseEntity<Map<LocalDate, Integer>> response = estoqueService.getProdutosPorDataDeValidade();

        // Verificação do status da resposta
        assertEquals(200, response.getStatusCodeValue()); // Verifica se o status da resposta é 200 (OK)

        // Verificação dos resultados
        Map<LocalDate, Integer> produtosPorDataDeValidade = response.getBody();
        assertNotNull(produtosPorDataDeValidade); // Verifica se a resposta não é nula
        assertEquals(1, produtosPorDataDeValidade.size()); // Verifica se há 1 data de validade

        // Verifica a quantidade de produtos para a data de validade esperada
        LocalDate dataValidade = LocalDate.now().plusDays(30);
        assertEquals(30, produtosPorDataDeValidade.get(dataValidade).intValue());

        verify(produtoRepository, times(1)).findAll(); // Verifica se o método foi chamado
    }

    @DisplayName("Deve validar código de produto existente")
    @Test
    public void testValidarCodigoProduto() {
        when(produtoRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> estoqueService.validarCodigoProduto(1L));
        verify(produtoRepository, times(1)).existsById(1L);
    }
}
