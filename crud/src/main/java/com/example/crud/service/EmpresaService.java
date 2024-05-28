package com.example.crud.service;

import com.example.crud.Model.Empresa;
import com.example.crud.dto.consultaDto.EmpresaConsultaDto;
import com.example.crud.dto.criacaoDto.EmpresaCriacaoDto;
import com.example.crud.repository.EmpresaRepository;
import com.example.crud.repository.LogradouroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final LogradouroRepository logradouroRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository, LogradouroRepository logradouroRepository, ModelMapper modelMapper) {
        this.empresaRepository = empresaRepository;
        this.logradouroRepository = logradouroRepository;
        this.modelMapper = modelMapper;
    }

    public List<EmpresaConsultaDto> getEmpresas() {
        List<Empresa> lista = empresaRepository.findAll();
        return lista.stream()
                .map(empresa -> modelMapper.map(empresa, EmpresaConsultaDto.class))
                .collect(Collectors.toList());
    }

    public Optional<EmpresaConsultaDto> getEmpresaById(Long id) {
        return empresaRepository.findById(id)
                .map(empresa -> modelMapper.map(empresa, EmpresaConsultaDto.class));
    }

    public Empresa criarEmpresa(EmpresaCriacaoDto novaEmpresaDto) {
        Empresa novaEmpresa = modelMapper.map(novaEmpresaDto, Empresa.class);
        logradouroRepository.save(novaEmpresa.getLogradouro());
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
