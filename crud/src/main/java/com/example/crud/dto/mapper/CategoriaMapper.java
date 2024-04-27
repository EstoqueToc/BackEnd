package com.example.crud.dto.mapper;

import com.example.crud.Model.Categoria;
import com.example.crud.dto.consultaDto.CategoriaConsultaDto;
import com.example.crud.dto.criacaoDto.CategoriaCriacaoDto;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaCriacaoDto categoriaCriacaoDto) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaCriacaoDto.getNome());
        categoria.setDescricao(categoriaCriacaoDto.getDescricao());
        return categoria;
    }

    public static CategoriaConsultaDto toConsultaDto(Categoria categoria) {
        return new CategoriaConsultaDto(categoria);
    }
}
