package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Categoria;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaConsultaDto that = (CategoriaConsultaDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao);
    }
}
