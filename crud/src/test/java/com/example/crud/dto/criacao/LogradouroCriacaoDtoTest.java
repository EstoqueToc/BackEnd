package com.example.crud.dto.criacao;

import com.example.crud.dto.criacaoDto.LogradouroCriacaoDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogradouroCriacaoDtoTest {

    @Test
    public void testConstrutorComArgumentos() {
        // Arrange
        String rua = "Rua Teste";
        String numero = "123";
        String complemento = "Complemento";
        String cidade = "Cidade";
        String estado = "Estado";
        String cep = "12345-678";
        String pais = "País";

        // Act
        LogradouroCriacaoDto logradouro = new LogradouroCriacaoDto(rua, numero, complemento, cidade, estado, cep, pais);

        // Assert
        assertEquals(rua, logradouro.getRuaLogradouro());
        assertEquals(numero, logradouro.getNumeroLogradouro());
        assertEquals(complemento, logradouro.getComplementoLogradouro());
        assertEquals(cidade, logradouro.getCidadeLogradouro());
        assertEquals(estado, logradouro.getEstadoLogradouro());
        assertEquals(cep, logradouro.getCepLogradouro());
    }

    @Test
    public void testConstrutorPadrao() {
        // Arrange
        LogradouroCriacaoDto logradouro = new LogradouroCriacaoDto();

        // Act

        // Assert
        assertNotNull(logradouro);
    }
}
