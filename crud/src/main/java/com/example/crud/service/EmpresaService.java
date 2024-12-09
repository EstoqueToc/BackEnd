package com.example.crud.service;

import com.example.crud.Model.Empresa;
import com.example.crud.dto.consultaDto.EmpresaConsultaDto;
import com.example.crud.dto.criacaoDto.EmpresaCriacaoDto;
import com.example.crud.repository.EmpresaRepository;
import com.example.crud.repository.LogradouroRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final LogradouroRepository logradouroRepository;
    private final ModelMapper modelMapper;

    public List<EmpresaConsultaDto> getEmpresas() {
        List<Empresa> lista = empresaRepository.findAll();
        return lista.stream()
                .map(empresa -> modelMapper.map(empresa, EmpresaConsultaDto.class))
                .collect(Collectors.toList());
    }

    public EmpresaConsultaDto getEmpresaById(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));
        return modelMapper.map(empresa, EmpresaConsultaDto.class);
    }

    public Empresa criarEmpresa(EmpresaCriacaoDto novaEmpresaDto) {
        Empresa novaEmpresa = modelMapper.map(novaEmpresaDto, Empresa.class);
        if(novaEmpresaDto.getLogradouro() == null){
            novaEmpresa.setLogradouro(null);
        }
        var logradouro = logradouroRepository.findById(novaEmpresaDto.getLogradouro().getId())
                .orElseThrow(() -> new IllegalArgumentException("Logradouro não encontrado"));
        novaEmpresa.setLogradouro(logradouro);

        return empresaRepository.save(novaEmpresa);
    }

    public Optional<EmpresaConsultaDto> atualizarEmpresa(Long id, EmpresaCriacaoDto empresaAtualizadaDto) {
        return empresaRepository.findById(id)
                .map(empresa -> {
                    modelMapper.map(empresaAtualizadaDto, empresa);
                    empresaRepository.save(empresa);
                    return modelMapper.map(empresa, EmpresaConsultaDto.class);
                });
    }

    public boolean deletarEmpresa(Long id) {
        if (empresaRepository.existsById(id)) {
            empresaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
