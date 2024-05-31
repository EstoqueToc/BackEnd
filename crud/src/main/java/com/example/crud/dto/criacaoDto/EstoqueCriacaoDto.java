package com.example.crud.dto.criacaoDto;

import com.example.crud.Model.Empresa;
import com.example.crud.Model.Produto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueCriacaoDto {

    private Integer qtdSaida;
    private Integer qtdDisponivel;

    @NotNull(message = "Empresa é obrigatória")
    private Empresa empresa;

    @NotNull(message = "Produto é obrigatório")
    private Produto produto;
}
