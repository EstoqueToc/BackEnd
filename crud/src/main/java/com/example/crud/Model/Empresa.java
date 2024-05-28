package com.example.crud.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Entity
@Getter
@Setter
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeEmpresa;

    private String razaoSocial;

    private String CNPJ;

    private String telefone;

    private String emailCorporativo;

    private String senhaEmpresa;

    @ManyToOne
    private Logradouro logradouro;

    private boolean ativo;

    @OneToMany(mappedBy = "empresa")
    private List<Produto> produtos;

    @OneToMany(mappedBy = "empresa")
    private List<Alerta> alertas;

    public Empresa() {
    }
}
