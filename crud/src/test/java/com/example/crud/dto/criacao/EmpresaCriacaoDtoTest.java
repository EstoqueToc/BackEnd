package com.example.crud.dto.criacao;

import com.example.crud.Model.Logradouro;
import com.example.crud.dto.criacaoDto.EmpresaCriacaoDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmpresaCriacaoDtoTest {

    @Test
    public void testEmpresaCriacaoDto() {
        EmpresaCriacaoDto empresaCriacaoDto = new EmpresaCriacaoDto();
        empresaCriacaoDto.setNomeEmpresa("Minha Empresa");
        empresaCriacaoDto.setRazaoSocial("Razao Social");
        empresaCriacaoDto.setCNPJ("52.254.752/0001-82");
        empresaCriacaoDto.setTelefone("(99) 99999-9999");
        empresaCriacaoDto.setEmailCorpotativo("empresa@example.com");
        empresaCriacaoDto.setSenhaEmpresa("senha123");
        empresaCriacaoDto.setAtivo(true);
        Logradouro logradouro = new Logradouro();
        empresaCriacaoDto.setLogradouro(logradouro);

        assertEquals("Minha Empresa", empresaCriacaoDto.getNomeEmpresa());
        assertEquals("Razao Social", empresaCriacaoDto.getRazaoSocial());
        assertEquals("52.254.752/0001-82", empresaCriacaoDto.getCNPJ());
        assertEquals("(99) 99999-9999", empresaCriacaoDto.getTelefone());
        assertEquals("empresa@example.com", empresaCriacaoDto.getEmailCorpotativo());
        assertEquals("senha123", empresaCriacaoDto.getSenhaEmpresa());
        assertEquals(true, empresaCriacaoDto.isAtivo());
        assertNotNull(empresaCriacaoDto.getLogradouro());
    }
}
