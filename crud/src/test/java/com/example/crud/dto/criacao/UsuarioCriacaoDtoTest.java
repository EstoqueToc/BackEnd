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
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setDataNascimento(dtNascimento);
        usuario.setFuncao(funcao);


        assertNotNull(usuario);
        assertEquals(nome, usuario.getNome());
        assertEquals(cpf, usuario.getCpf());
        assertEquals(email, usuario.getEmail());
        assertEquals(senha, usuario.getSenha());
        assertEquals(dtNascimento, usuario.getDataNascimento());
        assertEquals(funcao, usuario.getFuncao());
    }

    @Test
    public void testConstrutorPadrao() {
        UsuarioCriacaoDto usuario = new UsuarioCriacaoDto();

        assertNotNull(usuario);
    }
}
