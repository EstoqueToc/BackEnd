package com.example.crud.service;

import com.example.crud.Model.Empresa;
import com.example.crud.dto.consultaDto.EmpresaConsultaDto;
import com.example.crud.dto.criacaoDto.EmpresaCriacaoDto;
import com.example.crud.repository.EmpresaRepository;
import com.example.crud.repository.LogradouroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private LogradouroRepository logradouroRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmpresaService empresaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar todas as empresas")
    public void testGetEmpresas() {
        List<Empresa> listaEmpresas = new ArrayList<>();
        listaEmpresas.add(new Empresa());
        listaEmpresas.add(new Empresa());

        when(empresaRepository.findAll()).thenReturn(listaEmpresas);
        when(modelMapper.map(any(Empresa.class), eq(EmpresaConsultaDto.class))).thenReturn(new EmpresaConsultaDto());

        List<EmpresaConsultaDto> result = empresaService.getEmpresas();

        assertEquals(2, result.size());
        verify(empresaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar uma empresa pelo ID")
    public void testGetEmpresaById() {
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        EmpresaConsultaDto empresaConsultaDto = new EmpresaConsultaDto();

        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));
        when(modelMapper.map(empresa, EmpresaConsultaDto.class)).thenReturn(empresaConsultaDto);

        Optional<EmpresaConsultaDto> result = empresaService.getEmpresaById(1L);

        assertTrue(result.isPresent());
        assertEquals(empresaConsultaDto, result.get());
        verify(empresaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve criar uma nova empresa")
    public void testCriarEmpresa() {
        EmpresaCriacaoDto novaEmpresaDto = new EmpresaCriacaoDto();
        Empresa novaEmpresa = new Empresa();

        when(modelMapper.map(novaEmpresaDto, Empresa.class)).thenReturn(novaEmpresa);
        when(empresaRepository.save(novaEmpresa)).thenReturn(novaEmpresa);

        Empresa result = empresaService.criarEmpresa(novaEmpresaDto);

        assertEquals(novaEmpresa, result);
        verify(logradouroRepository, times(1)).save(novaEmpresa.getLogradouro());
        verify(empresaRepository, times(1)).save(novaEmpresa);
    }

    @Test
    @DisplayName("Deve atualizar uma empresa existente")
    public void testAtualizarEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        EmpresaCriacaoDto empresaAtualizadaDto = new EmpresaCriacaoDto();
        EmpresaConsultaDto empresaConsultaDto = new EmpresaConsultaDto();

        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));
        when(empresaRepository.save(empresa)).thenReturn(empresa);
        when(modelMapper.map(empresa, EmpresaConsultaDto.class)).thenReturn(empresaConsultaDto);

        Optional<EmpresaConsultaDto> result = empresaService.atualizarEmpresa(1L, empresaAtualizadaDto);

        assertTrue(result.isPresent());
        assertEquals(empresaConsultaDto, result.get());
        verify(empresaRepository, times(1)).findById(1L);
        verify(empresaRepository, times(1)).save(empresa);
    }

    @Test
    @DisplayName("Deve deletar uma empresa pelo ID")
    public void testDeletarEmpresa() {
        when(empresaRepository.existsById(1L)).thenReturn(true);

        boolean result = empresaService.deletarEmpresa(1L);

        assertTrue(result);
        verify(empresaRepository, times(1)).existsById(1L);
        verify(empresaRepository, times(1)).deleteById(1L);
    }
}
