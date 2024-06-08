package com.example.crud.repository;

import com.example.crud.Model.Estoque;
import com.example.crud.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    List<Estoque> findByProduto(Produto produto);

    @Query("SELECT e.produto FROM Estoque e WHERE e.qtdDisponivel > :limite")
    List<Produto> findProdutosComEstoqueAlto(int limite);

    @Query("SELECT e.produto FROM Estoque e WHERE e.qtdDisponivel BETWEEN :minLimite AND :maxLimite")
    List<Produto> findProdutosComEstoqueMedio(int minLimite, int maxLimite);

    @Query("SELECT e.produto FROM Estoque e WHERE e.qtdDisponivel < :limite")
    List<Produto> findProdutosComEstoqueBaixo(int limite);

    @Query("SELECT p.nomeProduto, e.qtdDisponivel, p.precoVendaProduto FROM Estoque e JOIN e.produto p")
    List<Object[]> findInformacoesEstoque();
}
