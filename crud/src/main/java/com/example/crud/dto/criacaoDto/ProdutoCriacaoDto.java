package com.example.crud.dto.criacaoDto;

import com.example.crud.Model.Alerta;
import com.example.crud.Model.Categoria;
import com.example.crud.Model.Fornecedor;
import com.example.crud.Model.Empresa;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    private Double precoDeCompra;

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

    @NotNull
    private Empresa empresa;

    private List<Alerta> alertaEstoque;
}
