package com.example.crud.repository;

import com.example.crud.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

    List<Categoria> findAllByOrderByNomeAsc();
    List<Categoria> findByNomeContainsIgnoreCase(String nome);

}
