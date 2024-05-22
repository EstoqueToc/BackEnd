package com.example.crud.service;

import com.example.crud.Model.Empresa;
import com.example.crud.dto.consultaDto.EmpresaConsultaDto;
import com.example.crud.dto.criacaoDto.EmpresaCriacaoDto;
import com.example.crud.repository.EmpresaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<EmpresaConsultaDto> getEmpresas() {
        List<Empresa> lista = repository.findAll();
        return lista.stream()
                .map(empresa -> modelMapper.map(empresa, EmpresaConsultaDto.class))
                .collect(Collectors.toList());
    }

    public Optional<EmpresaConsultaDto> getEmpresaById(Long id) {
        return repository.findById(id)
                .map(empresa -> modelMapper.map(empresa, EmpresaConsultaDto.class));
    }

    public Empresa criarEmpresa(EmpresaCriacaoDto novaEmpresaDto) {
        Empresa novaEmpresa = modelMapper.map(novaEmpresaDto, Empresa.class);
        return repository.save(novaEmpresa);
    }

    public Optional<EmpresaConsultaDto> atualizarEmpresa(Long id, EmpresaCriacaoDto empresaAtualizadaDto) {
        return repository.findById(id)
                .map(empresa -> {
                    modelMapper.map(empresaAtualizadaDto, empresa);
                    empresa.setId(id);
                    repository.save(empresa);
                    return modelMapper.map(empresa, EmpresaConsultaDto.class);
                });
    }

    public boolean deletarEmpresa(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}