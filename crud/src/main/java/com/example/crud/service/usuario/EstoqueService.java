package com.example.crud.service.usuario;

import com.example.crud.Model.Alerta;
import com.example.crud.Model.Estoque;
import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaDto.EstoqueInfo;
import com.example.crud.dto.consultaDto.FaturamentoMensal;
import com.example.crud.repository.AlertaRepository;
import com.example.crud.repository.EstoqueRepository;
import com.example.crud.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final ProdutoRepository produtoRepository;

    private final AlertaRepository alertaRepository;

    private final EstoqueRepository estoqueRepository;

    private final JdbcTemplate jdbcTemplate;

    private static final Logger logger = Logger.getLogger(EstoqueService.class.getName());

    private Integer getQtdDisponivel(Produto produto) {
        List<Estoque> estoques = estoqueRepository.findByProduto(produto);
        return estoques.stream().mapToInt(Estoque::getQtdDisponivel).sum();
    }

    public ResponseEntity<Integer> getTotalProdutosEmEstoque() {
        List<Produto> produtos = produtoRepository.findAll();
        int total = produtos.stream().mapToInt(this::getQtdDisponivel).sum();
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Integer>> getProdutosPorCategoria() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<String, Integer> produtosPorCategoria = produtos.stream().collect(Collectors.groupingBy(
                produto -> produto.getCategoria().getNome(),
                Collectors.summingInt(this::getQtdDisponivel)
        ));
        return new ResponseEntity<>(produtosPorCategoria, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Integer>> getProdutosPorFornecedor() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<String, Integer> produtosPorFornecedor = produtos.stream().collect(Collectors.groupingBy(
                produto -> produto.getFornecedor().getNomeFantasia(),
                Collectors.summingInt(this::getQtdDisponivel)
        ));
        return new ResponseEntity<>(produtosPorFornecedor, HttpStatus.OK);
    }

    public ResponseEntity<Map<LocalDate, Integer>> getProdutosPorDataDeEntrada() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<LocalDate, Integer> produtosPorDataDeEntrada = produtos.stream().collect(Collectors.groupingBy(
                Produto::getDataEntrada,
                Collectors.summingInt(this::getQtdDisponivel)
        ));
        return new ResponseEntity<>(produtosPorDataDeEntrada, HttpStatus.OK);
    }

    public ResponseEntity<Map<LocalDate, Integer>> getProdutosPorDataDeValidade() {
        List<Produto> produtos = produtoRepository.findAll();
        Map<LocalDate, Integer> produtosPorDataDeValidade = produtos.stream().collect(Collectors.groupingBy(
                Produto::getDataValidade,
                Collectors.summingInt(this::getQtdDisponivel)
        ));
        return new ResponseEntity<>(produtosPorDataDeValidade, HttpStatus.OK);
    }

