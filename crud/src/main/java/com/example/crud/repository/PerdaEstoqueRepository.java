package com.example.crud.repository;

import com.example.crud.Model.PerdaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerdaEstoqueRepository extends JpaRepository<PerdaEstoque, Long> {

}
