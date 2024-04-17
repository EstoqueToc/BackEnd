package com.example.crud.Controller;

import com.example.crud.Model.Categoria;
import com.example.crud.Model.Produto;
import com.example.crud.repository.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    CategoriaRepository repository;

    @Operation(summary = "Retorna todas as categorias")
    @GetMapping
    public ResponseEntity<List<Categoria>> getCategoria() {
        var lista = repository.findAll();
        return lista.isEmpty() ? status(204).build() : status(200).body(lista);
    }

    @Operation(summary = "Retorna uma categoria pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable @Parameter(description = "ID da categoria para busca") Long id) {
        return of(repository.findById(id));
    }

    @Operation(summary = "Cria uma nova categoria")
    @PostMapping
    public ResponseEntity<Categoria> criarCategoria(@Parameter(description = "Objeto da categoria a ser criado") @RequestBody @Valid Categoria novaCategoria) {
        repository.save(novaCategoria);
        return status(201).body(novaCategoria);
    }

    @Operation(summary = "Atualiza uma categoria pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable @Parameter(description = "ID do produto para atualização") Long id, @RequestBody @Valid Categoria categoriaAtualizada) {
        if (!repository.existsById(id)) {
            return status(404).build();
        }
        categoriaAtualizada.setId(id);
        repository.save(categoriaAtualizada);
        return status(200).body(categoriaAtualizada);
    }

    @Operation(summary = "Deleta uma categoria pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable @Parameter(description = "ID da categoria para exclusão") Long id) {
        if (!repository.existsById(id)) {
            return status(404).build();
        }
        repository.deleteById(id);
        return status(204).build();
    }

    @Operation(summary = "ordenando por categoria")
    @GetMapping("/ordenar-categoria")
    public ResponseEntity<List<Categoria>> listarCategoria() {
        var lista = repository.findAllByOrderByNomeAsc();
        return lista.isEmpty() ? status(204).build() : status(200).body(lista);
    }

    @Operation(summary = "Pesquisa categorias por nome")
    @GetMapping("/pesquisar/{nome}")
    public ResponseEntity<List<Categoria>> pesquisarCategoriaPorNome(@PathVariable String nome) {
        var categorias = repository.findByNomeContainsIgnoreCase(nome);
        return categorias.isEmpty() ? status(204).build() : status(200).body(categorias);
    }


}






