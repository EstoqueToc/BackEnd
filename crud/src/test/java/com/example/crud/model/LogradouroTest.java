package com.example.crud.model;

import com.example.crud.Model.Logradouro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogradouroTest {

    @Test
    @DisplayName("Deve retornar corretamente a rua")
    void getRua() {
        Logradouro logradouro = new Logradouro();
        logradouro.setRuaLogradouro("Rua das Flores");
        assertEquals("Rua das Flores", logradouro.getRuaLogradouro());
    }

    @Test
    @DisplayName("Deve retornar corretamente o número")
    void getNumero() {
        Logradouro logradouro = new Logradouro();
        logradouro.setNumeroLogradouro("123");
        assertEquals("123", logradouro.getNumeroLogradouro());
    }

    @Test
    @DisplayName("Deve retornar corretamente o complemento")
    void getComplemento() {
        Logradouro logradouro = new Logradouro();
        logradouro.setComplementoLogradouro("Apto 101");
        assertEquals("Apto 101", logradouro.getComplementoLogradouro());
    }

    @Test
    @DisplayName("Deve retornar corretamente a cidade")
    void getCidade() {
        Logradouro logradouro = new Logradouro();
        logradouro.setCidadeLogradouro("São Paulo");
        assertEquals("São Paulo", logradouro.getCidadeLogradouro());
    }

    @Test
    @DisplayName("Deve retornar corretamente o estado")
    void getEstado() {
        Logradouro logradouro = new Logradouro();
        logradouro.setEstadoLogradouro("SP");
        assertEquals("SP", logradouro.getEstadoLogradouro());
    }

    @Test
    @DisplayName("Deve retornar corretamente o CEP")
    void getCep() {
        Logradouro logradouro = new Logradouro();
        logradouro.setCepLogradouro("01001-000");
        assertEquals("01001-000", logradouro.getCepLogradouro());
    }

    @Test
    @DisplayName("Deve definir corretamente a rua")
    void setRua() {
        Logradouro logradouro = new Logradouro();
        logradouro.setRuaLogradouro("Avenida Paulista");
        assertEquals("Avenida Paulista", logradouro.getRuaLogradouro());
    }

    @Test
    @DisplayName("Deve definir corretamente o número")
    void setNumero() {
        Logradouro logradouro = new Logradouro();
        logradouro.setNumeroLogradouro("456");
        assertEquals("456", logradouro.getNumeroLogradouro());
    }

    @Test
    @DisplayName("Deve definir corretamente o complemento")
    void setComplemento() {
        Logradouro logradouro = new Logradouro();
        logradouro.setComplementoLogradouro("Bloco B");
        assertEquals("Bloco B", logradouro.getComplementoLogradouro());
    }

    @Test
    @DisplayName("Deve definir corretamente a cidade")
    void setCidade() {
        Logradouro logradouro = new Logradouro();
        logradouro.setCidadeLogradouro("Rio de Janeiro");
        assertEquals("Rio de Janeiro", logradouro.getCidadeLogradouro());
    }

    @Test
    @DisplayName("Deve definir corretamente o estado")
    void setEstado() {
        Logradouro logradouro = new Logradouro();
        logradouro.setEstadoLogradouro("RJ");
        assertEquals("RJ", logradouro.getEstadoLogradouro());
    }

    @Test
    @DisplayName("Deve definir corretamente o CEP")
    void setCep() {
        Logradouro logradouro = new Logradouro();
        logradouro.setCepLogradouro("20000-000");
        assertEquals("20000-000", logradouro.getCepLogradouro());
    }

    @Test
    @DisplayName("Deve definir corretamente o endereço completo")
    void setEndereco() {
        Logradouro logradouro = new Logradouro();
        String endereco = "Rua das Acácias, 45, Casa, Curitiba - PR";
        logradouro.setEndereco(endereco);

        assertEquals("Rua das Acácias", logradouro.getRuaLogradouro());
        assertEquals("45", logradouro.getNumeroLogradouro());
        assertEquals("Casa", logradouro.getComplementoLogradouro());
        assertEquals("Curitiba", logradouro.getCidadeLogradouro());
        assertEquals("PR", logradouro.getEstadoLogradouro());
    }

    @Test
    @DisplayName("Deve lidar com formato inválido de endereço")
    void setEnderecoInvalido() {
        Logradouro logradouro = new Logradouro();
        String enderecoInvalido = "Formato inválido de endereço";
        logradouro.setEndereco(enderecoInvalido);

        assertNull(logradouro.getRuaLogradouro());
        assertNull(logradouro.getNumeroLogradouro());
        assertNull(logradouro.getComplementoLogradouro());
        assertNull(logradouro.getCidadeLogradouro());
        assertNull(logradouro.getEstadoLogradouro());
    }
}
