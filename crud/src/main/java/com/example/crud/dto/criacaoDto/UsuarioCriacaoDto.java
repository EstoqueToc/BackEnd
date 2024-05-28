package com.example.crud.dto.criacaoDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioCriacaoDto {

//    @NotBlank
    private String nome;

//    @CPF
//    @NotBlank
    private String CPF;

//    @Email
//    @NotBlank
    private String email;

//    @Size(min = 7, max = 14)
//    @NotBlank
    private String senha;

//    @Past
//    @NotNull
    private LocalDate dtNascimento;

    private String funcao;

    private boolean acesso;

}
