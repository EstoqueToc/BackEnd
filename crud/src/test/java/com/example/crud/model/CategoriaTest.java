package com.example.crud.model;

import com.example.crud.Model.Categoria;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriaTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve retornar corretamente o nome da categoria")
    void getNome() {
        Categoria categoria = new Categoria();
        categoria.setNome("Categoria A");
        assertEquals("Categoria A", categoria.getNome());
    }

    @Test
    @DisplayName("Deve retornar corretamente a descrição da categoria")
    void getDescricao() {
        Categoria categoria = new Categoria();
        categoria.setDescricao("Descrição da Categoria A");
        assertEquals("Descrição da Categoria A", categoria.getDescricao());
    }

    @Test
    @DisplayName("Deve construir corretamente um objeto Categoria com todos os campos")
    void categoriaConstructor() {
        Categoria categoria = new Categoria("Categoria B", "Descrição da Categoria B");

        assertAll(
                () -> assertEquals("Categoria B", categoria.getNome()),
                () -> assertEquals("Descrição da Categoria B", categoria.getDescricao())
        );
    }

    @Test
    @DisplayName("Deve retornar corretamente o ID da categoria")
    void getId() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        assertEquals(1L, categoria.getId());
    }

    @Test
    @DisplayName("Deve verificar corretamente a igualdade entre duas categorias")
    void testEquals() {
        Categoria categoria1 = new Categoria("Categoria A", "Descrição A");
        Categoria categoria2 = new Categoria("Categoria A", "Descrição A");
        Categoria categoria3 = new Categoria("Categoria B", "Descrição B");

        categoria1.setId(1L);
        categoria2.setId(1L);
        categoria3.setId(2L);

        assertEquals(categoria1, categoria2);
        assertNotEquals(categoria1, categoria3);
    }

    @Test
    @DisplayName("Deve retornar corretamente o hash code da categoria")
    void testHashCode() {
        Categoria categoria1 = new Categoria("Categoria A", "Descrição A");
        Categoria categoria2 = new Categoria("Categoria A", "Descrição A");

        categoria1.setId(1L);
        categoria2.setId(1L);

        assertEquals(categoria1.hashCode(), categoria2.hashCode());
    }

    @Test
    @DisplayName("Deve detectar um nome inválido (em branco)")
    void setNomeInvalido() {
        Categoria categoria = new Categoria();
        categoria.setNome("");

        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);
        assertFalse(violations.isEmpty());

        for (ConstraintViolation<Categoria> violation : violations) {
            if (violation.getPropertyPath().toString().equals("nome")) {
                assertEquals("não deve estar em branco", violation.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Deve detectar uma descrição inválida (em branco)")
    void setDescricaoInvalida() {
        Categoria categoria = new Categoria();
        categoria.setDescricao("");

        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);
        assertFalse(violations.isEmpty());

        for (ConstraintViolation<Categoria> violation : violations) {
            if (violation.getPropertyPath().toString().equals("descricao")) {
                assertEquals("não deve estar em branco", violation.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Deve construir corretamente um objeto Categoria com um nome")
    void categoriaConstructorWithName() {
        Categoria categoria = new Categoria("Categoria C");
        assertEquals("Categoria C", categoria.getNome());
    }
}
