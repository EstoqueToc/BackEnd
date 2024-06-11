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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProdutoConsultaDtoTest {

    @Test
    public void testProdutoConsultaDto() {
        Categoria categoria = new Categoria();
        categoria.setNome("Categoria");

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNomeFantasia("Fantasia");

        Produto produto = new Produto();
        produto.setNomeProduto("Produto");
        produto.setPrecoVendaProduto(10.0);
        produto.setPrecoCompraProduto(5.0);
        produto.setDataEntrada(LocalDate.of(2022, 1, 1));
        produto.setUnidadeMedida("UN");
        produto.setDescricaoProduto("Descrição");
        produto.setCategoria(categoria);
        produto.setFornecedor(fornecedor);
        produto.setDataValidade(LocalDate.of(2023, 1, 1));
        produto.setAlertaEstoque(new ArrayList<>());


        ProdutoConsultaDto dto = new ProdutoConsultaDto(produto);

        assertEquals("Produto", dto.getNomeProduto());
        assertEquals(10.0, dto.getPrecoVendaProduto());
        assertEquals(5.0, dto.getPrecoCompraProduto());
        assertEquals(LocalDate.of(2022, 1, 1), dto.getDataEntrada());
        assertEquals("UN", dto.getUnidadeMedida());
        assertEquals("Descrição", dto.getDescricaoProduto());
        assertEquals(categoria, dto.getCategoria());
        assertEquals(fornecedor, dto.getFornecedor());
        assertEquals(LocalDate.of(2023, 1, 1), dto.getDataValidade());

    }
}
