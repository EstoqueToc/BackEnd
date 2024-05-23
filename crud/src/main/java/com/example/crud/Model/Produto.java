package com.example.crud.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    @NotBlank
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @NotNull
    @PositiveOrZero
    private Integer qtdEstoque;

    private LocalDate dataDeValidade;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @OneToMany(mappedBy = "produto")
    private List<Alerta> alertas;

    public Produto() {
    }

   public Produto(Long id, String nome, Double precoDeVenda, double precoDeCompra, LocalDate dataDeEntrada, String unidadeDeMedida, String descricao, Categoria categoria, Fornecedor fornecedor, Integer qtdEstoque, Empresa empresa) {
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
        this.empresa = empresa;
    }

    public Produto(Long id, String nome, double precoDeCompra, Categoria categoria, Fornecedor fornecedor, LocalDate dataDeEntrada, LocalDate dataDeValidade) {
        this.id = id;
        this.nome = nome;
        this.precoDeCompra = precoDeCompra;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.dataDeEntrada = dataDeEntrada;
        this.dataDeValidade = dataDeValidade;
    }

}