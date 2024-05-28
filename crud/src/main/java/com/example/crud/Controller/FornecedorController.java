package com.example.crud.Controller;

import com.example.crud.Interface.IUpDate;
import com.example.crud.Model.Fornecedor;
import com.example.crud.dto.consultaDto.FornecedorConsultaDto;
import com.example.crud.dto.criacaoDto.FornecedorCriacaoDto;
import com.example.crud.service.FornecedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController implements IUpDate {

    List<Fornecedor> fornecedores = new ArrayList<>();

    @Autowired
    private FornecedorService fornecedorService;

    @Operation(summary = "Adiciona um novo fornecedor à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fornecedor adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<FornecedorConsultaDto> adicionarFornecedor(
            @Parameter(description = "Objeto de fornecedor com os dados para criação") @Valid @RequestBody FornecedorCriacaoDto novoFornecedorDto) {
        return fornecedorService.adicionarFornecedor(novoFornecedorDto);
    }

    @Operation(summary = "Retorna a lista de fornecedores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de fornecedores recuperada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum fornecedor disponível", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<FornecedorConsultaDto>> getFornecedores() {
        return fornecedorService.getFornecedores();
    }

    @Operation(summary = "Atualiza os dados de um fornecedor pelo índice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Índice inválido", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<FornecedorConsultaDto> atualizarFornecedor(
            @Parameter(description = "ID do fornecedor a ser atualizado") @PathVariable Long id,
            @Parameter(description = "Dados do fornecedor para atualização") @Valid @RequestBody FornecedorCriacaoDto fornecedorDto) {
        return fornecedorService.atualizarFornecedor(id, fornecedorDto);
    }

    @Operation(summary = "Remove um fornecedor da lista pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "ID inválido", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@Parameter(description = "ID do fornecedor na lista para remoção") @PathVariable Long id) {
        return fornecedorService.deletarFornecedor(id);
    }

    @Operation(summary = "Aplica um desconto ao preço de um fornecedor pelo índice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Desconto aplicado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado ou percentual de desconto inválido", content = @Content)
    })
    @PutMapping("/desconto/{indice}")
    public ResponseEntity<String> aplicarDesconto(
            @Parameter(description = "Índice do fornecedor na lista") @PathVariable int indice,
            @Parameter(description = "Percentual de desconto a ser aplicado ao preço do fornecedor") @RequestParam("percentualDesconto") double percentualDesconto) {
        return fornecedorService.aplicarDesconto(indice, percentualDesconto, fornecedores);
    }

    @Operation(summary = "Pesquisa fornecedores por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedores encontrados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum fornecedor encontrado com o nome fornecido", content = @Content)
    })
    @GetMapping("/fornecedor/{nome}")
    public ResponseEntity<List<Fornecedor>> getFornecedorPorNome(@PathVariable @Parameter(description = "Nome do fornecedor para pesquisa") String nome) {
        return fornecedorService.getFornecedorPorNome(nome);
    }

    @Operation(summary = "Ordena os fornecedores por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedores ordenados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum fornecedor disponível para ordenação", content = @Content)
    })
    @GetMapping("/ordenar-fornecedor")
    public ResponseEntity<List<Fornecedor>> listarFornecedor() {
        return fornecedorService.listarFornecedorOrdenado();
    }

    @Operation(summary = "Retorna um fornecedor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fornecedor com o ID fornecido não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable @Parameter(description = "ID do fornecedor para busca") Long id) {
        return fornecedorService.getFornecedorById(id);
    }
}