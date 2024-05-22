package com.example.crud.dto.criacaoDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioCriacaoDto {

    @NotBlank(message = "Nome do usuário é obrigatório")
    private String nome;

    @CPF(message = "CPF inválido")
    @NotBlank(message = "CPF é obrigatório")
    private String CPF;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    private String email;

    @Size(min = 7, max = 14, message = "Senha deve ter entre 7 e 14 caracteres")
    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @Past(message = "Data de nascimento deve estar no passado")
    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate dtNascimento;

    private String funcao;

    private boolean acesso;
}