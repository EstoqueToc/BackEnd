package com.example.crud.Controller;

import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaDto.EstoqueInfo;
import com.example.crud.service.usuario.EstoqueService;
import com.example.crud.slack.Slack;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

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

//    @GetMapping("/slack/send")
//    public ResponseEntity<String> verificarAlertas() {
//        return estoqueService.verificarAlertas() ? ResponseEntity.ok("Alerta de estoque baixo!") : ResponseEntity.ok("Estoque normal");
//    }

    @GetMapping("/alto")
    public ResponseEntity<List<Produto>> getProdutosComEstoqueAlto() {
        List<Produto> produtos = estoqueService.getProdutosComEstoqueAlto();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/medio")
    public ResponseEntity<List<Produto>> getProdutosComEstoqueMedio() {
        List<Produto> produtos = estoqueService.getProdutosComEstoqueMedio();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/baixo")
    public ResponseEntity<List<Produto>> getProdutosComEstoqueBaixo() {
        List<Produto> produtos = estoqueService.getProdutosComEstoqueBaixo();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/informacoes")
    public ResponseEntity<List<EstoqueInfo>> getInformacoesEstoque() {
        List<EstoqueInfo> estoqueInfos = estoqueService.getInformacoesEstoque();
        return ResponseEntity.ok(estoqueInfos);
    }
}
