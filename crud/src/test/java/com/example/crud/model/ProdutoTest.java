package com.example.crud.model;

import com.example.crud.Model.Categoria;
import com.example.crud.Model.Fornecedor;
import com.example.crud.Model.Produto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoTest {

    @Test
    @DisplayName("Deve retornar corretamente o nome do produto")
    void getNome() {
        Produto produto = new Produto();
        produto.setNomeProduto("Notebook");
        assertEquals("Notebook", produto.getNomeProduto());
    }

    @Test
    @DisplayName("Deve retornar corretamente o preço de venda do produto")
    void getPrecoDeVenda() {
        Produto produto = new Produto();
        produto.setPrecoVendaProduto(2999.99);
        assertEquals(2999.99, produto.getPrecoVendaProduto());
    }

    @Test
    @DisplayName("Deve retornar corretamente o preço de compra do produto")
    void getPrecoDeCompra() {
        Produto produto = new Produto();
        produto.setPrecoCompraProduto(1999.99);
        assertEquals(1999.99, produto.getPrecoCompraProduto());
    }

    @Test
    @DisplayName("Deve retornar corretamente a data de entrada do produto")
    void getDataDeEntrada() {
        LocalDate dataEntrada = LocalDate.of(2024, 5, 22);
        Produto produto = new Produto();
        produto.setDataEntrada(dataEntrada);
        assertEquals(dataEntrada, produto.getDataEntrada());
    }

    @Test
    @DisplayName("Deve retornar corretamente a unidade de medida do produto")
    void getUnidadeDeMedida() {
        Produto produto = new Produto();
        produto.setUnidadeMedida("Unidade");
        assertEquals("Unidade", produto.getUnidadeMedida());
    }

    @Test
    @DisplayName("Deve retornar corretamente a descrição do produto")
    void getDescricao() {
        Produto produto = new Produto();
        produto.setDescricaoProduto("Um notebook de alta performance");
        assertEquals("Um notebook de alta performance", produto.getDescricaoProduto());
    }

    @Test
    @DisplayName("Deve retornar corretamente a categoria do produto")
    void getCategoria() {
        Categoria categoria = new Categoria();
        Produto produto = new Produto();
        produto.setCategoria(categoria);
        assertEquals(categoria, produto.getCategoria());
    }

    @Test
    @DisplayName("Deve retornar corretamente o fornecedor do produto")
    void getFornecedor() {
        Fornecedor fornecedor = new Fornecedor();
        Produto produto = new Produto();
        produto.setFornecedor(fornecedor);
        assertEquals(fornecedor, produto.getFornecedor());
    }


    @Test
    @DisplayName("Deve retornar corretamente a data de validade do produto")
    void getDataDeValidade() {
        LocalDate dataValidade = LocalDate.of(2025, 5, 22);
        Produto produto = new Produto();
        produto.setDataValidade(dataValidade);
        assertEquals(dataValidade, produto.getDataValidade());
    }

    @Test
    @DisplayName("Deve definir corretamente o nome do produto")
    void setNome() {
        Produto produto = new Produto();
        produto.setNomeProduto("Tablet");
        assertEquals("Tablet", produto.getNomeProduto());
    }

    @Test
    @DisplayName("Deve definir corretamente o preço de venda do produto")
    void setPrecoDeVenda() {
        Produto produto = new Produto();
        produto.setPrecoVendaProduto(1999.99);
        assertEquals(1999.99, produto.getPrecoVendaProduto());
    }

    @Test
    @DisplayName("Deve definir corretamente o preço de compra do produto")
    void setPrecoDeCompra() {
        Produto produto = new Produto();
        produto.setPrecoCompraProduto(1499.99);
        assertEquals(1499.99, produto.getPrecoCompraProduto());
    }

    @Test
    @DisplayName("Deve definir corretamente a data de entrada do produto")
    void setDataDeEntrada() {
        LocalDate dataEntrada = LocalDate.of(2023, 1, 1);
        Produto produto = new Produto();
        produto.setDataEntrada(dataEntrada);
        assertEquals(dataEntrada, produto.getDataEntrada());
    }

    @Test
    @DisplayName("Deve definir corretamente a unidade de medida do produto")
    void setUnidadeDeMedida() {
        Produto produto = new Produto();
        produto.setUnidadeMedida("Litro");
        assertEquals("Litro", produto.getUnidadeMedida());
    }

    @Test
    @DisplayName("Deve definir corretamente a descrição do produto")
    void setDescricao() {
        Produto produto = new Produto();
        produto.setDescricaoProduto("Um tablet de alta performance");
        assertEquals("Um tablet de alta performance", produto.getDescricaoProduto());
    }

    @Test
    @DisplayName("Deve definir corretamente a categoria do produto")
    void setCategoria() {
        Categoria categoria = new Categoria();
        Produto produto = new Produto();
        produto.setCategoria(categoria);
        assertEquals(categoria, produto.getCategoria());
    }

    @Test
    @DisplayName("Deve definir corretamente o fornecedor do produto")
    void setFornecedor() {
        Fornecedor fornecedor = new Fornecedor();
        Produto produto = new Produto();
        produto.setFornecedor(fornecedor);
        assertEquals(fornecedor, produto.getFornecedor());
    }

    @Test
    @DisplayName("Deve definir corretamente a data de validade do produto")
    void setDataDeValidade() {
        LocalDate dataValidade = LocalDate.of(2024, 12, 31);
        Produto produto = new Produto();
        produto.setDataValidade(dataValidade);
        assertEquals(dataValidade, produto.getDataValidade());
    }
}
