package com.example.crud.repository;

import com.example.crud.Model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    List<Fornecedor> findAllByOrderByNomeAsc();
    List<Fornecedor> findByNomeContainsIgnoreCase(String nome);
    List<Fornecedor> findById(long id);

}
