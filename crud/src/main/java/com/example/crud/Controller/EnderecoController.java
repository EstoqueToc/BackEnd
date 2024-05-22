package com.example.crud.Controller;

import com.example.crud.Helpers.QuickSortEndereco;
import com.example.crud.dto.EnderecoApiExternaDto;
import com.example.crud.dto.EnderecoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private static final Logger log = LoggerFactory.getLogger(EnderecoController.class);

    private QuickSortEndereco quickSortEndereco = new QuickSortEndereco();

    @GetMapping("/estado-cidade/{estado}/{cidade}/{rua}/json/")
    @Operation(summary = "Buscar dados do endereço", description = """
    # Busca os dados de um endereço a partir do CEP utilizando uma API externa
    ---
    Retorna os dados de endereço retornados da API.
    """)
    @ApiResponse(responseCode = "200", description = "Dados de endereço")
    public ResponseEntity<List<EnderecoDto>> buscarEnderecoPorEstadoECidade(@PathVariable String estado, @PathVariable String cidade, @PathVariable String rua) {
        WebClient client = WebClient.create("https://viacep.com.br/ws/");

        List<EnderecoApiExternaDto> enderecos = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("{estado}/{cidade}/{rua}/json/")
                        .build(estado, cidade, rua))
                .retrieve()
                .bodyToFlux(EnderecoApiExternaDto.class)
                .collectList()
                .block();

        if (enderecos == null || enderecos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Convertendo para lista de EnderecoDto e ordenando
        List<EnderecoDto> resposta = enderecos.stream()
                .map(endereco -> {
                    EnderecoDto enderecoDto = new EnderecoDto();
                    enderecoDto.setBairro(endereco.getBairro());
                    enderecoDto.setCep(endereco.getCep());
                    enderecoDto.setCidade(endereco.getCidade());
                    enderecoDto.setEstado(endereco.getEstado());
                    enderecoDto.setRua(endereco.getRua());
                    return enderecoDto;
                }).collect(Collectors.toList());

                quickSortEndereco.quickSort(resposta);

        return status(200).body(resposta);
    }
}