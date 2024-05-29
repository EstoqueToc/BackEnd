package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Empresa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaDetalhesConsulta {

    private Long id;
    private String nomeEmpresa;
    private String razaoSocial;
    private String CNPJ; /* 52.254.752/0001-82 */

    public EmpresaDetalhesConsulta() {
    }

    public EmpresaDetalhesConsulta(Empresa empresa) {
        this.id = empresa.getId();
        this.nomeEmpresa = empresa.getNomeEmpresa();
        this.razaoSocial = empresa.getRazaoSocial();
        this.CNPJ = empresa.getCNPJ();
    }
}
