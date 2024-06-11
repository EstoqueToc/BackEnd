package com.example.crud.dto.consulta;

import com.example.crud.Model.Fornecedor;
import com.example.crud.Model.Logradouro;
import com.example.crud.dto.consultaDto.FornecedorConsultaDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FornecedorConsultaDtoTest {

    @Test
    public void testConstructorWithFornecedor() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(1L);
        fornecedor.setNomeFantasia("Fantasia A");
        fornecedor.setRazaoSocial("Razão Social A");
        fornecedor.setTelefone("(99) 99999-9999");
        fornecedor.setEmail("fornecedor@example.com");
        fornecedor.setCnpj("52.254.752/0001-82");
        fornecedor.setLogradouro(new Logradouro());

        FornecedorConsultaDto dto = new FornecedorConsultaDto(fornecedor);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Fantasia A", dto.getNomeFantasia());
        assertEquals("Razão Social A", dto.getRazaoSocial());
        assertEquals("(99) 99999-9999", dto.getTelefone());
        assertEquals("fornecedor@example.com", dto.getEmail());
        assertEquals("52.254.752/0001-82", dto.getCnpj());
    }
}
