package com.example.crud.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(id, empresa.id) &&
                Objects.equals(nomeEmpresa, empresa.nomeEmpresa) &&
                Objects.equals(razaoSocial, empresa.razaoSocial) &&
                Objects.equals(CNPJ, empresa.CNPJ) &&
                Objects.equals(telefone, empresa.telefone) &&
                Objects.equals(emailCorporativo, empresa.emailCorporativo) &&
                Objects.equals(senhaEmpresa, empresa.senhaEmpresa) &&
                Objects.equals(logradouro, empresa.logradouro) &&
                Objects.equals(ativo, empresa.ativo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeEmpresa, razaoSocial, CNPJ, telefone, emailCorporativo, senhaEmpresa, logradouro, ativo);
    }
}
