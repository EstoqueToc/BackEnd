package com.example.crud.Controller;

import com.example.crud.Model.Produto;
import com.example.crud.service.usuario.EstoqueService;
import com.example.crud.slack.Slack;
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
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

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
            sendSlackMessage("Alerta: Estoque crítico para o produto " + produto.getNome() + ". Apenas " + produto.getQtdEstoque() + " unidades restantes.");
        }
    }

    private void verificarEstoqueModerado() throws IOException, InterruptedException {
        List<Produto> produtosModerados = estoqueService.getProdutosModerados();
        for (Produto produto : produtosModerados) {
            sendSlackMessage("Aviso: Estoque moderado para o produto " + produto.getNome() + ". Apenas " + produto.getQtdEstoque() + " unidades restantes.");
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
}
