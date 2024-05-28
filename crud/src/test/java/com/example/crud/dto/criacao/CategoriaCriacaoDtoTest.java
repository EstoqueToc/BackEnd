import com.example.crud.dto.criacaoDto.CategoriaCriacaoDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoriaCriacaoDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCategoriaCriacaoDtoValidation() {
        CategoriaCriacaoDto categoriaCriacaoDto = new CategoriaCriacaoDto();
        categoriaCriacaoDto.setNome("Eletrônicos");
        categoriaCriacaoDto.setDescricao("Categoria para produtos eletrônicos");

        Set<ConstraintViolation<CategoriaCriacaoDto>> violations = validator.validate(categoriaCriacaoDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testCategoriaCriacaoDtoValidationMissingFields() {
        CategoriaCriacaoDto categoriaCriacaoDto = new CategoriaCriacaoDto();

        Set<ConstraintViolation<CategoriaCriacaoDto>> violations = validator.validate(categoriaCriacaoDto);
        assertEquals(2, violations.size());
    }
}
