package com.example.crud.dto.criacaoDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogradouroCriacaoDto {

    @NotBlank(message = "Rua é obrigatória")
    private String rua;

    @NotBlank(message = "Número é obrigatório")
    private String numero;

    private String complemento;

    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    @NotBlank(message = "CEP é obrigatório")
    private String cep;

    @NotBlank(message = "País é obrigatório")
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