package com.example.crud.dto.criacaoDto;

import com.example.crud.Model.Logradouro;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaCriacaoDto {

    private String nomeEmpresa;
    private String razaoSocial;
    private String CNPJ;
    private String telefone;
    private String emailCorpotativo;
    private String senhaEmpresa;
    @ManyToOne
    private Logradouro logradouro;
    private boolean ativo;

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setEmailCorpotativo(String emailCorpotativo) {
        this.emailCorpotativo = emailCorpotativo;
    }

    public String getEmailCorpotativo() {
        return emailCorpotativo;
    }

    public void setSenhaEmpresa(String senhaEmpresa) {
        this.senhaEmpresa = senhaEmpresa;
    }

    public String getSenhaEmpresa() {
        return senhaEmpresa;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
