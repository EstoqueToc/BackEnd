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

    private String nome;

    private String CPF;

    private String email;

    private String senha;

    private LocalDate dtNascimento;

    private String funcao;

    private boolean acesso;

    @ManyToOne
    private Empresa fkEmpresa;

    @ElementCollection
    private List<String> roles;
    public Usuario() {
    }

    public Usuario(Long id, String nome, String CPF, String email, String senha, LocalDate dtNascimento, String funcao, boolean acesso, Empresa fkEmpresa, String roles) {
        this.id = id;
        this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.senha = senha;
        this.dtNascimento = dtNascimento;
        this.funcao = funcao;
        this.acesso = acesso;
        this.fkEmpresa = fkEmpresa;
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

}
