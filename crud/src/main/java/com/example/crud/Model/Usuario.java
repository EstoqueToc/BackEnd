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
    private String cpf;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private String funcao;
    private int acesso;
    private int ativo;

    @ManyToOne
    @JoinColumn(name = "empresa", nullable = false)
    private Empresa empresa;

//    @ElementCollection
//    private List<String> roles;
    public Usuario() {
    }

    public Usuario(Long id, String nome, String cpf, String email, String senha, LocalDate dataNascimento, String funcao, int acesso, int ativo, Empresa empresa, String roles) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.funcao = funcao;
        this.acesso = acesso;
        this.ativo = ativo;
        this.empresa = empresa;
//        this.roles = Arrays.asList(roles.split(","));
    }

//    public List<String> getRolesList() {
//        return roles;
//    }

//    public void setRolesList(List<String> rolesList) {
//        this.roles = rolesList;
//    }

}
