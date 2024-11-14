package com.example.crud.dto.consultaResposta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCodigoRespostaDto {
    private String nomeProduto;
    private String descricaoProduto;
    private String unidadeMedida;
    private LocalDate dataValidade;
    private Integer qtdEntrada;
}
