package com.example.crud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.example.crud.Model.Categoria;
import com.example.crud.Model.Fornecedor;
import com.example.crud.Model.Produto;
import com.example.crud.excecoes.RecursoNaoEncontradoException;
import com.example.crud.repository.ProdutoRepository;
import com.example.crud.service.EstoqueService;
import com.example.crud.slack.Slack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EstoqueServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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


    @Test
    public void testGetTotalProdutosEmEstoque() {
        // Mock do repository
        when(produtoRepository.findAll()).thenReturn(mockProdutos());

        // Chamada ao serviço
        ResponseEntity<Integer> response = estoqueService.getTotalProdutosEmEstoque();

        // Verificação
        assertNotNull(response.getBody()); // Verifica se a resposta não é nula
        assertEquals(3, response.getBody().intValue()); // Verifica se o total de produtos em estoque é 3

        verify(produtoRepository, times(1)).findAll(); // Verifica se o método foi chamado
    }

    @Test
    public void testGetProdutosPorCategoria() {
        // Mock do repository
        when(produtoRepository.findAll()).thenReturn(mockProdutos());

        // Chamada ao serviço
        ResponseEntity<Map<String, Integer>> response = estoqueService.getProdutosPorCategoria();

        // Verificação
        Map<String, Integer> produtosPorCategoria = response.getBody();
        assertNotNull(produtosPorCategoria); // Verifica se a resposta não é nula
        assertEquals(2, produtosPorCategoria.size()); // Verifica se há 2 categorias

        // Verifica os valores para cada categoria
        assertEquals(3, produtosPorCategoria.get("Eletrônicos").intValue());
        assertEquals(2, produtosPorCategoria.get("Roupas").intValue());

        verify(produtoRepository, times(1)).findAll(); // Verifica se o método foi chamado
    }

    @Test
    public void testGetProdutosPorFornecedor() {
        // Mock do repository
        when(produtoRepository.findAll()).thenReturn(mockProdutos());

        // Chamada ao serviço
        ResponseEntity<Map<String, Integer>> response = estoqueService.getProdutosPorFornecedor();

        // Verificação
        Map<String, Integer> produtosPorFornecedor = response.getBody();
        assertNotNull(produtosPorFornecedor); // Verifica se a resposta não é nula
        assertEquals(2, produtosPorFornecedor.size()); // Verifica se há 2 fornecedores

        // Verifica os valores para cada fornecedor
        assertEquals(2, produtosPorFornecedor.get("Fornecedor A").intValue());
        assertEquals(3, produtosPorFornecedor.get("Fornecedor B").intValue());

        verify(produtoRepository, times(1)).findAll(); // Verifica se o método foi chamado
    }


    @Test
    public void testGetProdutosPorDataDeEntrada() {
        // Mock do repository
        when(produtoRepository.findAll()).thenReturn(mockProdutos());

        // Chamada ao serviço
        ResponseEntity<Map<LocalDate, Integer>> response = estoqueService.getProdutosPorDataDeEntrada();

        // Verificação
        Map<LocalDate, Integer> produtosPorDataDeEntrada = response.getBody();
        assertNotNull(produtosPorDataDeEntrada); // Verifica se a resposta não é nula
        assertEquals(2, produtosPorDataDeEntrada.size()); // Verifica se há 2 datas de entrada

        // Verifica os valores para cada data de entrada
        LocalDate data1 = LocalDate.now(); // Data atual
        LocalDate data2 = LocalDate.now().minusDays(1); // Data de ontem

        assertEquals(2, produtosPorDataDeEntrada.get(data1).intValue()); // Verifica a quantidade para a primeira data
        assertEquals(1, produtosPorDataDeEntrada.get(data2).intValue()); // Verifica a quantidade para a segunda data

        verify(produtoRepository, times(1)).findAll(); // Verifica se o método foi chamado
    }

    @Test
    public void testGetProdutosPorDataDeValidade() {
        when(produtoRepository.findAll()).thenReturn(mockProdutos());

        ResponseEntity<Map<LocalDate, Integer>> response = estoqueService.getProdutosPorDataDeValidade();

        Map<LocalDate, Integer> produtosPorDataDeValidade = response.getBody();
        LocalDate validade = LocalDate.now().plusDays(30);
        assertEquals(30, produtosPorDataDeValidade.get(validade));
        verify(produtoRepository, times(1)).findAll();
    }

   /* @Test
    public void testVerificarAlertas() throws IOException, InterruptedException {
        List<Produto> produtos = mockProdutos();
        when(produtoRepository.findAll()).thenReturn(produtos);
        when(produtoRepository.findAll()).thenReturn(produtos);

        boolean result = estoqueService.verificarAlertas();

        assertEquals(false, result);
        verify(produtoRepository, times(2)).findAll();
    }*/

    @Test
    public void testValidarCodigoProduto() {
        when(produtoRepository.existsById(1L)).thenReturn(true);
        estoqueService.validarCodigoProduto(1L);
        verify(produtoRepository, times(1)).existsById(1L);
    }

    @Test
    public void testGetUm() {
        Produto produto = new Produto(1L, "Produto 1", 10, new Categoria("Categoria 1"), new Fornecedor("Fornecedor 1"), LocalDate.now(), LocalDate.now().plusDays(30));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.existsById(1L)).thenReturn(true);

        Produto result = estoqueService.getUm(1L);

        assertEquals(produto, result);
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    public void testExcluirUm() {
        when(produtoRepository.existsById(1L)).thenReturn(true);

        estoqueService.excluirUm(1L);

        verify(produtoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetAll() {
        List<Produto> produtos = mockProdutos();
        when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> result = estoqueService.getAll();

        assertEquals(produtos, result);
        verify(produtoRepository, times(1)).findAll();
    }
}