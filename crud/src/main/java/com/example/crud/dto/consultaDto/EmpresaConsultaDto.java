package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Empresa;
import com.example.crud.Model.Logradouro;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaConsultaDto {


    private Long id;
    private String nomeEmpresa;
    private String razaoSocial;
    private String cnpj; /* 52.254.752/0001-82 */
    private String telefone; /* (99) 99999-9999 | (99)99999-9999 | (99) 9 9999-9999 | 99 99999-9999 | 99 9 9999-9999*/
    private String emailCorporativo;
    private LogradouroConsultaDto logradouro;
    private boolean ativo;

    public EmpresaConsultaDto() {
    }

    public EmpresaConsultaDto(Empresa empresa) {
        this.id = empresa.getId();
        this.nomeEmpresa = empresa.getNomeEmpresa();
        this.razaoSocial = empresa.getRazaoSocial();
        this.cnpj = empresa.getCnpj();
        this.telefone = empresa.getTelefone();
        this.emailCorporativo = empresa.getEmailCorporativo();
        this.logradouro = new LogradouroConsultaDto(empresa.getLogradouro());
        this.ativo = empresa.isAtivo();
    }
    public String getNome() {
        return nomeEmpresa;
    }

}
