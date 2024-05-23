package com.example.crud.dto.consulta;

import com.example.crud.Model.Categoria;
import com.example.crud.dto.consultaDto.CategoriaConsultaDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoriaConsultaDtoTest {

    @Test
    public void testConstructorWithCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Eletrônicos");
        categoria.setDescricao("Categoria para produtos eletrônicos");

        CategoriaConsultaDto dto = new CategoriaConsultaDto(categoria);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Eletrônicos", dto.getNome());
        assertEquals("Categoria para produtos eletrônicos", dto.getDescricao());
    }

    @Test
    public void testEqualsAndHashCode() {
        CategoriaConsultaDto dto1 = new CategoriaConsultaDto();
        dto1.setId(1L);
        dto1.setNome("Eletrônicos");
        dto1.setDescricao("Categoria para produtos eletrônicos");

        CategoriaConsultaDto dto2 = new CategoriaConsultaDto();
        dto2.setId(1L);
        dto2.setNome("Eletrônicos");
        dto2.setDescricao("Categoria para produtos eletrônicos");

        CategoriaConsultaDto dto3 = new CategoriaConsultaDto();
        dto3.setId(2L);
        dto3.setNome("Informática");
        dto3.setDescricao("Categoria para produtos de informática");

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto2, dto3);

        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
        assertNotEquals(dto2.hashCode(), dto3.hashCode());
    }
}
