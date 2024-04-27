package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Empresa;
import com.example.crud.Model.Logradouro;
import lombok.Getter;

@Getter
public class EmpresaConsultaDto {


    private Long empresa_id;
    private String nome;
    private String razaoSocial;
    private String CNPJ; /* 52.254.752/0001-82 */
    private String telefone; /* (99) 99999-9999 | (99)99999-9999 | (99) 9 9999-9999 | 99 99999-9999 | 99 9 9999-9999*/
    private String email;
    private Logradouro logradouro;
    private boolean ativo;

    public EmpresaConsultaDto(Empresa empresa) {
        this.empresa_id = empresa.getEmpresa_id();
        this.nome = empresa.getNome();
        this.razaoSocial = empresa.getRazaoSocial();
        this.CNPJ = empresa.getCNPJ();
        this.telefone = empresa.getTelefone();
        this.email = empresa.getEmail();
        this.logradouro = empresa.getLogradouro();
        this.ativo = empresa.isAtivo();
    }
}
