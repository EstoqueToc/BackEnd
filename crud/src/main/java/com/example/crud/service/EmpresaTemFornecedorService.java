package com.example.crud.service;

import com.example.crud.Model.EmpresaTemFornecedor;
import com.example.crud.repository.EmpresaTemFornecedorRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpresaTemFornecedorService {

    private final EmpresaTemFornecedorRepository repository;

    public EmpresaTemFornecedor save(EmpresaTemFornecedor empresaTemFornecedor) {
        return repository.save(empresaTemFornecedor);
    }
}
