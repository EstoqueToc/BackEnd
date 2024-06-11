package com.example.crud.Controller;

import com.example.crud.Model.Estoque;
import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaDto.EstoqueInfo;
import com.example.crud.dto.consultaDto.FaturamentoMensal;
import com.example.crud.repository.EstoqueRepository;
import com.example.crud.repository.ProdutoRepository;
import com.example.crud.service.usuario.EstoqueService;
import com.example.crud.slack.Slack;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;
    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalProdutosEmEstoque() throws IOException, InterruptedException {
        ResponseEntity<Integer> response = estoqueService.getTotalProdutosEmEstoque();
        verificarEstoqueCritico();
        verificarEstoqueModerado();
        return response;
    }

    @GetMapping("/categoria")
    public ResponseEntity<Map<String, Integer>> getProdutosPorCategoria() throws IOException, InterruptedException {
        ResponseEntity<Map<String, Integer>> response = estoqueService.getProdutosPorCategoria();
        verificarEstoqueCritico();
        verificarEstoqueModerado();
        return response;
    }

    @GetMapping("/fornecedor")
    public ResponseEntity<Map<String, Integer>> getProdutosPorFornecedor() throws IOException, InterruptedException {
        ResponseEntity<Map<String, Integer>> response = estoqueService.getProdutosPorFornecedor();
        verificarEstoqueCritico();
        verificarEstoqueModerado();
        return response;
    }

    @GetMapping("/data-entrada")
    public ResponseEntity<Map<LocalDate, Integer>> getProdutosPorDataDeEntrada() throws IOException, InterruptedException {
        ResponseEntity<Map<LocalDate, Integer>> response = estoqueService.getProdutosPorDataDeEntrada();
        verificarEstoqueCritico();
        verificarEstoqueModerado();
        return response;
    }

    @GetMapping("/data-validade")
    public ResponseEntity<Map<LocalDate, Integer>> getProdutosPorDataDeValidade() throws IOException, InterruptedException {
        ResponseEntity<Map<LocalDate, Integer>> response = estoqueService.getProdutosPorDataDeValidade();
        verificarEstoqueCritico();
        verificarEstoqueModerado();
        return response;
    }

    private void verificarEstoqueCritico() throws IOException, InterruptedException {
        List<Produto> produtosCriticos = estoqueService.getProdutosCriticos();
        for (Produto produto : produtosCriticos) {
            sendSlackMessage("Alerta: Estoque crítico para o produto " + produto.getNomeProduto() + ". Apenas " + getTotalProdutosEmEstoque() + " unidades restantes.");
        }
    }

    private void verificarEstoqueModerado() throws IOException, InterruptedException {
        List<Produto> produtosModerados = estoqueService.getProdutosModerados();
        for (Produto produto : produtosModerados) {
            sendSlackMessage("Aviso: Estoque moderado para o produto " + produto.getNomeProduto() + ". Apenas " + getTotalProdutosEmEstoque() + " unidades restantes.");
        }
    }

    private void sendSlackMessage(String message) throws IOException, InterruptedException {
        JSONObject json = new JSONObject();
        json.put("text", message);
        Slack.sendMessage(json.toString()); // Convertendo JSONObject para String
    }


