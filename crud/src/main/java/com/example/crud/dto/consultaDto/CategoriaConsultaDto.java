package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Categoria;
import com.example.crud.Model.Empresa;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class CategoriaConsultaDto {

    private Long id;
    private String nome;
    private Empresa empresa;

    public CategoriaConsultaDto() {
    }

    public CategoriaConsultaDto(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.empresa = categoria.getEmpresa();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaConsultaDto that = (CategoriaConsultaDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
