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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProdutoCriacaoDto {

    @NotBlank(message = "Nome do produto é obrigatório")
    private String nomeProduto;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricaoProduto;

    private LocalDate dataValidade;

    @NotNull(message = "Preço de compra é obrigatório")
    @PositiveOrZero(message = "Preço de compra deve ser maior ou igual a zero")
    private double precoCompraProduto;

    @NotNull(message = "Preço de venda é obrigatório")
    @PositiveOrZero(message = "Preço de venda deve ser maior ou igual a zero")
    private Double precoVendaProduto;

    @NotNull(message = "Data de entrada é obrigatória")
    private LocalDate dataEntrada;

    @NotBlank(message = "Unidade de medida é obrigatória")
    private String unidadeMedida;

    @NotNull(message = "Quantidade de Entrada é obrigatória")
    @Positive(message = "Quantidade de Entrada deve ser maior que zero")
    private Integer qtdEntrada;

    @NotNull(message = "Categoria é obrigatória")
    private Categoria categoria;

    @NotNull(message = "Fornecedor é obrigatório")
    private Fornecedor fornecedor;

    @NotNull(message = "Empresa é obrigatória")
    private Empresa empresa;

    private List<Alerta> alerta;

    public ProdutoCriacaoDto() {
    }
}
