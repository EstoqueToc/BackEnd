package com.example.crud.service;

import com.example.crud.Model.Logradouro;
import com.example.crud.repository.LogradouroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LogradouroServiceTest {

    @Mock
    private LogradouroRepository repository;

    @InjectMocks
    private LogradouroService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Deve retornar todos os logradouros")
    @Test
    void getLogradouros() {
        List<Logradouro> logradouros = new ArrayList<>();
        when(repository.findAll()).thenReturn(logradouros);

        ResponseEntity<List<Logradouro>> response = service.getLogradouros();

        assertEquals(logradouros, response.getBody());
    }
}
