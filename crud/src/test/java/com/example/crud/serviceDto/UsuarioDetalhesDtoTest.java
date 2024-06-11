package com.example.crud.serviceDto;
import com.example.crud.Model.Usuario;
import com.example.crud.service.dto.UsuarioDetalhesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDetalhesDtoTest {

    private Usuario usuario;
    private UsuarioDetalhesDto usuarioDetalhesDto;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Test User");
        usuario.setEmail("testuser@example.com");
        usuario.setSenha("password123");
        usuario.setDataNascimento(LocalDate.of(1990, 1, 1));

        usuarioDetalhesDto = new UsuarioDetalhesDto(usuario);
    }

    @Test
    @DisplayName("Deve retornar o nome do usuário")
    void getNome() {
        assertEquals("Test User", usuarioDetalhesDto.getNome());
    }

    @Test
    @DisplayName("Deve retornar a senha do usuário")
    void getPassword() {
        assertEquals("password123", usuarioDetalhesDto.getPassword());
    }

    @Test
    @DisplayName("Deve retornar o email do usuário")
    void getUsername() {
        assertEquals("testuser@example.com", usuarioDetalhesDto.getUsername());
    }

    @Test
    @DisplayName("Deve retornar true para isAccountNonExpired")
    void isAccountNonExpired() {
        assertTrue(usuarioDetalhesDto.isAccountNonExpired());
    }

    @Test
    @DisplayName("Deve retornar true para isAccountNonLocked")
    void isAccountNonLocked() {
        assertTrue(usuarioDetalhesDto.isAccountNonLocked());
    }

    @Test
    @DisplayName("Deve retornar true para isCredentialsNonExpired")
    void isCredentialsNonExpired() {
        assertTrue(usuarioDetalhesDto.isCredentialsNonExpired());
    }

    @Test
    @DisplayName("Deve retornar true para isEnabled")
    void isEnabled() {
        assertTrue(usuarioDetalhesDto.isEnabled());
    }
}
