package com.example.crud.dto.criacaoDto;

import com.example.crud.Model.Empresa;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaCriacaoDto {

    @NotBlank(message = "Nome da categoria é obrigatório")
    private String nome;

    private Empresa empresa;
}
