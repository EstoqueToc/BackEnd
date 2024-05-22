package com.example.crud.service;

import com.example.crud.Model.Logradouro;
import com.example.crud.repository.LogradouroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogradouroService {

    @Autowired
    private LogradouroRepository repository;

    public ResponseEntity<List<Logradouro>> getLogradouros() {
        List<Logradouro> lista = repository.findAll();
        return ResponseEntity.ok(lista);
    }

    public ResponseEntity<Logradouro> getLogradouroById(Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Logradouro> getLogradouroByCep(String cep) {
        return repository.findByCep(cep)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<Logradouro>> getLogradouroByCidade(String cidade) {
        List<Logradouro> lista = repository.findByCidade(cidade);
        return ResponseEntity.ok(lista);
    }

    public ResponseEntity<List<Logradouro>> getLogradouroByEstado(String estado) {
        List<Logradouro> lista = repository.findByEstado(estado);
        return ResponseEntity.ok(lista);
    }

    public ResponseEntity<List<Logradouro>> getLogradouroByRua(String rua) {
        List<Logradouro> lista = repository.findByRua(rua);
        return ResponseEntity.ok(lista);
    }

    public ResponseEntity<Logradouro> atualizarLogradouro(Long id, Logradouro logradouroDetails) {
        return repository.findById(id)
                .map(logradouro -> {
                    logradouro.setId(id);
                    repository.save(logradouro);
                    return ResponseEntity.ok(logradouro);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deletarLogradouro(Long id) {
        return repository.findById(id)
                .map(logradouro -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deletarLogradouroByCep(String cep) {
        return repository.findByCep(cep)
                .map(logradouro -> {
                    repository.deleteByCep(cep);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}