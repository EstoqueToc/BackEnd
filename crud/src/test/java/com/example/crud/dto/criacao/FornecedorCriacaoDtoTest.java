package com.example.crud.dto.criacao;

import com.example.crud.dto.criacaoDto.FornecedorCriacaoDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FornecedorCriacaoDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testNomeNotBlank() {
        FornecedorCriacaoDto dto = new FornecedorCriacaoDto();
        dto.setNomeFantasia("");
        assertTrue(validator.validate(dto).size() > 0);
    }

    @Test
    public void testNomeFantasiaNotBlank() {
        FornecedorCriacaoDto dto = new FornecedorCriacaoDto();
        dto.setNomeFantasia("");
        assertTrue(validator.validate(dto).size() > 0);
    }

    @Test
    public void testRazaoSocialNotBlank() {
        FornecedorCriacaoDto dto = new FornecedorCriacaoDto();
        dto.setRazaoSocial("");
        assertTrue(validator.validate(dto).size() > 0);
    }

    @Test
    public void testTelefoneNotBlank() {
        FornecedorCriacaoDto dto = new FornecedorCriacaoDto();
        dto.setTelefone("");
        assertTrue(validator.validate(dto).size() > 0);
    }

    @Test
    public void testEmailValid() {
        FornecedorCriacaoDto dto = new FornecedorCriacaoDto();
        dto.setEmail("invalid-email");
        assertTrue(validator.validate(dto).size() > 0);
    }

    @Test
    public void testCnpjValid() {
        FornecedorCriacaoDto dto = new FornecedorCriacaoDto();
        dto.setCnpj("invalid-cnpj");
        assertTrue(validator.validate(dto).size() > 0);
    }

    @Test
    public void testValidDto() {
        FornecedorCriacaoDto dto = new FornecedorCriacaoDto();
        dto.setNomeFantasia("Nome Fantasia");
        dto.setRazaoSocial("Razão Social");
        dto.setTelefone("123456789");
        dto.setEmail("email@example.com");
        dto.setCnpj("68.484.147/0001-38");

        Set<ConstraintViolation<FornecedorCriacaoDto>> violations = validator.validate(dto);
        for (ConstraintViolation<FornecedorCriacaoDto> violation : violations) {
            System.out.println(violation.getPropertyPath() + " - " + violation.getMessage());
        }

        assertTrue(violations.isEmpty());
    }

}
