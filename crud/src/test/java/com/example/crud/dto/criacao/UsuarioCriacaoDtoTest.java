package com.example.crud.dto.criacao;

import com.example.crud.dto.criacaoDto.UsuarioCriacaoDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsuarioCriacaoDtoTest {

    @Test
    public void testConstrutorComArgumentos() {
        String nome = "Fulano";
        String cpf = "123.456.789-00";
        String email = "fulano@example.com";
        String senha = "senha123";
        LocalDate dtNascimento = LocalDate.of(2000, 1, 1);
        String funcao = "Analista";
        boolean acesso = true;

        UsuarioCriacaoDto usuario = new UsuarioCriacaoDto();
        usuario.setNome(nome);
        usuario.setCPF(cpf);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setDtNascimento(dtNascimento);
        usuario.setFuncao(funcao);
        usuario.setAcesso(acesso);

        assertNotNull(usuario);
        assertEquals(nome, usuario.getNome());
        assertEquals(cpf, usuario.getCPF());
        assertEquals(email, usuario.getEmail());
        assertEquals(senha, usuario.getSenha());
        assertEquals(dtNascimento, usuario.getDtNascimento());
        assertEquals(funcao, usuario.getFuncao());
        assertEquals(acesso, usuario.isAcesso());
    }

    @Test
    public void testConstrutorPadrao() {
        UsuarioCriacaoDto usuario = new UsuarioCriacaoDto();

        assertNotNull(usuario);
    }
}
