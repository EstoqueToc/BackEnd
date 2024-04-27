package com.example.crud.dto.criacaoDto;

import com.example.crud.Model.Categoria;
import com.example.crud.Model.Fornecedor;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.time.LocalDate;

@Getter
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

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Fornecedor fornecedor;

    @NotNull
    @PositiveOrZero
    private Integer qtdEstoque;

    private LocalDate dataDeValidade;


}
