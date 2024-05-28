package com.example.crud.Controller;

import com.example.crud.Model.Alerta;
import com.example.crud.dto.consultaDto.AlertaConsultaDto;
import com.example.crud.dto.criacaoDto.AlertaCriacaoDto;
import com.example.crud.repository.AlertaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Cria um novo alerta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alerta criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AlertaConsultaDto> criarAlerta(@Parameter(description = "Objeto do alerta a ser criado") @RequestBody @Valid AlertaCriacaoDto novoAlertaDto) {
        Alerta novoAlerta = modelMapper.map(novoAlertaDto, Alerta.class);
        alertaRepository.save(novoAlerta);
        AlertaConsultaDto alertaCriadoDto = modelMapper.map(novoAlerta, AlertaConsultaDto.class);
        return status(201).body(alertaCriadoDto);
    }

    @Operation(summary = "Retorna todos os alertas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alertas listados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum alerta disponível", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<AlertaConsultaDto>> getAlertas() {
        var lista = alertaRepository.findAll();
        List<AlertaConsultaDto> listaDto = lista.stream()
                .map(alerta -> modelMapper.map(alerta, AlertaConsultaDto.class))
                .collect(Collectors.toList());
        return lista.isEmpty() ? status(204).build() : status(200).body(listaDto);
    }

    @Operation(summary = "Busca um alerta pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerta encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AlertaConsultaDto> listarAlertaPorId(
            @Parameter(description = "ID do alerta para busca") @PathVariable Long id) {
        var alertaOpt = alertaRepository.findById(id);
        return alertaOpt.map(alerta -> status(200).body(modelMapper.map(alerta, AlertaConsultaDto.class)))
                .orElseGet(() -> status(404).build());
    }

    @Operation(summary = "Atualiza os dados de um alerta pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerta atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<AlertaConsultaDto> alterarAlerta(
            @Parameter(description = "ID do alerta para atualização") @PathVariable Long id,
            @Parameter(description = "Objeto do alerta com dados atualizados") @Valid @RequestBody AlertaCriacaoDto alertaAtualizadoDto) {
        if (alertaRepository.existsById(id)) {
            Alerta alertaAtualizado = modelMapper.map(alertaAtualizadoDto, Alerta.class);
            alertaAtualizado.setId(id);
            alertaRepository.save(alertaAtualizado);
            AlertaConsultaDto alertaConsultaDto = modelMapper.map(alertaAtualizado, AlertaConsultaDto.class);
            return status(200).body(alertaConsultaDto);
        }
        return status(404).build();
    }

    @Operation(summary = "Deleta um alerta pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alerta excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do alerta para exclusão") @PathVariable Long id) {
        if (alertaRepository.existsById(id)) {
            alertaRepository.deleteById(id);
            return status(204).build();
        }
        return status(404).build();
    }
}
