//package com.example.crud.dto.criacao;
//
//import com.example.crud.Model.Categoria;
//import com.example.crud.Model.Fornecedor;
//import com.example.crud.dto.criacaoDto.ProdutoCriacaoDto;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ProdutoCriacaoDtoTest {
//
//    @Test
//    public void testConstrutorComArgumentos() {
//        // Arrange
//        String nome = "Produto Teste";
//        Double precoDeVenda = 100.0;
//        double precoDeCompra = 50.0;
//        LocalDate dataDeEntrada = LocalDate.now();
//        String unidadeDeMedida = "unidade";
//        String descricao = "Descrição do produto";
//        Categoria categoria = new Categoria();
//        Fornecedor fornecedor = new Fornecedor();
//        LocalDate dataDeValidade = LocalDate.now();
//
//        // Act
//        ProdutoCriacaoDto produto = new ProdutoCriacaoDto(nome, precoDeVenda, precoDeCompra, dataDeEntrada, unidadeDeMedida, descricao, categoria, fornecedor, dataDeValidade);
//        // Assert
//        assertEquals(nome, produto.getNomeProduto());
//        assertEquals(precoDeVenda, produto.getPrecoVendaProduto());
//        assertEquals(precoDeCompra, produto.getPrecoCompraProduto());
//        assertEquals(dataDeEntrada, produto.getDataEntrada());
//        assertEquals(unidadeDeMedida, produto.getUnidadeMedida());
//        assertEquals(descricao, produto.getDescricao());
//        assertEquals(categoria, produto.getCategoria());
//        assertEquals(fornecedor, produto.getFornecedor());
//
//
//    }
//
//    @Test
//    public void testConstrutorPadrao() {
//        // Arrange
//        ProdutoCriacaoDto produto = new ProdutoCriacaoDto();
//
//        // Act
//
//        // Assert
//        assertNotNull(produto);
//    }
//}
