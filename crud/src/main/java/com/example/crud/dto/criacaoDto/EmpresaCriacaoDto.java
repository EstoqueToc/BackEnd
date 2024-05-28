package com.example.crud.dto.criacaoDto;

import com.example.crud.Model.Logradouro;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;


@Getter
@Setter
public class EmpresaCriacaoDto {


    @NotBlank(message = "Nome da empresa é obrigatório")
    @Size(max = 50, message = "Nome da empresa deve ter no máximo 50 caracteres")
    private String nomeEmpresa;

    @Size(max = 50, message = "Razão social deve ter no máximo 50 caracteres")
    private String razaoSocial;


    @Size(min = 13, max = 16)
    private String telefone; /* (99) 99999-9999 | (99)99999-9999 | (99) 9 9999-9999 | 99 99999-9999 | 99 9 9999-9999*/

    @CNPJ(message = "CNPJ inválido")
    @NotBlank(message = "CNPJ é obrigatório")
    private String CNPJ; /* 52.254.752/0001-82 */

    @Email(message = "Email corporativo inválido")
    @NotBlank(message = "Email corporativo é obrigatório")
    private String emailCorpotativo;

    @NotBlank(message = "Senha da empresa é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    private String senhaEmpresa;

    @NotNull(message = "Logradouro é obrigatório")
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

    public String getEmailCorporativo() {
        return emailCorpotativo;}
}
