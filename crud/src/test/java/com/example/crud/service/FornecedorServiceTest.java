package com.example.crud.service;

import com.example.crud.Model.Fornecedor;
import com.example.crud.dto.consultaDto.FornecedorConsultaDto;
import com.example.crud.dto.criacaoDto.FornecedorCriacaoDto;
import com.example.crud.repository.FornecedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FornecedorServiceTest {

    @Mock
    private FornecedorRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FornecedorService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdicionarFornecedor() {
        FornecedorCriacaoDto novoFornecedorDto = new FornecedorCriacaoDto();
        FornecedorConsultaDto fornecedorCriadoDto = new FornecedorConsultaDto();
        when(modelMapper.map(novoFornecedorDto, Fornecedor.class)).thenReturn(new Fornecedor());
        when(modelMapper.map(any(Fornecedor.class), eq(FornecedorConsultaDto.class))).thenReturn(fornecedorCriadoDto);

        ResponseEntity<FornecedorConsultaDto> response = service.adicionarFornecedor(novoFornecedorDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(fornecedorCriadoDto, response.getBody());
        verify(repository, times(1)).save(any(Fornecedor.class));
    }

    @Test
    public void testGetFornecedores() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        fornecedores.add(new Fornecedor());
        when(repository.findAll()).thenReturn(fornecedores);
        when(modelMapper.map(any(Fornecedor.class), eq(FornecedorConsultaDto.class))).thenReturn(new FornecedorConsultaDto());

        ResponseEntity<List<FornecedorConsultaDto>> response = service.getFornecedores();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
