package com.example.crud.model;

import static org.junit.jupiter.api.Assertions.*;

import com.example.crud.Model.Empresa;
import com.example.crud.Model.Logradouro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EmpresaTest {

    @Test
    @DisplayName("Teste de inicialização da empresa")

    void testInicializacaoEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNomeEmpresa("Nome da Empresa");
        empresa.setRazaoSocial("Razão Social da Empresa");
        empresa.setCNPJ("52.254.752/0001-82");
        empresa.setTelefone("(99) 99999-9999");
        empresa.setEmailCorporativo("empresa@example.com");
        empresa.setSenhaEmpresa("senha123");
        Logradouro logradouro = new Logradouro();
        empresa.setLogradouro(logradouro);
        empresa.setAtivo(true);

        assertNotNull(empresa);
        assertEquals(1L, empresa.getId());
        assertEquals("Nome da Empresa", empresa.getNomeEmpresa());
        assertEquals("Razão Social da Empresa", empresa.getRazaoSocial());
        assertEquals("52.254.752/0001-82", empresa.getCNPJ());
        assertEquals("(99) 99999-9999", empresa.getTelefone());
        assertEquals("empresa@example.com", empresa.getEmailCorporativo());
        assertEquals("senha123", empresa.getSenhaEmpresa());
        assertEquals(logradouro, empresa.getLogradouro());
        assertTrue(empresa.isAtivo());
    }

    @Test
    @DisplayName("Teste de validação de CNPJ")
    void testValidacaoCNPJ() {
        Empresa empresa = new Empresa();
        empresa.setCNPJ("52.254.752/0001-82");
        assertEquals("52.254.752/0001-82", empresa.getCNPJ());
    }

    @Test
    @DisplayName("Teste de validação de e-mail corporativo")
    void testValidacaoEmailCorporativo() {
        Empresa empresa = new Empresa();
        empresa.setEmailCorporativo("empresa@example.com");
        assertEquals("empresa@example.com", empresa.getEmailCorporativo());
    }

    @Test
    @DisplayName("Teste de inicialização com logradouro nulo")
    void testInicializacaoLogradouroNulo() {
        Empresa empresa = new Empresa();
        empresa.setLogradouro(null);
        assertNull(empresa.getLogradouro());
    }

    @Test
    @DisplayName("Teste de desativação de empresa")
    void testDesativacaoEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setAtivo(false);
        assertFalse(empresa.isAtivo());
    }

    @Test
    @DisplayName("Teste de verificação de igualdade entre empresas")
    void testIgualdadeEmpresas() {
        Empresa empresa1 = new Empresa();
        empresa1.setId(1L);
        empresa1.setNomeEmpresa("Empresa 1");

        Empresa empresa2 = new Empresa();
        empresa2.setId(1L);
        empresa2.setNomeEmpresa("Empresa 1");

        assertEquals(empresa1, empresa2);
    }

    @Test
    @DisplayName("Teste de manipulação de logradouro")
    void testManipulacaoLogradouro() {
        Empresa empresa = new Empresa();
        Logradouro logradouro = new Logradouro();
        logradouro.setEndereco("Rua Exemplo");
        empresa.setLogradouro(logradouro);

        assertEquals(logradouro, empresa.getLogradouro());
    }

    @Test
    @DisplayName("Teste de validação de telefone")
    void testValidacaoTelefone() {
        Empresa empresa = new Empresa();
        empresa.setTelefone("(99) 99999-9999");
        assertEquals("(99) 99999-9999", empresa.getTelefone());
    }

    @Test
    @DisplayName("Teste de definição de senha")
    void testDefinicaoSenha() {
        Empresa empresa = new Empresa();
        empresa.setSenhaEmpresa("senha123");
        assertEquals("senha123", empresa.getSenhaEmpresa());
    }

    @Test
    @DisplayName("Teste de inicialização com valores padrão")
    void testInicializacaoValoresPadrao() {
        Empresa empresa = new Empresa();
        assertNotNull(empresa);
        assertNull(empresa.getId());
        assertNull(empresa.getNomeEmpresa());
        assertNull(empresa.getRazaoSocial());
        assertNull(empresa.getCNPJ());
        assertNull(empresa.getTelefone());
        assertNull(empresa.getEmailCorporativo());
        assertNull(empresa.getSenhaEmpresa());
        assertNull(empresa.getLogradouro());
        assertFalse(empresa.isAtivo());
    }

    @Test
    @DisplayName("Teste de clonagem profunda")
    void testClonagemProfunda() {
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNomeEmpresa("Empresa 1");

        Empresa clone = new Empresa();
        clone.setId(empresa.getId());
        clone.setNomeEmpresa(empresa.getNomeEmpresa());

        assertEquals(empresa, clone);
    }
}