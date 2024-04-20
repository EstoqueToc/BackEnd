package com.example.crud.Controller;

import com.example.crud.Model.Logradouro;
import com.example.crud.repository.LogradouroRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logradouros")
public class LogradouroController {

    @Autowired
    LogradouroRepository repository;

    @Operation(summary = "Retorna logradouros")
    @GetMapping
    public ResponseEntity<List<Logradouro>>  logradouros(){

    }
}