//    private void sendSlackMessage(String message) throws IOException, InterruptedException {
//        JSONObject json = new JSONObject();
//        json.put("text", message);
//        Slack.sendMessage(json);
//    }
//
//    @GetMapping("/slack/send")
//    public ResponseEntity<String> verificarAlertas() {
//        return estoqueService.verificarAlertas() ? ResponseEntity.ok("Alerta de estoque baixo!") : ResponseEntity.ok("Estoque normal");
//    }

    @GetMapping("/alto")
    public ResponseEntity<List<EstoqueInfo>> getProdutosComEstoqueAlto(@RequestParam Long empresaId) {
        List<EstoqueInfo> produtos = estoqueService.getProdutosComEstoqueAlto(empresaId);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/medio")
    public ResponseEntity<List<EstoqueInfo>> getProdutosComEstoqueMedio(@RequestParam Long empresaId) {
        List<EstoqueInfo> produtos = estoqueService.getProdutosComEstoqueMedio(empresaId);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/baixo")
    public ResponseEntity<List<EstoqueInfo>> getProdutosComEstoqueBaixo(@RequestParam Long empresaId) {
        List<EstoqueInfo> produtos = estoqueService.getProdutosComEstoqueBaixo(empresaId);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/informacoes")
    public ResponseEntity<List<EstoqueInfo>> getInformacoesEstoque(@RequestParam Long empresaId) throws IOException, InterruptedException {
        List<EstoqueInfo> estoqueInfos = estoqueService.getInformacoesEstoque(empresaId);
//        verificarEstoqueCritico();
//        verificarEstoqueModerado();

        return ResponseEntity.ok(estoqueInfos);
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<Map<String, Object>>> getEstoqueProdutos(@RequestParam Long empresaId) {
        return ResponseEntity.ok(estoqueService.getEstoqueProdutos(empresaId));
    }

    @GetMapping("/vendas/total")
    public ResponseEntity<Double> getValorTotalVendas(@RequestParam Long empresaId) {
        return ResponseEntity.ok(estoqueService.getValorTotalVendas(empresaId));
    }

    @GetMapping("/estoque/valor")
    public ResponseEntity<Double> getValorTotalEstoque(@RequestParam Long empresaId) {
        return ResponseEntity.ok(estoqueService.getValorTotalEstoque(empresaId));
    }

    @GetMapping("/estoque/disponivel")
    public ResponseEntity<Double> getValorTotalEstoqueDisponivel(@RequestParam Long empresaId) {
        return ResponseEntity.ok(estoqueService.getValorTotalEstoqueDisponivel(empresaId));
    }

    @GetMapping("/perdas/total")
    public ResponseEntity<Double> getTotalPerdas(@RequestParam Long empresaId) {
        return ResponseEntity.ok(estoqueService.getTotalPerdas(empresaId));
    }

    @GetMapping("/produtos/total")
    public ResponseEntity<Integer> getTotalProdutos(@RequestParam Long empresaId) {
        return ResponseEntity.ok(estoqueService.getTotalProdutos(empresaId));
    }

    @GetMapping("/validade")
    public ResponseEntity<List<Map<String, Object>>> getProdutosPertoDeVencer(@RequestParam Long empresaId) {
        return ResponseEntity.ok(estoqueService.getProdutosPertoDeVencer(empresaId));
    }

    @GetMapping("/vendas/mensais")
    public ResponseEntity<List<Map<String, Object>>> getVendasMensais(@RequestParam Long empresaId) {
        return ResponseEntity.ok(estoqueService.getVendasMensais(empresaId));
    }

    @GetMapping("/faturamento/anual")
    public ResponseEntity<List<Map<String, Object>>> getFaturamentoAnual(@RequestParam Long empresaId) {
        return ResponseEntity.ok(estoqueService.getFaturamentoAnual(empresaId));
    }

    @GetMapping("/faturamento/atual")
    public ResponseEntity<Double> getFaturamentoAtual(@RequestParam Long empresaId) {
        return ResponseEntity.ok(estoqueService.getFaturamentoAtual(empresaId));
    }

    @GetMapping("/faturamento/anterior")
    public ResponseEntity<Double> getFaturamentoAnterior(@RequestParam Long empresaId) {
        return ResponseEntity.ok(estoqueService.getFaturamentoAnterior(empresaId));
    }

    @GetMapping("/perdidos")
    public ResponseEntity<Integer> getTotalProdutosPerdidos(@RequestParam Long empresaId) {
        return ResponseEntity.ok(estoqueService.getTotalProdutosPerdidos(empresaId));
    }

    @GetMapping("/faturamento/mensal")
    public ResponseEntity<List<FaturamentoMensal>> getFaturamentoMensalPorEmpresa(@RequestParam Long empresaId) {
        List<FaturamentoMensal> faturamentoMensal = estoqueService.getFaturamentoMensalPorEmpresa(empresaId);
        return ResponseEntity.ok(faturamentoMensal);
    }

    @GetMapping("/entrada-mes-atual")
    public ResponseEntity<Integer> getEntradaEstoqueMesAtual(@RequestParam Long empresaId) {
        Integer entradaMesAtual = estoqueService.getEntradaEstoqueMesAtual(empresaId);
        return ResponseEntity.ok(entradaMesAtual);
    }

    @GetMapping("/saida-mes-atual")
    public ResponseEntity<Integer> getSaidaEstoqueMesAtual(@RequestParam Long empresaId) {
        Integer saidaMesAtual = estoqueService.getSaidaEstoqueMesAtual(empresaId);
        return ResponseEntity.ok(saidaMesAtual);
    }

    @GetMapping("/entrada-mes-passado")
    public ResponseEntity<Integer> getEntradaEstoqueMesPassado(@RequestParam Long empresaId) {
        Integer entradaMesPassado = estoqueService.getEntradaEstoqueMesPassado(empresaId);
        return ResponseEntity.ok(entradaMesPassado);
    }

    @GetMapping("/saida-mes-passado")
    public ResponseEntity<Integer> getSaidaEstoqueMesPassado(@RequestParam Long empresaId) {
        Integer saidaMesPassado = estoqueService.getSaidaEstoqueMesPassado(empresaId);
        return ResponseEntity.ok(saidaMesPassado);
    }

    @PostMapping("/adicionar/{produtoId}")
    public ResponseEntity<Void> adicionarQuantidade(@PathVariable Long produtoId, @RequestParam Integer quantidadeAlterada) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId);
        Produto produto = produtoRepository.findById(produtoId).get();

        if (estoque == null) {
            return ResponseEntity.notFound().build();
        }

        estoque.setQtdDisponivel(quantidadeAlterada);

        produto.setQtdEntrada(quantidadeAlterada);
        produto.setDataEntrada(LocalDate.now());
        produto.setId(produtoId);
        produtoRepository.save(produto);
        estoqueRepository.save(estoque);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/remover/{produtoId}")
    public ResponseEntity<Void> removerQuantidade(@PathVariable Long produtoId, @RequestParam Integer quantidadeAlterada) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId);

        if (estoque == null) {
            return ResponseEntity.notFound().build();
        }

        int novaQuantidade = estoque.getQtdDisponivel() - quantidadeAlterada;

        if (novaQuantidade < 0) {
            return ResponseEntity.badRequest().build();
        }

        estoque.setDataSaida(LocalDate.now());
        estoque.setQtdSaida(novaQuantidade);
        estoque.setQtdDisponivel(quantidadeAlterada);

        estoqueRepository.save(estoque);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/produtos/simples/{nome}/{empresaId}")
    public ResponseEntity<List<EstoqueInfo>> getProdutosSimples(@PathVariable String nome, @PathVariable Long empresaId) {
        return ResponseEntity.ok(estoqueService.getProdutosSimples(nome, empresaId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Estoque> estoque = estoqueRepository.findById(id);
        if (estoque.isEmpty()) {
            return notFound().build();
        }
        estoqueRepository.delete(estoque.get());
        return ResponseEntity.ok().build();
    }
}
