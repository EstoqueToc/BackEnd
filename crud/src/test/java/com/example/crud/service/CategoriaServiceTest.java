package com.example.crud.service;

import com.example.crud.Model.Categoria;
import com.example.crud.dto.consultaDto.CategoriaConsultaDto;
import com.example.crud.repository.CategoriaRepository;
import com.example.crud.service.CategoriaService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import jakarta.validation.metadata.ConstraintDescriptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Validator validator;

    @InjectMocks
    private CategoriaService service;

    @Test
    public void testGetAllCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria());
        when(repository.findAll()).thenReturn(categorias);

        CategoriaConsultaDto dto = new CategoriaConsultaDto();
        // Configure os campos do DTO se necessário
        List<CategoriaConsultaDto> dtos = new ArrayList<>();
        dtos.add(dto);
        when(modelMapper.map(any(), eq(CategoriaConsultaDto.class))).thenReturn(dto);

        ResponseEntity<List<CategoriaConsultaDto>> response = service.getAllCategorias();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }

    @Test
    public void setDescricaoInvalida() {
        Categoria categoria = new Categoria();
        categoria.setDescricao("");

        when(validator.validate(categoria)).thenReturn(Set.of(
                new ConstraintViolation<Categoria>() {
                    @Override
                    public String getMessage() {
                        return "não deve estar em branco";
                    }

                    @Override
                    public String getMessageTemplate() {
                        return "";
                    }

                    @Override
                    public Categoria getRootBean() {
                        return categoria;
                    }

                    @Override
                    public Class<Categoria> getRootBeanClass() {
                        return Categoria.class;
                    }

                    @Override
                    public Object getLeafBean() {
                        return categoria;
                    }

                    @Override
                    public Object[] getExecutableParameters() {
                        return new Object[0];
                    }

                    @Override
                    public Object getExecutableReturnValue() {
                        return null;
                    }

                    @Override
                    public Path getPropertyPath() {
                        return null;
                    }

                    @Override
                    public Object getInvalidValue() {
                        return "";
                    }

                    @Override
                    public ConstraintDescriptor<?> getConstraintDescriptor() {
                        return null;
                    }

                    @Override
                    public <U> U unwrap(Class<U> type) {
                        return null;
                    }
                }
        ));

        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);
        assertFalse(violations.isEmpty());

        for (ConstraintViolation<Categoria> violation : violations) {
            assertEquals("não deve estar em branco", violation.getMessage());
        }
    }
}
