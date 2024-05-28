package com.example.crud.dto.criacaoDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogradouroCriacaoDto {

    @NotBlank
    private String rua;
    @NotBlank
    private String numero;
    @NotBlank
    private String complemento;
    @NotBlank
    private String cidade;
    @NotBlank
    private String estado;
    @NotBlank
    private String cep;
    @NotBlank
    private String pais;

}
