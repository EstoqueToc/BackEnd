package com.example.crud.dto;

import com.example.crud.dto.EnderecoDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class EnderecoDtoTest {

    @Test
    public void testGettersAndSetters() {
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setCep("12345-678");
        enderecoDto.setEstado("SP");
        enderecoDto.setCidade("São Paulo");
        enderecoDto.setBairro("Centro");
        enderecoDto.setRua("Rua Teste");

        assertEquals("12345-678", enderecoDto.getCep());
        assertEquals("SP", enderecoDto.getEstado());
        assertEquals("São Paulo", enderecoDto.getCidade());
        assertEquals("Centro", enderecoDto.getBairro());
        assertEquals("Rua Teste", enderecoDto.getRua());
    }
}
