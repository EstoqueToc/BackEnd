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
        logradouro.setRua("Rua das Flores");
        assertEquals("Rua das Flores", logradouro.getRua());
    }

    @Test
    @DisplayName("Deve retornar corretamente o número")
    void getNumero() {
        Logradouro logradouro = new Logradouro();
        logradouro.setNumero("123");
        assertEquals("123", logradouro.getNumero());
    }

    @Test
    @DisplayName("Deve retornar corretamente o complemento")
    void getComplemento() {
        Logradouro logradouro = new Logradouro();
        logradouro.setComplemento("Apto 101");
        assertEquals("Apto 101", logradouro.getComplemento());
    }

    @Test
    @DisplayName("Deve retornar corretamente a cidade")
    void getCidade() {
        Logradouro logradouro = new Logradouro();
        logradouro.setCidade("São Paulo");
        assertEquals("São Paulo", logradouro.getCidade());
    }

    @Test
    @DisplayName("Deve retornar corretamente o estado")
    void getEstado() {
        Logradouro logradouro = new Logradouro();
        logradouro.setEstado("SP");
        assertEquals("SP", logradouro.getEstado());
    }

    @Test
    @DisplayName("Deve retornar corretamente o CEP")
    void getCep() {
        Logradouro logradouro = new Logradouro();
        logradouro.setCep("01001-000");
        assertEquals("01001-000", logradouro.getCep());
    }

    @Test
    @DisplayName("Deve retornar corretamente o país")
    void getPais() {
        Logradouro logradouro = new Logradouro();
        logradouro.setPais("Brasil");
        assertEquals("Brasil", logradouro.getPais());
    }

    @Test
    @DisplayName("Deve definir corretamente a rua")
    void setRua() {
        Logradouro logradouro = new Logradouro();
        logradouro.setRua("Avenida Paulista");
        assertEquals("Avenida Paulista", logradouro.getRua());
    }

    @Test
    @DisplayName("Deve definir corretamente o número")
    void setNumero() {
        Logradouro logradouro = new Logradouro();
        logradouro.setNumero("456");
        assertEquals("456", logradouro.getNumero());
    }

    @Test
    @DisplayName("Deve definir corretamente o complemento")
    void setComplemento() {
        Logradouro logradouro = new Logradouro();
        logradouro.setComplemento("Bloco B");
        assertEquals("Bloco B", logradouro.getComplemento());
    }

    @Test
    @DisplayName("Deve definir corretamente a cidade")
    void setCidade() {
        Logradouro logradouro = new Logradouro();
        logradouro.setCidade("Rio de Janeiro");
        assertEquals("Rio de Janeiro", logradouro.getCidade());
    }

    @Test
    @DisplayName("Deve definir corretamente o estado")
    void setEstado() {
        Logradouro logradouro = new Logradouro();
        logradouro.setEstado("RJ");
        assertEquals("RJ", logradouro.getEstado());
    }

    @Test
    @DisplayName("Deve definir corretamente o CEP")
    void setCep() {
        Logradouro logradouro = new Logradouro();
        logradouro.setCep("20000-000");
        assertEquals("20000-000", logradouro.getCep());
    }

    @Test
    @DisplayName("Deve definir corretamente o país")
    void setPais() {
        Logradouro logradouro = new Logradouro();
        logradouro.setPais("Brasil");
        assertEquals("Brasil", logradouro.getPais());
    }

    @Test
    @DisplayName("Deve definir corretamente o endereço completo")
    void setEndereco() {
        Logradouro logradouro = new Logradouro();
        String endereco = "Rua das Acácias, 45, Casa, Curitiba - PR";
        logradouro.setEndereco(endereco);

        assertEquals("Rua das Acácias", logradouro.getRua());
        assertEquals("45", logradouro.getNumero());
        assertEquals("Casa", logradouro.getComplemento());
        assertEquals("Curitiba", logradouro.getCidade());
        assertEquals("PR", logradouro.getEstado());
    }

    @Test
    @DisplayName("Deve lidar com formato inválido de endereço")
    void setEnderecoInvalido() {
        Logradouro logradouro = new Logradouro();
        String enderecoInvalido = "Formato inválido de endereço";
        logradouro.setEndereco(enderecoInvalido);

        assertNull(logradouro.getRua());
        assertNull(logradouro.getNumero());
        assertNull(logradouro.getComplemento());
        assertNull(logradouro.getCidade());
        assertNull(logradouro.getEstado());
    }
}
