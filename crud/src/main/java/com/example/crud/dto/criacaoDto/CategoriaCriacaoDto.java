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

}
