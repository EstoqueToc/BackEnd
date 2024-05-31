package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Fornecedor;
import com.example.crud.Model.Logradouro;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FornecedorConsultaDto {


    private Long id;
    private String nomeFantasia;
    private String razaoSocial;
    private String telefone;
    private String email;
    private String cnpj;
    private boolean ativo;
    private LogradouroConsultaDto logradouro;

    public FornecedorConsultaDto() {
    }

    public FornecedorConsultaDto(Fornecedor fornecedor) {
        this.id = fornecedor.getId();
        this.nomeFantasia = fornecedor.getNomeFantasia();
        this.razaoSocial = fornecedor.getRazaoSocial();
        this.telefone = fornecedor.getTelefone();
        this.email = fornecedor.getEmail();
        this.cnpj = fornecedor.getCnpj();
        this.ativo = fornecedor.isAtivo();
        this.logradouro = new LogradouroConsultaDto(fornecedor.getLogradouro());
    }
}
