package com.example.crud.repository;

import com.example.crud.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoriaNomeIgnoreCase(String nome);

    List<Produto> findByPrecoVendaProdutoBetween(Double precoMinimo, Double precoMaximo);

    List<Produto> findByQtdEntradaGreaterThanEqual(int qtdEntrada);

    List<Produto> findAllByOrderByNomeProdutoAsc();

    List<Produto> findAllByOrderByPrecoVendaProdutoAsc();

    List<Produto> findAllByOrderByDataValidadeAsc();

    List<Produto> findAllByOrderByDataEntradaAsc();

    List<Produto> findAllByOrderByQtdEntradaAsc();

    List<Produto> findByNomeProdutoContainsIgnoreCase(String nome);

    Optional<Produto> findByCodigoBarras(String codigoBarras);
}
