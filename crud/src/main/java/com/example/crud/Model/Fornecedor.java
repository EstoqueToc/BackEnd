package com.example.crud.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeFantasia;
    private String razaoSocial;
    @Column(name = "telefone_fornecedor")
    private String telefone;
    @Column(name = "email_fornecedor")
    private String email;
    @Column(name = "cnpj_fornecedor")
    private String cnpj;
    private int ativo;

    @ManyToOne
    private Logradouro logradouro;

    public Fornecedor() {
    }

    public Fornecedor(String nome) {
        this.nomeFantasia = nome;
    }
}
