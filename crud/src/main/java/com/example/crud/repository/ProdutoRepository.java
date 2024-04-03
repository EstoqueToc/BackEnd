package com.example.crud.repository;

import com.example.crud.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoriaNomeIgnoreCase(String nome);

    List<Produto> findByPrecoDeVendaBetween(Double precoMinimo, Double precoMaximo);

}
