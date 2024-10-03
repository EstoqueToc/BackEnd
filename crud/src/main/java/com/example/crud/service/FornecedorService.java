package com.example.crud.service;

import com.example.crud.Model.Fornecedor;
import com.example.crud.dto.consultaDto.FornecedorConsultaDto;
import com.example.crud.dto.criacaoDto.FornecedorCriacaoDto;
import com.example.crud.repository.FornecedorRepository;
import com.example.crud.repository.LogradouroRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository repository;
    private final LogradouroRepository logradouroRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<FornecedorConsultaDto> adicionarFornecedor(FornecedorCriacaoDto novoFornecedorDto) {
        Fornecedor novoFornecedor = modelMapper.map(novoFornecedorDto, Fornecedor.class);
        repository.save(novoFornecedor);
        FornecedorConsultaDto fornecedorCriadoDto = modelMapper.map(novoFornecedor, FornecedorConsultaDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorCriadoDto);
    }

    public ResponseEntity<List<FornecedorConsultaDto>> getFornecedores() {
        List<Fornecedor> fornecedores = repository.findAll();
        List<FornecedorConsultaDto> fornecedoresDto = fornecedores.stream()
                .map(fornecedor -> modelMapper.map(fornecedor, FornecedorConsultaDto.class))
                .collect(Collectors.toList());
        return fornecedores.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(fornecedoresDto);
    }

    public ResponseEntity<FornecedorConsultaDto> atualizarFornecedor(Long id, FornecedorCriacaoDto fornecedorDto) {
        return repository.findById(id)
                .map(fornecedor -> {
                    modelMapper.map(fornecedorDto, fornecedor);
                    fornecedor.setId(id);
                    repository.save(fornecedor);
                    FornecedorConsultaDto fornecedorAtualizadoDto = modelMapper.map(fornecedor, FornecedorConsultaDto.class);
                    return ResponseEntity.ok(fornecedorAtualizadoDto);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<Object> deletarFornecedor(Long id) {
        return repository.findById(id)
                .map(fornecedor -> {
                    repository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<List<Fornecedor>> getFornecedorPorNome(String nome) {
        List<Fornecedor> fornecedores = repository.findByNomeFantasiaContainsIgnoreCase(nome);
        return fornecedores.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(fornecedores);
    }

    public ResponseEntity<List<Fornecedor>> listarFornecedorOrdenado() {
        var lista = repository.findAllByOrderByNomeFantasiaAsc();
        return lista.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(lista);
    }

    public ResponseEntity<Fornecedor> getFornecedorById(Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public List<Fornecedor> getFornecedorByEmpresaId(long id) {
        return repository.findFornecedorByEmpresaId(id);
    }
}