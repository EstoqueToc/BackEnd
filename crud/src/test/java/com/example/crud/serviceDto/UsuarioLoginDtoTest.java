package com.example.crud.serviceDto;

import com.example.crud.service.dto.UsuarioLoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class UsuarioLoginDtoTest {

    private UsuarioLoginDto usuarioLoginDto;

    @BeforeEach
    void setUp() {
        usuarioLoginDto = new UsuarioLoginDto();
        usuarioLoginDto.setEmail("testuser@example.com");
        usuarioLoginDto.setSenha("password123");
    }

    @Test
    @DisplayName("Deve retornar o email do usuário")
    void getEmail() {
        assertEquals("testuser@example.com", usuarioLoginDto.getEmail());
    }

    @Test
    @DisplayName("Deve retornar a senha do usuário")
    void getSenha() {
        assertEquals("password123", usuarioLoginDto.getSenha());
    }

    @Test
    @DisplayName("Deve definir o email do usuário")
    void setEmail() {
        usuarioLoginDto.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", usuarioLoginDto.getEmail());
    }

    @Test
    @DisplayName("Deve definir a senha do usuário")
    void setSenha() {
        usuarioLoginDto.setSenha("newpassword456");
        assertEquals("newpassword456", usuarioLoginDto.getSenha());
    }
}
