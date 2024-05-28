package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Categoria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaConsultaDto {

    private Long id;
    private String nome;
    private String descricao;

    public CategoriaConsultaDto() {
    }

    public CategoriaConsultaDto(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.descricao = categoria.getDescricao();
    }
}
