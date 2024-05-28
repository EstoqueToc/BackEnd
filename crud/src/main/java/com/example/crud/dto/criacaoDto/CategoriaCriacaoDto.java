package com.example.crud.dto.criacaoDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaCriacaoDto {

    @NotBlank(message = "Nome da categoria é obrigatório")
    private String nome;

    @NotBlank(message = "Descrição da categoria é obrigatória")
    private String descricao;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


}
