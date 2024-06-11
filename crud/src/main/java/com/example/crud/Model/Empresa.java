package com.example.crud.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeEmpresa;

    private String razaoSocial;

    private String cnpj;

    private String telefone;

    private String emailCorporativo;

    private boolean ativo = true;

    @ManyToOne
    private Logradouro logradouro;

//    @OneToMany(mappedBy = "produtos")
//    private List<Produto> produtos;

//    @OneToMany(mappedBy = "empresa")
//    private List<Alerta> alertas;

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
                Objects.equals(cnpj, empresa.cnpj) &&
                Objects.equals(telefone, empresa.telefone) &&
                Objects.equals(emailCorporativo, empresa.emailCorporativo) &&
                Objects.equals(ativo, empresa.ativo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeEmpresa, razaoSocial, cnpj, telefone, emailCorporativo, ativo);
    }

//    public String setNome(String novoNome) {
//        String nomeAntigo = this.nomeEmpresa;
//        this.nomeEmpresa = novoNome;
//        return nomeAntigo;
//    }
//
//    public String getNome() {
//        return nomeEmpresa;
//    }


}
