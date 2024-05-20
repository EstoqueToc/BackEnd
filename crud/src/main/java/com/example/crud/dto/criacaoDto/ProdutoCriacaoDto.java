package com.example.crud.dto.criacaoDto;

import com.example.crud.Model.Categoria;
import com.example.crud.Model.Fornecedor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProdutoCriacaoDto {

    @NotBlank
    private String nome;

    @NotNull
    @PositiveOrZero
    private Double precoDeVenda;

    @NotNull
    @PositiveOrZero
    private double precoDeCompra;

    @NotNull
    private LocalDate dataDeEntrada;

    @NotBlank
    private String unidadeDeMedida;

    @NotBlank
    private String descricao;

    @NotNull
    private Categoria categoria;

    @NotNull
    private Fornecedor fornecedor;

    @NotNull
    @PositiveOrZero
    private Integer qtdEstoque;

    private LocalDate dataDeValidade;

    // Construtor com argumentos para inicializar todos os campos
    public ProdutoCriacaoDto(String nome, Double precoDeVenda, double precoDeCompra, LocalDate dataDeEntrada,
                             String unidadeDeMedida, String descricao, Categoria categoria,
                             Fornecedor fornecedor, Integer qtdEstoque, LocalDate dataDeValidade) {
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
    }

    // Construtor padrão sem argumentos
    public ProdutoCriacaoDto() {}
}
