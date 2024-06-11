package com.example.crud.dto.consulta;

import com.example.crud.dto.criacaoDto.UsuarioCriacaoDto;
import com.example.crud.dto.consultaDto.UsuarioConsultaDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioConsultaDtoTest {

    @Test
    public void testUsuarioConsultaDto() {
        UsuarioCriacaoDto usuario = new UsuarioCriacaoDto();
        usuario.setNome("Nome");
        usuario.setCpf("123.456.789-00");
        usuario.setEmail("email@example.com");
        usuario.setSenha("senha123");
        usuario.setDataNascimento(LocalDate.of(2000, 1, 1));
        usuario.setFuncao("Função");


        UsuarioConsultaDto dto = new UsuarioConsultaDto(usuario);

        assertEquals("Nome", dto.getNome());
        assertEquals("123.456.789-00", dto.getCpf());
        assertEquals("email@example.com", dto.getEmail());
        assertEquals("senha123", dto.getSenha());
        assertEquals(LocalDate.of(2000, 1, 1), dto.getDataNascimento());
        assertEquals("Função", dto.getFuncao());
    }
}
