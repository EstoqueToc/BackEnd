package com.example.crud.dto.criacaoDto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
public class UsuarioCriacaoDto {

    @NotBlank
    private String nome;

    @CPF
    @NotBlank
    private String CPF;

    @Email
    @NotBlank
    private String email;

    @Size(min = 7, max = 14)
    @NotBlank
    private String senha;

    @Past
    @NotNull
    private LocalDate dtNascimento;

    @NotBlank
    private String funcao;

    @NotNull
    private boolean acesso;

}
