package com.example.crud.repository;

import com.example.crud.Model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    List<Fornecedor> findAllByOrderByNomeFantasiaAsc();
    List<Fornecedor> findByNomeFantasiaContainsIgnoreCase(String nome);
    List<Fornecedor> findById(long id);

    @Query("SELECT f FROM Fornecedor f " +
            "JOIN EmpresaTemFornecedor etf ON f.id = etf.fornecedor.id " +
            "WHERE etf.empresa.id = ?1")
    List<Fornecedor> findFornecedorByEmpresaId(long id);

}
