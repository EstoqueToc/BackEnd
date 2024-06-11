package com.example.crud.Controller;

import com.example.crud.Model.PerdaEstoque;
import com.example.crud.dto.consultaDto.PerdaEstoqueConsultaDto;
import com.example.crud.dto.consultaResposta.PerdaEstoqueRespostaDto;
import com.example.crud.dto.criacaoDto.PerdaEstoqueCriacaoDto;
import com.example.crud.repository.PerdaEstoqueRepository;
import com.example.crud.repository.ProdutoRepository;
import com.example.crud.service.usuario.PerdaEstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtosPerdidos")
@RequiredArgsConstructor
public class PerdaEstoqueController {

    private final PerdaEstoqueService perdaEstoqueService;
    private final ProdutoRepository produtoRepository;
    private final PerdaEstoqueRepository perdaEstoqueRepository;


    @Operation(summary = "Cria uma nova perda de estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perda de estoque criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PerdaEstoqueRespostaDto> criarPerdaEstoque(@Parameter(description = "Objeto da perda de estoque a ser criada") @RequestBody @Valid PerdaEstoqueCriacaoDto perdaEstoqueCriacaoDto) {
        PerdaEstoqueRespostaDto perdaEstoqueRespostaDto = perdaEstoqueService.criarPerdaEstoque(perdaEstoqueCriacaoDto);
        return ResponseEntity.status(201).body(perdaEstoqueRespostaDto);
    }

    @Operation(summary = "Retorna todas as perdas de estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perdas de estoque listadas com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhuma perda de estoque disponível", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<PerdaEstoqueConsultaDto>> getPerdasEstoque() {
        List<PerdaEstoqueConsultaDto> perdasEstoque = perdaEstoqueService.getPerdasEstoque();
        return ResponseEntity.status(perdasEstoque.isEmpty() ? 204 : 200).body(perdasEstoque);
    }
}
