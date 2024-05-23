package com.example.crud.service;

import com.example.crud.Model.Categoria;
import com.example.crud.dto.consultaDto.CategoriaConsultaDto;
import com.example.crud.repository.CategoriaRepository;
import com.example.crud.service.CategoriaService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoriaService service;

    @Test
    public void testGetAllCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria());
        when(repository.findAll()).thenReturn(categorias);

        List<CategoriaConsultaDto> dtos = new ArrayList<>();
        dtos.add(new CategoriaConsultaDto());
        when(modelMapper.map(any(), any())).thenReturn(new CategoriaConsultaDto());

        ResponseEntity<List<CategoriaConsultaDto>> response = service.getAllCategorias();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }
}
