package com.example.crud.repository;

import com.example.crud.Model.Estoque;
import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaDto.EstoqueInfo;
import com.example.crud.dto.consultaDto.FaturamentoMensal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    List<Estoque> findByProduto(Produto produto);

    @Query("SELECT new com.example.crud.dto.consultaDto.EstoqueInfo(e.id, e.produto.nomeProduto, e.qtdDisponivel, e.produto.precoVendaProduto) FROM Estoque e WHERE e.qtdDisponivel > :limite AND e.empresa.id = :empresaId")
    List<EstoqueInfo> findProdutosComEstoqueAlto(@Param("limite") int limite, @Param("empresaId") Long empresaId);

    @Query("SELECT new com.example.crud.dto.consultaDto.EstoqueInfo(e.id, e.produto.nomeProduto, e.qtdDisponivel, e.produto.precoVendaProduto) FROM Estoque e WHERE e.qtdDisponivel BETWEEN :minLimite AND :maxLimite AND e.empresa.id = :empresaId")
    List<EstoqueInfo> findProdutosComEstoqueMedio(@Param("minLimite") int minLimite, @Param("maxLimite") int maxLimite, @Param("empresaId") Long empresaId);

    @Query("SELECT new com.example.crud.dto.consultaDto.EstoqueInfo(e.id, e.produto.nomeProduto, e.qtdDisponivel, e.produto.precoVendaProduto) FROM Estoque e WHERE e.qtdDisponivel < :limite AND e.empresa.id = :empresaId")
    List<EstoqueInfo> findProdutosComEstoqueBaixo(@Param("limite") int limite, @Param("empresaId") Long empresaId);

    @Query("SELECT p.id, p.nomeProduto, e.qtdDisponivel, p.precoVendaProduto FROM Estoque e JOIN e.produto p WHERE e.empresa.id = :empresaId")
    List<Object[]> findInformacoesEstoque(Long empresaId);

    @Query("SELECT NEW com.example.crud.dto.consultaDto.FaturamentoMensal(DATE_FORMAT(e.dataSaida, '%Y-%m'), SUM(p.precoVendaProduto * e.qtdSaida)) " +
            "FROM Estoque e " +
            "JOIN e.produto p " +
            "WHERE e.empresa.id = :empresaId " +
            "GROUP BY DATE_FORMAT(e.dataSaida, '%Y-%m') " +
            "ORDER BY DATE_FORMAT(e.dataSaida, '%Y-%m') DESC")
    List<FaturamentoMensal> calcularFaturamentoMensalPorEmpresa(@Param("empresaId") Long empresaId);

    @Query("SELECT COALESCE(SUM(p.qtdEntrada), 0) " +
            "FROM Produto p " +
            "JOIN Estoque e ON p.id = e.produto.id " +
            "WHERE p.dataEntrada BETWEEN :inicioMes AND :fimMes " +
            "AND e.empresa.id = :empresaId")
    Integer getEntradaEstoqueMes(@Param("inicioMes") LocalDate inicioMes, @Param("fimMes") LocalDate fimMes, @Param("empresaId") Long empresaId);

    @Query("SELECT COALESCE(SUM(e.qtdSaida), 0) " +
            "FROM Estoque e " +
            "WHERE e.dataSaida BETWEEN :inicioMes AND :fimMes " +
            "AND e.empresa.id = :empresaId")
    Integer getSaidaEstoqueMes(@Param("inicioMes") LocalDate inicioMes, @Param("fimMes") LocalDate fimMes, @Param("empresaId") Long empresaId);

    @Query("SELECT e FROM Estoque e WHERE e.produto.id = :produtoId")
    Estoque findByProdutoId(Long produtoId);

    @Query("SELECT NEW com.example.crud.dto.consultaDto.EstoqueInfo(e.id, p.nomeProduto, e.qtdDisponivel, p.precoVendaProduto) " +
            "FROM Estoque e " +
            "JOIN e.produto p " +
            "WHERE p.nomeProduto LIKE %:nome% " +
            "AND e.empresa.id = :empresaId")
    List<EstoqueInfo> findInformacoesSimples(String nome, Long empresaId);
}
