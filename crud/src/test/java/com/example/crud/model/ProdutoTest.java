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
        produto.setNome("Notebook");
        assertEquals("Notebook", produto.getNome());
    }

    @Test
    @DisplayName("Deve retornar corretamente o preço de venda do produto")
    void getPrecoDeVenda() {
        Produto produto = new Produto();
        produto.setPrecoDeVenda(2999.99);
        assertEquals(2999.99, produto.getPrecoDeVenda());
    }

    @Test
    @DisplayName("Deve retornar corretamente o preço de compra do produto")
    void getPrecoDeCompra() {
        Produto produto = new Produto();
        produto.setPrecoDeCompra(1999.99);
        assertEquals(1999.99, produto.getPrecoDeCompra());
    }

    @Test
    @DisplayName("Deve retornar corretamente a data de entrada do produto")
    void getDataDeEntrada() {
        LocalDate dataEntrada = LocalDate.of(2024, 5, 22);
        Produto produto = new Produto();
        produto.setDataDeEntrada(dataEntrada);
        assertEquals(dataEntrada, produto.getDataDeEntrada());
    }

    @Test
    @DisplayName("Deve retornar corretamente a unidade de medida do produto")
    void getUnidadeDeMedida() {
        Produto produto = new Produto();
        produto.setUnidadeDeMedida("Unidade");
        assertEquals("Unidade", produto.getUnidadeDeMedida());
    }

    @Test
    @DisplayName("Deve retornar corretamente a descrição do produto")
    void getDescricao() {
        Produto produto = new Produto();
        produto.setDescricao("Um notebook de alta performance");
        assertEquals("Um notebook de alta performance", produto.getDescricao());
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
    @DisplayName("Deve retornar corretamente a quantidade em estoque do produto")
    void getQtdEstoque() {
        Produto produto = new Produto();
        produto.setQtdEstoque(50);
        assertEquals(50, produto.getQtdEstoque());
    }

    @Test
    @DisplayName("Deve retornar corretamente a data de validade do produto")
    void getDataDeValidade() {
        LocalDate dataValidade = LocalDate.of(2025, 5, 22);
        Produto produto = new Produto();
        produto.setDataDeValidade(dataValidade);
        assertEquals(dataValidade, produto.getDataDeValidade());
    }

    @Test
    @DisplayName("Deve definir corretamente o nome do produto")
    void setNome() {
        Produto produto = new Produto();
        produto.setNome("Tablet");
        assertEquals("Tablet", produto.getNome());
    }

    @Test
    @DisplayName("Deve definir corretamente o preço de venda do produto")
    void setPrecoDeVenda() {
        Produto produto = new Produto();
        produto.setPrecoDeVenda(1999.99);
        assertEquals(1999.99, produto.getPrecoDeVenda());
    }

    @Test
    @DisplayName("Deve definir corretamente o preço de compra do produto")
    void setPrecoDeCompra() {
        Produto produto = new Produto();
        produto.setPrecoDeCompra(1499.99);
        assertEquals(1499.99, produto.getPrecoDeCompra());
    }

    @Test
    @DisplayName("Deve definir corretamente a data de entrada do produto")
    void setDataDeEntrada() {
        LocalDate dataEntrada = LocalDate.of(2023, 1, 1);
        Produto produto = new Produto();
        produto.setDataDeEntrada(dataEntrada);
        assertEquals(dataEntrada, produto.getDataDeEntrada());
    }

    @Test
    @DisplayName("Deve definir corretamente a unidade de medida do produto")
    void setUnidadeDeMedida() {
        Produto produto = new Produto();
        produto.setUnidadeDeMedida("Litro");
        assertEquals("Litro", produto.getUnidadeDeMedida());
    }

    @Test
    @DisplayName("Deve definir corretamente a descrição do produto")
    void setDescricao() {
        Produto produto = new Produto();
        produto.setDescricao("Um tablet de alta performance");
        assertEquals("Um tablet de alta performance", produto.getDescricao());
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
    @DisplayName("Deve definir corretamente a quantidade em estoque do produto")
    void setQtdEstoque() {
        Produto produto = new Produto();
        produto.setQtdEstoque(100);
        assertEquals(100, produto.getQtdEstoque());
    }

    @Test
    @DisplayName("Deve definir corretamente a data de validade do produto")
    void setDataDeValidade() {
        LocalDate dataValidade = LocalDate.of(2024, 12, 31);
        Produto produto = new Produto();
        produto.setDataDeValidade(dataValidade);
        assertEquals(dataValidade, produto.getDataDeValidade());
    }
}
