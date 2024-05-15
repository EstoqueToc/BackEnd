package com.example.crud.Controller;

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
import java.util.Map;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalProdutosEmEstoque() {
        return estoqueService.getTotalProdutosEmEstoque();
    }

    @GetMapping("/categoria")
    public ResponseEntity<Map<String, Integer>> getProdutosPorCategoria() {
        return estoqueService.getProdutosPorCategoria();
    }

    @GetMapping("/fornecedor")
    public ResponseEntity<Map<String, Integer>> getProdutosPorFornecedor() {
        return estoqueService.getProdutosPorFornecedor();
    }

    @GetMapping("/data-entrada")
    public ResponseEntity<Map<LocalDate, Integer>> getProdutosPorDataDeEntrada() {
        return estoqueService.getProdutosPorDataDeEntrada();
    }

    @GetMapping("/data-validade")
    public ResponseEntity<Map<LocalDate, Integer>> getProdutosPorDataDeValidade() {
        return estoqueService.getProdutosPorDataDeValidade();
    }

    @GetMapping("/slack/send")
    public ResponseEntity<String> enviarMensagemSlack() {
        try {
            Slack.sendMessage("Mensagem de teste");
            return ResponseEntity.ok("Mensagem enviada com sucesso");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao enviar mensagem no Slack");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
