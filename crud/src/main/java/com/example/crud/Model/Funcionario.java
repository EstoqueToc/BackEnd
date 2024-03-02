package com.example.crud.Model;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Funcionario {
    @NotBlank
    private String nome;

    @CPF
    @NotBlank
    private String CPF;

    @Email
    @NotBlank
    private String email;

    @Size(min = 7,max = 14)
    @NotBlank
    private String senha;

    @Past
    @NotNull
    private LocalDate dtNascimento;

    @NotBlank
    private String funcao;

    @NotNull
    private boolean acesso;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public boolean isAcesso() {
        return acesso;
    }

    public void setAcesso(boolean acesso) {
        this.acesso = acesso;
    }

    public long getIdade(){
        LocalDate hoje = LocalDate.now();
        return ChronoUnit.YEARS.between(dtNascimento, hoje);
    }
}
