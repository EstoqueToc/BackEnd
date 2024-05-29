package com.example.crud.service.dto;

import com.example.crud.Model.Empresa;
import com.example.crud.dto.consultaDto.EmpresaDetalhesConsulta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioTokenDto {

    private Long userId;
    private String nome;
    private String email;
    private String token;
    private EmpresaDetalhesConsulta fkEmpresa;
    private String tipo;

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
