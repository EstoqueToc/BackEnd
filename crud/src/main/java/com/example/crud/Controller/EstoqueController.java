package com.example.crud.Controller;

import com.example.crud.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

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
    public ResponseEntity<String> verificarAlertas() {
        return estoqueService.verificarAlertas() ? ResponseEntity.ok("Alerta de estoque baixo!") : ResponseEntity.ok("Estoque normal");
    }
}