//    public List<Produto> getProdutosCriticos() {
//        List<Produto> produtosCriticos = produtoRepository.findAll().stream()
//                .filter(produto -> produto.getQtdEstoque() <= 2)
//                .collect(Collectors.toList());
//        logger.info("Produtos críticos encontrados: " + produtosCriticos.size());
//        for (Produto produto : produtosCriticos) {
//            logger.info("Produto crítico: " + produto.getNome() + " - Quantidade: " + produto.getQtdEstoque());
//        }
//        return produtosCriticos;
//    }


    public List<Produto> getProdutosModerados() {
        Alerta alerta = alertaRepository.findFirstByOrderByIdAsc();
        int alertaModerado = alerta.getAlertaModerado();

        List<Produto> produtosModerados = produtoRepository.findAll().stream()
                .filter(produto -> getQtdDisponivel(produto) <= alertaModerado)
                .collect(Collectors.toList());
        logger.info("Produtos moderados encontrados: " + produtosModerados.size());
        for (Produto produto : produtosModerados) {
            logger.info("Produto moderado: " + produto.getNomeProduto() + " - Quantidade: " + getQtdDisponivel(produto));
        }
        return produtosModerados;
    }

    public List<Produto> getProdutosCriticos() {
        Alerta alerta = alertaRepository.findFirstByOrderByIdAsc();
        int alertaGrave = alerta.getAlertaGrave();

        List<Produto> produtosCriticos = produtoRepository.findAll().stream()
                .filter(produto -> getQtdDisponivel(produto) <= alertaGrave)
                .collect(Collectors.toList());
        logger.info("Produtos críticos encontrados: " + produtosCriticos.size());
        for (Produto produto : produtosCriticos) {
            logger.info("Produto crítico: " + produto.getNomeProduto() + " - Quantidade: " + getQtdDisponivel(produto));
        }
        return produtosCriticos;
    }

    public List<EstoqueInfo> getProdutosComEstoqueAlto(Long empresaId) {
        int limiteEstoqueAlto = 100;
        return estoqueRepository.findProdutosComEstoqueAlto(limiteEstoqueAlto, empresaId);
    }

    public List<EstoqueInfo> getProdutosComEstoqueMedio(Long empresaId) {
        int limiteEstoqueBaixo = 20;
        int limiteEstoqueAlto = 100;
        return estoqueRepository.findProdutosComEstoqueMedio(limiteEstoqueBaixo, limiteEstoqueAlto, empresaId);
    }

    public List<EstoqueInfo> getProdutosComEstoqueBaixo(Long empresaId) {
        int limiteEstoqueBaixo = 20;
        return estoqueRepository.findProdutosComEstoqueBaixo(limiteEstoqueBaixo, empresaId);
    }

    public List<EstoqueInfo> getInformacoesEstoque(Long empresaId) {
        List<Object[]> resultados = estoqueRepository.findInformacoesEstoque(empresaId);

        List<EstoqueInfo> estoqueInfos = new ArrayList<>();
        for (Object[] resultado : resultados) {
            Long id = (Long) resultado[0];
            String produto = (String) resultado[1];
            int quantidade = (Integer) resultado[2];
            double precoUnitario = (Double) resultado[3];
            String statusEstoque = calcularStatusEstoque(quantidade);

            EstoqueInfo estoqueInfo = new EstoqueInfo(id, produto, quantidade, precoUnitario, statusEstoque);
            estoqueInfos.add(estoqueInfo);
        }

        return estoqueInfos;
    }


    private String calcularStatusEstoque(int quantidade) {
        if (quantidade >= 100) {
            return "Alto";
        } else if (quantidade >= 20) {
            return "Médio";
        } else {
            return "Baixo";
        }
    }

    public List<Map<String, Object>> getEstoqueProdutos(Long empresaId) {
        String sql = "SELECT p.nome_produto, COALESCE(SUM(e.qtd_saida), 0) AS total_saida, " +
                "COALESCE(SUM(p.qtd_entrada), 0) AS total_entrada, " +
                "(COALESCE(SUM(p.qtd_entrada), 0) - COALESCE(SUM(e.qtd_saida), 0)) AS total_em_estoque " +
                "FROM produtos p " +
                "LEFT JOIN estoque e ON p.id = e.produtos_id " +
                "WHERE e.empresa_id = ? " +
                "GROUP BY p.nome_produto";
        return jdbcTemplate.queryForList(sql, empresaId);
    }

    public Double getValorTotalVendas(Long empresaId) {
        String sql = "SELECT SUM(p.preco_venda_produto * e.qtd_saida) AS valor_total_vendas " +
                "FROM produtos p JOIN estoque e ON p.id = e.produtos_id " +
                "WHERE e.empresa_id = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, empresaId);
    }

    public Double getValorTotalEstoque(Long empresaId) {
        String sql = "SELECT SUM(p.preco_compra_produto * p.qtd_entrada) AS valor_total_estoque " +
                "FROM produtos p " +
                "WHERE p.id = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, empresaId);
    }

    public Double getValorTotalEstoqueDisponivel(Long empresaId) {
        String sql = "SELECT SUM(p.preco_venda_produto * e.qtd_disponivel) AS total_value " +
                "FROM produtos p " +
                "JOIN estoque e ON p.id = e.produtos_id " +
                "WHERE e.empresa_id = ? " +
                "AND p.id NOT IN (SELECT produto_id FROM perdaEstoque)";
        return jdbcTemplate.queryForObject(sql, Double.class, empresaId);
    }

    public Double getTotalPerdas(Long empresaId) {
        String sql = "SELECT SUM(pe.qtd_perdida * pr.preco_compra_produto) AS total_perdas " +
                "FROM perdaEstoque pe JOIN produtos pr ON pe.produtos_id = pr.id " +
                "WHERE pe.empresa_id = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, empresaId);
    }

    public Integer getTotalProdutos(Long empresaId) {
        String sql = "SELECT SUM(qtd_entrada) AS total_products " +
                "FROM produtos " +
                "WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, empresaId);
    }

    public List<Map<String, Object>> getProdutosPertoDeVencer(Long empresaId) {
        String sql = "SELECT p.nome_produto, p.data_validade, DATEDIFF(p.data_validade, CURRENT_DATE) AS dias_para_vencer " +
                "FROM produtos p JOIN estoque e ON p.id = e.produtos_id " +
                "WHERE e.empresa_id = ? " +
                "AND DATEDIFF(p.data_validade, CURRENT_DATE) <= 30";
        return jdbcTemplate.queryForList(sql, empresaId);
    }

    public List<Map<String, Object>> getVendasMensais(Long empresaId) {
        String sql = "SELECT DATE_FORMAT(e.data_saida, '%Y-%m') AS mes_ano, SUM(p.preco_venda_produto * e.qtd_saida) AS valor_venda_mensal " +
                "FROM estoque e JOIN produtos p ON e.produtos_id = p.id " +
                "WHERE e.empresa_id = ? " +
                "GROUP BY mes_ano ORDER BY mes_ano DESC";
        return jdbcTemplate.queryForList(sql, empresaId);
    }

    public List<Map<String, Object>> getFaturamentoAnual(Long empresaId) {
        String sql = "SELECT DATE_FORMAT(e.data_saida, '%Y') AS ano, SUM(p.preco_venda_produto * e.qtd_saida) AS faturamento_anual " +
                "FROM estoque e JOIN produtos p ON e.produtos_id = p.id " +
                "WHERE e.empresa_id = ? " +
                "GROUP BY ano ORDER BY ano DESC";
        return jdbcTemplate.queryForList(sql, empresaId);
    }

    public Double getFaturamentoAtual(Long empresaId) {
        String sql = "SELECT SUM(p.preco_venda_produto * e.qtd_saida) " +
                "FROM produtos p JOIN estoque e ON p.id = e.produtos_id " +
                "WHERE e.empresa_id = ? " +
                "AND MONTH(e.data_saida) = MONTH(CURRENT_DATE()) " +
                "AND YEAR(e.data_saida) = YEAR(CURRENT_DATE())";
        return jdbcTemplate.queryForObject(sql, Double.class, empresaId);
    }

    public Double getFaturamentoAnterior(Long empresaId) {
        String sql = "SELECT SUM(p.preco_venda_produto * e.qtd_saida) " +
                "FROM produtos p JOIN estoque e ON p.id = e.produtos_id " +
                "WHERE e.empresa_id = ? " +
                "AND MONTH(e.data_saida) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH) " +
                "AND YEAR(e.data_saida) = YEAR(CURRENT_DATE() - INTERVAL 1 MONTH)";
        return jdbcTemplate.queryForObject(sql, Double.class, empresaId);
    }

    public Integer getTotalProdutosPerdidos(Long empresaId) {
        String sql = "SELECT SUM(pe.qtd_perdida) " +
                "FROM perdaEstoque pe JOIN produtos pr ON pe.produtos_id = pr.id " +
                "WHERE pr.id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, empresaId);
    }

    public List<FaturamentoMensal> getFaturamentoMensalPorEmpresa(Long empresaId) {
        return estoqueRepository.calcularFaturamentoMensalPorEmpresa(empresaId);
    }

    public Integer getEntradaEstoqueMesAtual(Long empresaId) {
        LocalDate primeiroDiaMesAtual = LocalDate.now().withDayOfMonth(1);
        LocalDate ultimoDiaMesAtual = LocalDate.now().withDayOfMonth(primeiroDiaMesAtual.lengthOfMonth());
        return estoqueRepository.getEntradaEstoqueMes(primeiroDiaMesAtual, ultimoDiaMesAtual, empresaId);
    }

    public Integer getSaidaEstoqueMesAtual(Long empresaId) {
        LocalDate primeiroDiaMesAtual = LocalDate.now().withDayOfMonth(1);
        LocalDate ultimoDiaMesAtual = LocalDate.now().withDayOfMonth(primeiroDiaMesAtual.lengthOfMonth());
        return estoqueRepository.getSaidaEstoqueMes(primeiroDiaMesAtual, ultimoDiaMesAtual, empresaId);
    }

    public Integer getEntradaEstoqueMesPassado(Long empresaId) {
        LocalDate primeiroDiaMesPassado = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate ultimoDiaMesPassado = LocalDate.now().minusMonths(1).withDayOfMonth(primeiroDiaMesPassado.lengthOfMonth());
        return estoqueRepository.getEntradaEstoqueMes(primeiroDiaMesPassado, ultimoDiaMesPassado, empresaId);
    }

    public Integer getSaidaEstoqueMesPassado(Long empresaId) {
        LocalDate primeiroDiaMesPassado = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate ultimoDiaMesPassado = LocalDate.now().minusMonths(1).withDayOfMonth(primeiroDiaMesPassado.lengthOfMonth());
        return estoqueRepository.getSaidaEstoqueMes(primeiroDiaMesPassado, ultimoDiaMesPassado, empresaId);
    }

    public List<EstoqueInfo> getProdutosSimples(String nome, Long empresaId){
        var pesquisa = estoqueRepository.findInformacoesSimples(nome, empresaId);
        return pesquisa;
    }
}
