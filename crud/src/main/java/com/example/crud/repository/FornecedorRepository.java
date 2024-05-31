package com.example.crud.repository;

import com.example.crud.Model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    List<Fornecedor> findAllByOrderByNomeFantasiaAsc();
    List<Fornecedor> findByNomeFantasiaContainsIgnoreCase(String nome);
    List<Fornecedor> findById(long id);

}
