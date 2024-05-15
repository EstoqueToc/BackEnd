package com.example.crud.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Getter
@Setter
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @NotBlank

    private String nomeEmpresa;

    private String razaoSocial;
//    @CNPJ
//    @Size(min = 18, max = 18)
    private String CNPJ; /* 52.254.752/0001-82 */

//    @Size(min = 13, max = 16)
    private String telefone; /* (99) 99999-9999 | (99)99999-9999 | (99) 9 9999-9999 | 99 99999-9999 | 99 9 9999-9999*/
//    @Email
    private String emailCorporativo;

    private String senhaEmpresa;

    @ManyToOne
    private Logradouro logradouro;

    private boolean ativo;

    public Empresa() {
    }
}
