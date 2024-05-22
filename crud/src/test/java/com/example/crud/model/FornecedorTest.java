package com.example.crud.model;

import com.example.crud.Model.Fornecedor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FornecedorTest {

    @Test
    @DisplayName("Deve retornar corretamente o nome do fornecedor")
    void getNome() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Fornecedor A");
        assertEquals("Fornecedor A", fornecedor.getNome());
    }

    @Test
    @DisplayName("Deve retornar corretamente o nome fantasia do fornecedor")
    void getNomeFantasia() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNomeFantasia("Fantasia A");
        assertEquals("Fantasia A", fornecedor.getNomeFantasia());
    }

    @Test
    @DisplayName("Deve retornar corretamente a razão social do fornecedor")
    void getRazaoSocial() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setRazaoSocial("Razão Social A");
        assertEquals("Razão Social A", fornecedor.getRazaoSocial());
    }

    @Test
    @DisplayName("Deve retornar corretamente o telefone do fornecedor")
    void getTelefone() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setTelefone("1234-5678");
        assertEquals("1234-5678", fornecedor.getTelefone());
    }

    @Test
    @DisplayName("Deve retornar corretamente o email do fornecedor")
    void getEmail() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setEmail("fornecedor@example.com");
        assertEquals("fornecedor@example.com", fornecedor.getEmail());
    }

    @Test
    @DisplayName("Deve retornar corretamente o CNPJ do fornecedor")
    void getCNPJ() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCnpj("12.345.678/0001-99");
        assertEquals("12.345.678/0001-99", fornecedor.getCnpj());
    }

    @Test
    @DisplayName("Deve retornar corretamente o preço do fornecedor")
    void getPreco() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setPreco(100.0);
        assertEquals(100.0, fornecedor.getPreco());
    }

    @Test
    @DisplayName("Deve retornar corretamente a parceria do fornecedor")
    void getParceria() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setParceria(true);
        assertTrue(fornecedor.getParceria());
    }

    @Test
    @DisplayName("Deve construir corretamente um objeto Fornecedor com todos os campos")
    void fornecedorConstructor() {
        Fornecedor fornecedor = new Fornecedor("Fornecedor B", "Fantasia B", "Razão Social B", "8765-4321", "fornecedorb@example.com", "98.765.432/0001-11", 200.0, false);

        assertAll(
                () -> assertEquals("Fornecedor B", fornecedor.getNome()),
                () -> assertEquals("Fantasia B", fornecedor.getNomeFantasia()),
                () -> assertEquals("Razão Social B", fornecedor.getRazaoSocial()),
                () -> assertEquals("8765-4321", fornecedor.getTelefone()),
                () -> assertEquals("fornecedorb@example.com", fornecedor.getEmail()),
                () -> assertEquals("98.765.432/0001-11", fornecedor.getCnpj()),
                () -> assertEquals(200.0, fornecedor.getPreco()),
                () -> assertFalse(fornecedor.getParceria())
        );
    }
}
