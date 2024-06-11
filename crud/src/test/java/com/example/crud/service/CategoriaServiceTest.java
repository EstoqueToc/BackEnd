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

}
