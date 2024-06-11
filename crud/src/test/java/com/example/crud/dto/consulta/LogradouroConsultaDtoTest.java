package com.example.crud.dto.consulta;

import com.example.crud.Model.Logradouro;
import com.example.crud.dto.consultaDto.LogradouroConsultaDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LogradouroConsultaDtoTest {

        @Test
        public void testConstructorWithLogradouro() {
            Logradouro logradouro = new Logradouro();
            logradouro.setId(1L);
            logradouro.setRuaLogradouro("Rua das Flores");
            logradouro.setNumeroLogradouro("123");
            logradouro.setComplementoLogradouro("Apto 101");
            logradouro.setCidadeLogradouro("São Paulo");
            logradouro.setEstadoLogradouro("SP");
            logradouro.setCepLogradouro("12345-678");

            LogradouroConsultaDto dto = new LogradouroConsultaDto(logradouro);

            assertNotNull(dto);
            assertEquals(1L, dto.getId());
            assertEquals("Rua das Flores", dto.getRua());
            assertEquals("123", dto.getNumero());
            assertEquals("Apto 101", dto.getComplemento());
            assertEquals("São Paulo", dto.getCidade());
            assertEquals("SP", dto.getEstado());
            assertEquals("12345-678", dto.getCep());
        }
    }

