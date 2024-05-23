package com.example.crud.dto.consulta;

import com.example.crud.Model.Empresa;
import com.example.crud.Model.Logradouro;
import com.example.crud.dto.consultaDto.EmpresaConsultaDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmpresaConsultaDtoTest {

    @Test
    public void testConstructorWithEmpresa() {
        Logradouro logradouro = new Logradouro();
        logradouro.setRua("Rua das Flores");
        logradouro.setNumero("123");
        logradouro.setComplemento("Apto 101");
        logradouro.setCidade("São Paulo");
        logradouro.setEstado("SP");
        logradouro.setCep("12345-678");
        logradouro.setPais("Brasil");

        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNomeEmpresa("Minha Empresa");
        empresa.setRazaoSocial("Razão Social");
        empresa.setCNPJ("52.254.752/0001-82");
        empresa.setTelefone("(99) 99999-9999");
        empresa.setEmailCorporativo("empresa@example.com");
        empresa.setSenhaEmpresa("senha123");
        empresa.setLogradouro(logradouro);
        empresa.setAtivo(true);

        EmpresaConsultaDto dto = new EmpresaConsultaDto(empresa);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Minha Empresa", dto.getNomeEmpresa());
        assertEquals("Razão Social", dto.getRazaoSocial());
        assertEquals("52.254.752/0001-82", dto.getCNPJ());
        assertEquals("(99) 99999-9999", dto.getTelefone());
        assertEquals("empresa@example.com", dto.getEmailCorporativo());
        assertEquals("senha123", dto.getSenhaEmpresa());
        assertEquals(logradouro, dto.getLogradouro());
        assertEquals(true, dto.isAtivo());
    }

    @Test
    public void testGetNome() {
        Empresa empresa = new Empresa();
        empresa.setNomeEmpresa("Minha Empresa");

        EmpresaConsultaDto dto = new EmpresaConsultaDto(empresa);

        assertEquals("Minha Empresa", dto.getNome());
    }
}
