package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Categoria;
import lombok.Getter;

@Getter
public class CategoriaConsultaDto {


    private Long categoria_id;
    private String nome;
    private String descricao;

    public CategoriaConsultaDto(Categoria categoria) {
        this.categoria_id = categoria.getCategoria_id();
        this.nome = categoria.getNome();
        this.descricao = categoria.getDescricao();
    }
}
