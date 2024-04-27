package com.example.crud.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    //se vai ser em caixa, unidade, litros etc

    @NotBlank
    private String descricao;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Fornecedor fornecedor;

    @NotNull
    @PositiveOrZero
    private Integer qtdEstoque;

    private LocalDate dataDeValidade;

    public Produto() {
    }

    public Produto(Long id, String nome, Double precoDeVenda, double precoDeCompra, LocalDate dataDeEntrada, String unidadeDeMedida, String descricao, Categoria categoria, Fornecedor fornecedor, Integer qtdEstoque, LocalDate dataDeValidade) {
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
    }
}
