package com.example.crud.repository;

import com.example.crud.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    List<Estoque> findByProduto(Produto produto);
}
