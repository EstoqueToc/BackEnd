package com.example.crud.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank
    private String nome;

    // @CPF
    // @NotBlank
    private String CPF;

    // @Email
    // @NotBlank
    private String email;

    // @NotBlank
    private String senha;

    // @Past
    // @NotNull
    private LocalDate dtNascimento;

    private String funcao;

    private boolean acesso;

    @ElementCollection
    private List<String> roles;
    public Usuario() {
    }

    public Usuario(Long id, String nome, String CPF, String email, String senha, LocalDate dtNascimento, String funcao, boolean acesso, String roles) {
        this.id = id;
        this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.senha = senha;
        this.dtNascimento = dtNascimento;
        this.funcao = funcao;
        this.acesso = acesso;
        this.roles = Arrays.asList(roles.split(","));
    }


    public long getIdade() {
        LocalDate hoje = LocalDate.now();
        return ChronoUnit.YEARS.between(dtNascimento, hoje);
    }

    public List<String> getRolesList() {
        return roles;
    }

    public void setRolesList(List<String> rolesList) {
        this.roles = rolesList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

}
