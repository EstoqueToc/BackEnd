package com.example.crud.Controller;

import com.example.crud.dto.consultaDto.CategoriaConsultaDto;
import com.example.crud.dto.criacaoDto.CategoriaCriacaoDto;
import com.example.crud.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Operation(summary = "Retorna todas as categorias")
    @ApiResponse(responseCode = "200", description = "Lista de categorias recuperada com sucesso")
    @ApiResponse(responseCode = "204", description = "Nenhuma categoria disponível", content = @Content)
    @GetMapping
    public ResponseEntity<List<CategoriaConsultaDto>> getCategoria() {
        return categoriaService.getAllCategorias();
    }

    @Operation(summary = "Retorna uma categoria pelo ID")
    @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Categoria com o ID fornecido não encontrada", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaConsultaDto> getCategoriaById(@Parameter(description = "ID da categoria para busca") @PathVariable Long id) {
        return categoriaService.getCategoriaById(id);
    }

    @Operation(summary = "Cria uma nova categoria")
    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso")
    @PostMapping
    public ResponseEntity<CategoriaConsultaDto> criarCategoria(@Parameter(description = "Objeto da categoria a ser criado") @Valid @RequestBody CategoriaCriacaoDto novaCategoriaDto) {
        return categoriaService.criarCategoria(novaCategoriaDto);
    }

    @Operation(summary = "Atualiza uma categoria pelo ID")
    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Categoria com o ID fornecido não encontrada", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaConsultaDto> atualizarCategoria(@Parameter(description = "ID da categoria para atualização") @PathVariable Long id, @Valid @RequestBody CategoriaCriacaoDto categoriaAtualizadaDto) {
        return categoriaService.atualizarCategoria(id, categoriaAtualizadaDto);
    }

    @Operation(summary = "Deleta uma categoria pelo ID")
    @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Categoria com o ID fornecido não encontrada", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarCategoria(@Parameter(description = "ID da categoria para exclusão") @PathVariable Long id) {
        return categoriaService.deletarCategoria(id);
    }

    @Operation(summary = "Ordenando por categoria")
    @ApiResponse(responseCode = "200", description = "Categorias ordenadas com sucesso")
    @ApiResponse(responseCode = "204", description = "Nenhuma categoria disponível para ordenação", content = @Content)
    @GetMapping("/ordenar-categoria")
    public ResponseEntity<List<CategoriaConsultaDto>> listarCategoria() {
        return categoriaService.listarCategoriaOrdenada();
    }

    @Operation(summary = "Pesquisa categorias por nome")
    @ApiResponse(responseCode = "200", description = "Categorias encontradas com sucesso")
    @ApiResponse(responseCode = "204", description = "Nenhuma categoria encontrada com o nome fornecido", content = @Content)
    @GetMapping("/pesquisar/{nome}")
    public ResponseEntity<List<CategoriaConsultaDto>> pesquisarCategoriaPorNome(@Parameter(description = "Nome da categoria para pesquisa") @PathVariable String nome) {
        return categoriaService.pesquisarCategoriaPorNome(nome);
    }
}