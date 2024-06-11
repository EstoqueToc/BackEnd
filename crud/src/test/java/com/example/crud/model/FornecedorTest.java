package com.example.crud.model;

import com.example.crud.Model.Fornecedor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FornecedorTest {

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
}
