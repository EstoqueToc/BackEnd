package com.example.crud.model;

import com.example.crud.Model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    @DisplayName("Deve retornar corretamente o nome do usuário")
    void getNome() {
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        assertEquals("Maria", usuario.getNome());
    }

    @Test
    @DisplayName("Deve retornar corretamente o CPF do usuário")
    void getCPF() {
        Usuario usuario = new Usuario();
        usuario.setCpf("987.654.321-00");
        assertEquals("987.654.321-00", usuario.getCpf());
    }

    @Test
    @DisplayName("Deve retornar corretamente o email do usuário")
    void getEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("maria@example.com");
        assertEquals("maria@example.com", usuario.getEmail());
    }

    @Test
    @DisplayName("Deve retornar corretamente a senha do usuário")
    void getSenha() {
        Usuario usuario = new Usuario();
        usuario.setSenha("senha123");
        assertEquals("senha123", usuario.getSenha());
    }

    @Test
    @DisplayName("Deve retornar corretamente a data de nascimento do usuário")
    void getDtNascimento() {
        LocalDate dataNascimento = LocalDate.of(1980, 5, 15);
        Usuario usuario = new Usuario();
        usuario.setDataNascimento(dataNascimento);
        assertEquals(dataNascimento, usuario.getDataNascimento());
    }

    @Test
    @DisplayName("Deve retornar corretamente a função do usuário")
    void getFuncao() {
        Usuario usuario = new Usuario();
        usuario.setFuncao("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", usuario.getFuncao());
    }


//    @Test
//    @DisplayName("Deve definir corretamente a lista de papéis do usuário")
//    void setRolesList() {
//        Usuario usuario = new Usuario();
//        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
//        usuario.setRolesList(roles);
//        assertEquals(roles, usuario.getRolesList());
//    }
}
