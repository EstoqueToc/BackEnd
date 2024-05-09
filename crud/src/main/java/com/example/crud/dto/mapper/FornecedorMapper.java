package com.example.crud.dto.mapper;

import com.example.crud.Model.Fornecedor;
import com.example.crud.dto.consultaDto.FornecedorConsultaDto;
import com.example.crud.dto.criacaoDto.FornecedorCriacaoDto;

public class FornecedorMapper {

    public static Fornecedor toEntity(FornecedorCriacaoDto fornecedorCriacaoDto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(fornecedorCriacaoDto.getNome());
        fornecedor.setNomeFantasia(fornecedorCriacaoDto.getNomeFantasia());
        fornecedor.setRazaoSocial(fornecedorCriacaoDto.getRazaoSocial());
        fornecedor.setTelefone(fornecedorCriacaoDto.getTelefone());
        fornecedor.setEmail(fornecedorCriacaoDto.getEmail());
        fornecedor.setCnpj(fornecedorCriacaoDto.getCnpj());
        fornecedor.setPreco(fornecedorCriacaoDto.getPreco());
        fornecedor.setParceria(fornecedorCriacaoDto.getParceria());
        return fornecedor;
    }

    public static FornecedorConsultaDto toConsultaDto(Fornecedor fornecedor) {
        return new FornecedorConsultaDto(fornecedor);
    }
}
