package com.example.crud.dto.mapper;

import com.example.crud.Model.Empresa;
import com.example.crud.dto.consultaDto.EmpresaConsultaDto;
import com.example.crud.dto.criacaoDto.EmpresaCriacaoDto;

public class EmpresaMapper {

    public static Empresa toEntity(EmpresaCriacaoDto empresaCriacaoDto) {
        Empresa empresa = new Empresa();
        empresa.setNome(empresaCriacaoDto.getNome());
        empresa.setRazaoSocial(empresaCriacaoDto.getRazaoSocial());
        empresa.setCNPJ(empresaCriacaoDto.getCNPJ());
        empresa.setTelefone(empresaCriacaoDto.getTelefone());
        empresa.setEmailCorporativo(empresaCriacaoDto.getEmail());
        empresa.setLogradouro(empresaCriacaoDto.getLogradouro());
        empresa.setAtivo(empresaCriacaoDto.isAtivo());
        return empresa;
    }

    public static EmpresaConsultaDto toConsultaDto(Empresa empresa) {
        return new EmpresaConsultaDto(empresa);
    }
}
