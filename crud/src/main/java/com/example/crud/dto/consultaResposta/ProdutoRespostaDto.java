package com.example.crud.dto.consultaResposta;

import com.example.crud.Model.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProdutoRespostaDto {

    private Long id;
    private String nome;
    private Double precoDeVenda;
    private Double precoDeCompra;
    private LocalDate dataDeEntrada;
    private String unidadeDeMedida;
    private String descricao;
    private Categoria categoria;
    private Fornecedor fornecedor;
    private Integer qtdEstoque;
    private LocalDate dataDeValidade;
    private Empresa empresa;
    private List<AlertaRespostaDto> alertaEstoque;

    public ProdutoRespostaDto() {}

    public ProdutoRespostaDto(Long id, String nome, Double precoDeVenda, Double precoDeCompra, LocalDate dataDeEntrada, String unidadeDeMedida, String descricao, Categoria categoria, Fornecedor fornecedor, Integer qtdEstoque, LocalDate dataDeValidade, Empresa empresa, List<AlertaRespostaDto> alertaEstoque) {
        this.id = id;
        this.nome = nome;
        this.precoDeVenda = precoDeVenda;
        this.precoDeCompra = precoDeCompra;
        this.dataDeEntrada = dataDeEntrada;
        this.unidadeDeMedida = unidadeDeMedida;
        this.descricao = descricao;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.qtdEstoque = qtdEstoque;
        this.dataDeValidade = dataDeValidade;
        this.empresa = empresa;
        this.alertaEstoque = alertaEstoque;
    }
}
