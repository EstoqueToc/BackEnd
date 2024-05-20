package com.example.crud.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String nomeFantasia;

    @NotBlank
    private String razaoSocial;

    @NotBlank
    private String telefone;

    @Email
    private String email;

    @NotBlank
    @CNPJ
    private String cnpj;

    @NotNull
    private Double preco;

    @NotNull
    private Boolean parceria;

    public Fornecedor() {
    }

    public Fornecedor(String nome) {
        this.nome = nome;
    }

    public Fornecedor(String nome, String nomeFantasia, String razaoSocial, String telefone, String email, String cnpj, Double preco, Boolean parceria) {
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.telefone = telefone;
        this.email = email;
        this.cnpj = cnpj;
        this.preco = preco;
        this.parceria = parceria;
    }
}
