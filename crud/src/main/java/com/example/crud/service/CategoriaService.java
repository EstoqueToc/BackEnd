package com.example.crud.service;

import com.example.crud.Model.Categoria;
import com.example.crud.dto.consultaDto.CategoriaConsultaDto;
import com.example.crud.dto.criacaoDto.CategoriaCriacaoDto;
import com.example.crud.excecoes.RecursoNaoEncontradoException;
import com.example.crud.repository.CategoriaRepository;
import com.example.crud.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;
    private final EmpresaRepository empresaRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<List<CategoriaConsultaDto>> getAllCategorias() {
        List<Categoria> lista = repository.findAll();
        List<CategoriaConsultaDto> listaDto = lista.stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaConsultaDto.class))
                .collect(Collectors.toList());
        return lista.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(listaDto);
    }

    public ResponseEntity<CategoriaConsultaDto> getCategoriaById(Long id) {
        return repository.findById(id)
                .map(categoria -> ResponseEntity.ok(modelMapper.map(categoria, CategoriaConsultaDto.class)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<CategoriaConsultaDto> criarCategoria(CategoriaCriacaoDto novaCategoriaDto) {
        Categoria novaCategoria = modelMapper.map(novaCategoriaDto, Categoria.class);
        var empresa = empresaRepository.findById(novaCategoriaDto.getEmpresa().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria", novaCategoria.getEmpresa().getId()));
        novaCategoria.setEmpresa(empresa);
        repository.save(novaCategoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(novaCategoria, CategoriaConsultaDto.class));
    }

    public ResponseEntity<CategoriaConsultaDto> atualizarCategoria(Long id, CategoriaCriacaoDto categoriaAtualizadaDto) {
        return repository.findById(id)
                .map(categoria -> {
                    categoria.setNome(categoriaAtualizadaDto.getNome());
                    var empresa = empresaRepository.findById(categoriaAtualizadaDto.getEmpresa().getId())
                            .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria", categoriaAtualizadaDto.getEmpresa().getId()));
                    categoria.setEmpresa(empresa);
                    repository.save(categoria);
                    return ResponseEntity.ok(modelMapper.map(categoria, CategoriaConsultaDto.class));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<Object> deletarCategoria(Long id) {
        return repository.findById(id)
                .map(categoria -> {
                    repository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<List<CategoriaConsultaDto>> listarCategoriaOrdenada() {
        var lista = repository.findAllByOrderByNomeAsc();
        List<CategoriaConsultaDto> listaDto = lista.stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaConsultaDto.class))
                .collect(Collectors.toList());
        return lista.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(listaDto);
    }

    public ResponseEntity<List<CategoriaConsultaDto>> pesquisarCategoriaPorNome(String nome) {
        var categorias = repository.findByNomeContainsIgnoreCase(nome);
        List<CategoriaConsultaDto> listaDto = categorias.stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaConsultaDto.class))
                .collect(Collectors.toList());
        return categorias.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(listaDto);
    }

    public ResponseEntity<List<CategoriaConsultaDto>> getCategoriaByEmpresaId(Long id) {
        var categorias = repository.findCategoriaByEmpresaId(id);
        List<CategoriaConsultaDto> listaDto = categorias.stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaConsultaDto.class))
                .collect(Collectors.toList());
        return categorias.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(listaDto);
    }
}
