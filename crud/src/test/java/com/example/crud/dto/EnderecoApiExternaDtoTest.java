package com.example.crud.dto;

import com.example.crud.dto.EnderecoApiExternaDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnderecoApiExternaDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSerializacaoJson() throws JsonProcessingException {
        EnderecoApiExternaDto enderecoDto = new EnderecoApiExternaDto();
        enderecoDto.setCep("12345-678");
        enderecoDto.setEstado("SP");
        enderecoDto.setCidade("São Paulo");
        enderecoDto.setBairro("Centro");
        enderecoDto.setRua("Rua Teste");

        String json = objectMapper.writeValueAsString(enderecoDto);

        Map<String, Object> expected = new HashMap<>();
        expected.put("cep", "12345-678");
        expected.put("uf", "SP");
        expected.put("localidade", "São Paulo");
        expected.put("bairro", "Centro");
        expected.put("logradouro", "Rua Teste");

        Map<String, Object> actual = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});

        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializacaoJson() throws JsonProcessingException {
        String json = "{\"cep\":\"12345-678\",\"uf\":\"SP\",\"localidade\":\"São Paulo\",\"bairro\":\"Centro\",\"logradouro\":\"Rua Teste\"}";

        EnderecoApiExternaDto enderecoDto = objectMapper.readValue(json, EnderecoApiExternaDto.class);

        assertEquals("12345-678", enderecoDto.getCep());
        assertEquals("SP", enderecoDto.getEstado());
        assertEquals("São Paulo", enderecoDto.getCidade());
        assertEquals("Centro", enderecoDto.getBairro());
        assertEquals("Rua Teste", enderecoDto.getRua());
    }
}
