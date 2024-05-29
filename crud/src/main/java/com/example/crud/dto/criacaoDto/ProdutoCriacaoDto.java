package com.example.crud.dto.criacaoDto;

import com.example.crud.Model.Alerta;
import com.example.crud.Model.Categoria;
import com.example.crud.Model.Empresa;
import com.example.crud.Model.Fornecedor;
import com.example.crud.Model.Empresa;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProdutoCriacaoDto {

    @NotBlank(message = "Nome do produto é obrigatório")
    private String nome;

    @NotNull(message = "Preço de venda é obrigatório")
    @PositiveOrZero(message = "Preço de venda deve ser maior ou igual a zero")
    private Double precoDeVenda;


    @NotNull(message = "Preço de compra é obrigatório")
    @PositiveOrZero(message = "Preço de compra deve ser maior ou igual a zero")
    private double precoDeCompra;

    @NotNull(message = "Data de entrada é obrigatória")
    private LocalDate dataDeEntrada;

    @NotBlank(message = "Unidade de medida é obrigatória")
    private String unidadeDeMedida;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;



    @NotNull(message = "Categoria é obrigatória")
    private Categoria categoria;

    @NotNull(message = "Fornecedor é obrigatório")
    private Fornecedor fornecedor;

    @NotNull(message = "Quantidade em estoque é obrigatória")
    @PositiveOrZero(message = "Quantidade em estoque deve ser maior ou igual a zero")
    private Integer qtdEstoque;

    @NotNull(message = "Empresa é obrigatória")
    private Empresa empresaId;

    private LocalDate dataDeValidade;

    @NotNull
    private Empresa empresa;

    private List<Alerta> alertaEstoque;
    public ProdutoCriacaoDto(String nome, Double precoDeVenda, double precoDeCompra, LocalDate dataDeEntrada, String unidadeDeMedida, String descricao, Categoria categoria, Fornecedor fornecedor, Integer qtdEstoque, LocalDate dataDeValidade) {
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

    public ProdutoCriacaoDto() {
    }
}
