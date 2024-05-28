package com.example.crud.dto.criacao;

import com.example.crud.dto.criacaoDto.ProdutoCriacaoDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoCriacaoDtoTest {

    @Test
    public void testConstrutorComArgumentos() {
        // Arrange
        String nome = "Produto Teste";
        Double precoDeVenda = 100.0;
        double precoDeCompra = 50.0;
        // LocalDate dataDeEntrada = LocalDate.now();
        String unidadeDeMedida = "unidade";
        String descricao = "Descrição do produto";
        // Categoria categoria = new Categoria();
        // Fornecedor fornecedor = new Fornecedor();
        Integer qtdEstoque = 10;
        // LocalDate dataDeValidade = LocalDate.now();

        // Act
        ProdutoCriacaoDto produto = new ProdutoCriacaoDto(nome, precoDeVenda, precoDeCompra,
                null, unidadeDeMedida, descricao, null, null, qtdEstoque, null);

        // Assert
        assertEquals(nome, produto.getNome());
        assertEquals(precoDeVenda, produto.getPrecoDeVenda());
        assertEquals(precoDeCompra, produto.getPrecoDeCompra());
        assertNull(produto.getDataDeEntrada());
        assertEquals(unidadeDeMedida, produto.getUnidadeDeMedida());
        assertEquals(descricao, produto.getDescricao());
        assertNull(produto.getCategoria());
        assertNull(produto.getFornecedor());
        assertEquals(qtdEstoque, produto.getQtdEstoque());
        assertNull(produto.getDataDeValidade());
    }

    @Test
    public void testConstrutorPadrao() {
        // Arrange
        ProdutoCriacaoDto produto = new ProdutoCriacaoDto();

        // Act

        // Assert
        assertNotNull(produto);
    }
}
