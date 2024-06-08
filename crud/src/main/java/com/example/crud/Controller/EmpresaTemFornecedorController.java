package com.example.crud.Controller;

import com.example.crud.Model.EmpresaTemFornecedor;
import com.example.crud.service.EmpresaTemFornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresaTemFornecedor")
@RequiredArgsConstructor
public class EmpresaTemFornecedorController {

    private final EmpresaTemFornecedorService service;

    @PostMapping
    public ResponseEntity<EmpresaTemFornecedor> create(@RequestBody EmpresaTemFornecedor empresaTemFornecedor) {
        EmpresaTemFornecedor savedEntity = service.save(empresaTemFornecedor);
        return ResponseEntity.ok(savedEntity);
    }
}
