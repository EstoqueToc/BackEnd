package com.example.crud.Model;

import jakarta.validation.constraints.*;

public class Produto {

    @NotBlank
    private String nome;

    @NotNull
    @PositiveOrZero
    private Double preco;

    @NotBlank
    private String descricao;

    @NotBlank
    private String grupo;

    @NotNull
    @PositiveOrZero
    private Integer qtdEstoque;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Integer getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(Integer qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }
}
