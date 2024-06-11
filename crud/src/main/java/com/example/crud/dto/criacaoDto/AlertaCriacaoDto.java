package com.example.crud.dto.criacaoDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertaCriacaoDto {

    @NotNull
    private Integer alertaModerado;

    @NotNull
    private Integer alertaGrave;

}
