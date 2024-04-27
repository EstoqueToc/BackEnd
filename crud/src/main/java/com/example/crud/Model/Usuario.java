package com.example.crud.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Usuario() {
    }

    public Usuario(Long id, String nome, String CPF, String email, String senha, LocalDate dtNascimento, String funcao, boolean acesso) {
        this.id = id;
        this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.senha = senha;
        this.dtNascimento = dtNascimento;
        this.funcao = funcao;
        this.acesso = acesso;
    }

    public long getIdade() {
        LocalDate hoje = LocalDate.now();
        return ChronoUnit.YEARS.between(dtNascimento, hoje);
    }
}
