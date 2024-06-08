package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EstoqueInfo {

    private String produto;
    private int quantidade;
    private double precoUnitario;
    private String statusEstoque;

    public EstoqueInfo(String produto, int quantidade, double precoUnitario, String statusEstoque) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.statusEstoque = statusEstoque;
    }
}
