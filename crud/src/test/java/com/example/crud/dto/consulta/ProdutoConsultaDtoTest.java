package com.example.crud.dto.consulta;

import com.example.crud.Model.Categoria;
import com.example.crud.Model.Fornecedor;
import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaDto.ProdutoConsultaDto;
import com.example.crud.dto.criacaoDto.CategoriaCriacaoDto;
import com.example.crud.dto.criacaoDto.FornecedorCriacaoDto;
import com.example.crud.dto.criacaoDto.ProdutoCriacaoDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProdutoConsultaDtoTest {

    @Test
    public void testProdutoConsultaDto() {
        Categoria categoria = new Categoria();
        categoria.setNome("Categoria");

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Fornecedor");

        Produto produto = new Produto();
        produto.setNome("Produto");
        produto.setPrecoDeVenda(10.0);
        produto.setPrecoDeCompra(5.0);
        produto.setDataDeEntrada(LocalDate.of(2022, 1, 1));
        produto.setUnidadeDeMedida("UN");
        produto.setDescricao("Descrição");
        produto.setCategoria(categoria);
        produto.setFornecedor(fornecedor);
        produto.setQtdEstoque(100);
        produto.setDataDeValidade(LocalDate.of(2023, 1, 1));

        ProdutoConsultaDto dto = new ProdutoConsultaDto(produto);

        assertEquals("Produto", dto.getNome());
        assertEquals(10.0, dto.getPrecoDeVenda());
        assertEquals(5.0, dto.getPrecoDeCompra());
        assertEquals(LocalDate.of(2022, 1, 1), dto.getDataDeEntrada());
        assertEquals("UN", dto.getUnidadeDeMedida());
        assertEquals("Descrição", dto.getDescricao());
        assertEquals(categoria, dto.getCategoria());
        assertEquals(fornecedor, dto.getFornecedor());
        assertEquals(100, dto.getQtdEstoque());
        assertEquals(LocalDate.of(2023, 1, 1), dto.getDataDeValidade());
    }
}
