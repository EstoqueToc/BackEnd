package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Categoria;
import com.example.crud.Model.Fornecedor;
import com.example.crud.Model.Produto;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class ProdutoConsultaDto {


    private Long id;
    private String nome;
    private Double precoDeVenda;
    private double precoDeCompra;
    private LocalDate dataDeEntrada;
    private String unidadeDeMedida;
    private String descricao;
    private Categoria categoria;
    private Fornecedor fornecedor;
    private Integer qtdEstoque;
    private LocalDate dataDeValidade;


    public ProdutoConsultaDto(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.precoDeVenda = produto.getPrecoDeVenda();
        this.precoDeCompra = produto.getPrecoDeCompra();
        this.dataDeEntrada = produto.getDataDeEntrada();
        this.unidadeDeMedida = produto.getUnidadeDeMedida();
        this.descricao = produto.getDescricao();
        this.categoria = produto.getCategoria();
        this.fornecedor = produto.getFornecedor();
        this.qtdEstoque = produto.getQtdEstoque();
        this.dataDeValidade = produto.getDataDeValidade();
    }
}
