package com.example.crud.dto.criacaoDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogradouroCriacaoDto {

    @NotBlank(message = "Rua é obrigatória")
    private String ruaLogradouro;

    @NotBlank(message = "Número é obrigatório")
    private String numeroLogradouro;

    private String complementoLogradouro;

    @NotBlank(message = "Cidade é obrigatória")
    private String cidadeLogradouro;

    @NotBlank(message = "Estado é obrigatório")
    private String estadoLogradouro;

    @NotBlank(message = "CEP é obrigatório")
    private String cepLogradouro;

    @NotBlank(message = "País é obrigatório")
    private String paisLogradouro;

    public LogradouroCriacaoDto() {

    }

}