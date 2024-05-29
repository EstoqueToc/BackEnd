package com.example.crud.Controller;

import com.example.crud.Model.Logradouro;
import com.example.crud.service.LogradouroService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logradouros")
@RequiredArgsConstructor
public class LogradouroController {

    private final LogradouroService logradouroService;

    @Operation(summary = "Retorna logradouros")
    @GetMapping
    public ResponseEntity<List<Logradouro>> logradouros() {
        return logradouroService.getLogradouros();
    }

    @Operation(summary = "Retorna logradouro pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Logradouro> logradouroById(@PathVariable Long id) {
        return logradouroService.getLogradouroById(id);
    }

    @Operation(summary = "Retorna logradouro pelo CEP")
    @GetMapping("/cep/{cep}")
    public ResponseEntity<Logradouro> logradouroByCep(@PathVariable String cep) {
        return logradouroService.getLogradouroByCep(cep);
    }

    @Operation(summary = "Retorna logradouro pela cidade")
    @GetMapping("/cidade/{cidade}")
    public ResponseEntity<List<Logradouro>> logradouroByCidade(@PathVariable String cidade) {
        return logradouroService.getLogradouroByCidade(cidade);
    }

    @Operation(summary = "Retorna logradouro pelo estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Logradouro>> logradouroByEstado(@PathVariable String estado) {
        return logradouroService.getLogradouroByEstado(estado);
    }

    @Operation(summary = "Retorna logradouro pela rua")
    @GetMapping("/rua/{rua}")
    public ResponseEntity<List<Logradouro>> logradouroByRua(@PathVariable String rua) {
        return logradouroService.getLogradouroByRua(rua);
    }

    @Operation(summary = "Atualiza logradouro pelo ID")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Logradouro> atualizarLogradouro(@PathVariable Long id, @RequestBody Logradouro logradouro) {
        return logradouroService.atualizarLogradouro(id, logradouro);
    }

    @Operation(summary = "Deleta logradouro pelo ID")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarLogradouro(@PathVariable Long id) {
        return logradouroService.deletarLogradouro(id);
    }

    @Operation(summary = "Deleta logradouro pelo CEP")
    @DeleteMapping("/deletar/cep/{cep}")
    public ResponseEntity<Void> deletarLogradouroByCep(@PathVariable String cep) {
        return logradouroService.deletarLogradouroByCep(cep);
    }
}
