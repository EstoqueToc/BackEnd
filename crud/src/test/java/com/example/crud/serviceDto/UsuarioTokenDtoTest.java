package com.example.crud.serviceDto;

import com.example.crud.service.dto.UsuarioTokenDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioTokenDtoTest {

    private UsuarioTokenDto usuarioTokenDto;

    @BeforeEach
    void setUp() {
        usuarioTokenDto = new UsuarioTokenDto();
        usuarioTokenDto.setUserId(1L);
        usuarioTokenDto.setNome("Teste");
        usuarioTokenDto.setEmail("teste@example.com");
        usuarioTokenDto.setToken("abc123");
    }

    @Test
    @DisplayName("Deve retornar o ID do usuário")
    void getUserId() {
        assertEquals(1L, usuarioTokenDto.getUserId());
    }

    @Test
    @DisplayName("Deve retornar o nome do usuário")
    void getNome() {
        assertEquals("Teste", usuarioTokenDto.getNome());
    }

    @Test
    @DisplayName("Deve retornar o email do usuário")
    void getEmail() {
        assertEquals("teste@example.com", usuarioTokenDto.getEmail());
    }

    @Test
    @DisplayName("Deve retornar o token do usuário")
    void getToken() {
        assertEquals("abc123", usuarioTokenDto.getToken());
    }

    @Test
    @DisplayName("Deve definir e retornar o tipo do token")
    void setTipo() {
        usuarioTokenDto.setTipo("Bearer");
        assertEquals("Bearer", usuarioTokenDto.getTipo());
    }
}
