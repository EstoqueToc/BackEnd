package com.example.crud.dto.criacaoDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaCriacaoDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;

}
