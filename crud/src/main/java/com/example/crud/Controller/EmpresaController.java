package com.example.crud.Controller;

import com.example.crud.Model.Empresa;
import com.example.crud.Model.Logradouro;
import com.example.crud.dto.consultaDto.EmpresaConsultaDto;
import com.example.crud.dto.criacaoDto.EmpresaCriacaoDto;
import com.example.crud.repository.EmpresaRepository;
import com.example.crud.repository.LogradouroRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    EmpresaRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    LogradouroRepository logradouroRepository;

    @Operation(summary = "Retorna todas as empresas")
    @ApiResponse(responseCode = "200", description = "Lista de empresas recuperada com sucesso")
    @ApiResponse(responseCode = "204", description = "Nenhuma empresa disponível", content = @Content)
    @GetMapping
    public ResponseEntity<List<EmpresaConsultaDto>> getEmpresa() {
        List<Empresa> lista = repository.findAll();
        List<EmpresaConsultaDto> listaDto = lista.stream()
                .map(empresa -> modelMapper.map(empresa, EmpresaConsultaDto.class))
                .collect(Collectors.toList());
        return lista.isEmpty() ? status(204).build() : status(200).body(listaDto);
    }

    @Operation(summary = "Retorna uma empresa pelo ID")
    @ApiResponse(responseCode = "200", description = "Empresa encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Empresa com o ID fornecido não encontrada", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaConsultaDto> getEmpresaById(@Parameter(description = "ID da empresa para busca") @PathVariable Long id) {
        return repository.findById(id)
                .map(empresa -> status(200).body(modelMapper.map(empresa, EmpresaConsultaDto.class)))
                .orElse(status(404).build());
    }

    @Operation(summary = "Cria uma nova empresa")
    @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso")
    @PostMapping("/cadastro")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Empresa> criarEmpresa(@Parameter(description = "Objeto da empresa a ser criado") @Valid @RequestBody Empresa novaEmpresaDto) {
        Empresa novaEmpresa = modelMapper.map(novaEmpresaDto, Empresa.class);
        Logradouro logradouro = novaEmpresa.getLogradouro();
        if (logradouro != null && logradouro.getId() == null) {
            logradouroRepository.save(logradouro);
        }
        repository.save(novaEmpresa);
        return status(201).body(novaEmpresa);
    }

    @Operation(summary = "Atualiza uma empresa pelo ID")
    @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Empresa com o ID fornecido não encontrada", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaConsultaDto> atualizarEmpresa(@Parameter(description = "ID da empresa para atualização") @PathVariable Long id, @Valid @RequestBody EmpresaCriacaoDto empresaAtualizadaDto) {
        return repository.findById(id)
                .map(empresa -> {
                    modelMapper.map(empresaAtualizadaDto, empresa);
                    empresa.setId(id);
                    repository.save(empresa);
                    return status(200).body(modelMapper.map(empresa, EmpresaConsultaDto.class));
                })
                .orElse(status(404).build());
    }

    @Operation(summary = "Deleta uma empresa pelo ID")
    @ApiResponse(responseCode = "204", description = "Empresa deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Empresa com o ID fornecido não encontrada", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@Parameter(description = "ID da empresa para exclusão") @PathVariable Long id) {
        return repository.findById(id)
                .map(empresa -> {
                    repository.deleteById(id);
                    return status(204).<Void>build();
                })
                .orElse(status(404).build());
    }
}
