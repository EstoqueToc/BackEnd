package com.example.crud.dto.criacaoDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
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

    public LogradouroCriacaoDto(String rua, String numero, String complemento, String cidade, String estado, String cep, String pais) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.pais = pais;
    }

    public LogradouroCriacaoDto() {

    }
}
