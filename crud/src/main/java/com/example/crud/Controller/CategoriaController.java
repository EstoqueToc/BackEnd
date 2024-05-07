package com.example.crud.Controller;

import com.example.crud.Model.Categoria;
import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaDto.CategoriaConsultaDto;
import com.example.crud.dto.criacaoDto.CategoriaCriacaoDto;
import com.example.crud.repository.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    CategoriaRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Retorna todas as categorias")
    @ApiResponse(responseCode = "200", description = "Lista de categorias recuperada com sucesso")
    @ApiResponse(responseCode = "204", description = "Nenhuma categoria disponível", content = @Content)
    @GetMapping
    public ResponseEntity<List<CategoriaConsultaDto>> getCategoria() {
        List<Categoria> lista = repository.findAll();
        List<CategoriaConsultaDto> listaDto = lista.stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaConsultaDto.class))
                .collect(Collectors.toList());
        return lista.isEmpty() ? status(204).build() : status(200).body(listaDto);
    }

    @Operation(summary = "Retorna uma categoria pelo ID")
    @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Categoria com o ID fornecido não encontrada", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaConsultaDto> getCategoriaById(@Parameter(description = "ID da categoria para busca") @PathVariable Long id) {
        return repository.findById(id)
                .map(categoria -> status(200).body(modelMapper.map(categoria, CategoriaConsultaDto.class)))
                .orElse(status(404).build());
    }

    @Operation(summary = "Cria uma nova categoria")
    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso")
    @PostMapping
    public ResponseEntity<CategoriaConsultaDto> criarCategoria(@Parameter(description = "Objeto da categoria a ser criado") @Valid @RequestBody CategoriaCriacaoDto novaCategoriaDto) {
        Categoria novaCategoria = modelMapper.map(novaCategoriaDto, Categoria.class);
        repository.save(novaCategoria);
        return status(201).body(modelMapper.map(novaCategoria, CategoriaConsultaDto.class));
    }

    @Operation(summary = "Atualiza uma categoria pelo ID")
    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Categoria com o ID fornecido não encontrada", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaConsultaDto> atualizarCategoria(@Parameter(description = "ID da categoria para atualização") @PathVariable Long id, @Valid @RequestBody CategoriaCriacaoDto categoriaAtualizadaDto) {
        return repository.findById(id)
                .map(categoria -> {
                    modelMapper.map(categoriaAtualizadaDto, categoria);
                    categoria.setCategoria_id(id);
                    repository.save(categoria);
                    return status(200).body(modelMapper.map(categoria, CategoriaConsultaDto.class));
                })
                .orElse(status(404).build());
    }

    @Operation(summary = "Deleta uma categoria pelo ID")
    @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Categoria com o ID fornecido não encontrada", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@Parameter(description = "ID da categoria para exclusão") @PathVariable Long id) {
        return repository.findById(id)
                .map(categoria -> {
                    repository.deleteById(id);
                    return status(204).<Void>build();
                })
                .orElse(status(404).build());
    }


    @Operation(summary = "Ordenando por categoria")
    @ApiResponse(responseCode = "200", description = "Categorias ordenadas com sucesso")
    @ApiResponse(responseCode = "204", description = "Nenhuma categoria disponível para ordenação", content = @Content)
    @GetMapping("/ordenar-categoria")
    public ResponseEntity<List<CategoriaConsultaDto>> listarCategoria() {
        var lista = repository.findAllByOrderByNomeAsc();
        List<CategoriaConsultaDto> listaDto = lista.stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaConsultaDto.class))
                .collect(Collectors.toList());
        return lista.isEmpty() ? status(204).build() : status(200).body(listaDto);
    }

    @Operation(summary = "Pesquisa categorias por nome")
    @ApiResponse(responseCode = "200", description = "Categorias encontradas com sucesso")
    @ApiResponse(responseCode = "204", description = "Nenhuma categoria encontrada com o nome fornecido", content = @Content)
    @GetMapping("/pesquisar/{nome}")
    public ResponseEntity<List<CategoriaConsultaDto>> pesquisarCategoriaPorNome(@Parameter(description = "Nome da categoria para pesquisa") @PathVariable String nome) {
        var categorias = repository.findByNomeContainsIgnoreCase(nome);
        List<CategoriaConsultaDto> listaDto = categorias.stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaConsultaDto.class))
                .collect(Collectors.toList());
        return categorias.isEmpty() ? status(204).build() : status(200).body(listaDto);
    }


}






