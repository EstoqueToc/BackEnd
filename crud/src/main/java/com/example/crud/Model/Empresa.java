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

    @NotBlank(message = "CNPJ é obrigatório")
    @CNPJ(message = "CNPJ inválido")
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

    public String setNome(String novoNome) {
        String nomeAntigo = this.nomeEmpresa;
        this.nomeEmpresa = novoNome;
        return nomeAntigo;
    }

    public String getNome() {
        return nomeEmpresa;
    }
}
