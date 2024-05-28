package com.example.crud.service;

import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaDto.ProdutoConsultaDto;
import com.example.crud.dto.criacaoDto.ProdutoCriacaoDto;
import com.example.crud.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProdutoServiceTest {
    ProdutoService service;
    ProdutoRepository produtoRepository;
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        produtoRepository = mock(ProdutoRepository.class);
        modelMapper = new ModelMapper();
        service = new ProdutoService(produtoRepository, modelMapper);
    }



    @DisplayName("Deve retornar a lista de produtos")
    @Test
    void getProdutos() {
        List<Produto> listaEsperada = List.of(mock(Produto.class),
                mock(Produto.class),
                mock(Produto.class));

        when(produtoRepository.findAll()).thenReturn(listaEsperada);

        List<ProdutoConsultaDto> resultado = service.getProdutos();

        assertEquals(listaEsperada.size(), resultado.size());
    }


}
