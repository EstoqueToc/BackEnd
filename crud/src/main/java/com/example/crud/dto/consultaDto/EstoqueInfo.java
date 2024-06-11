package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class EstoqueInfo {

    private Long id;
    private String produto;
    private int quantidade;
    private double precoUnitario;
    private String statusEstoque;

    public EstoqueInfo(Long id, String produto, int quantidade, double precoUnitario, String statusEstoque) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.statusEstoque = statusEstoque;
    }

    public EstoqueInfo(Long id, String produto, int quantidade, double precoUnitario) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public Long getId() {
        return id;
    }

    public String getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public String getStatusEstoque() {
        if (quantidade >= 100) {
            return "Alto";
        } else if (quantidade >= 20) {
            return "Médio";
        } else {
            return "Baixo";
        }
    }
}
