package com.example.crud.repository;

import com.example.crud.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoriaNomeIgnoreCase(String nome);

    List<Produto> findByPrecoDeVendaBetween(Double precoMinimo, Double precoMaximo);

    List<Produto> findByQtdEstoqueGreaterThanEqual(int qtdEstoque);

    List<Produto> findAllByOrderByNomeAsc();

    List<Produto> findAllByOrderByPrecoDeVendaAsc();

    List<Produto> findAllByOrderByDataDeValidadeAsc();

    List<Produto> findAllByOrderByDataDeEntradaAsc();

    List<Produto> findAllByOrderByQtdEstoqueAsc();

    List<Produto> findByNomeContainsIgnoreCase(String nome);
}
