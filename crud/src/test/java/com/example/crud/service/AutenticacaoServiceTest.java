package com.example.crud.service;

import com.example.crud.Model.Usuario;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.service.dto.UsuarioDetalhesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AutenticacaoServiceTest {

    private AutenticacaoService autenticacaoService;
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        autenticacaoService = new AutenticacaoService();
        autenticacaoService.usuarioRepository = usuarioRepository; // Injetando o mock manualmente
    }

    @DisplayName("Se o usuário existir, deve retornar UserDetails")
    @Test
    void loadUserByUsernameUsuarioExistente() {
        String email = "usuario@exemplo.com";
        Usuario usuario = new Usuario(); // Criando uma instância de Usuario
        usuario.setEmail(email); // Configurando o email do usuário

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        UserDetails userDetails = autenticacaoService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(usuario.getEmail(), userDetails.getUsername());
    }

    @DisplayName("Se o usuário não existir, deve lançar UsernameNotFoundException")
    @Test
    void loadUserByUsernameUsuarioNaoExistente() {
        String email = "naoexiste@exemplo.com";

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> autenticacaoService.loadUserByUsername(email));
    }
    @DisplayName("Se o usuário existir, UserDetails deve conter o mesmo email")
    @Test
    void userDetailsDeveConterMesmoEmail() {
        String email = "usuario@exemplo.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        UserDetails userDetails = autenticacaoService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());
    }

    @DisplayName("Se o usuário existir, UserDetails deve conter a senha correta")
    @Test
    void userDetailsDeveConterSenhaCorreta() {
        String email = "usuario@exemplo.com";
        String senha = "senha123";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        UserDetails userDetails = autenticacaoService.loadUserByUsername(email);

        assertEquals(senha, userDetails.getPassword());
    }

    @DisplayName("Se o usuário existir, UserDetails deve ter as roles corretas")
    @Test
    void userDetailsDeveConterRolesCorretas() {
        String email = "usuario@exemplo.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setRoles(Collections.singletonList("ROLE_USER"));

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        UserDetails userDetails = autenticacaoService.loadUserByUsername(email);

        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));
    }
}