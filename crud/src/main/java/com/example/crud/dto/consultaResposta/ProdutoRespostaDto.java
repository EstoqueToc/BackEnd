package com.example.crud.dto.consultaResposta;

import com.example.crud.Model.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProdutoRespostaDto {

    private Long id;
    private String nomeProduto;
    private String descricaoProduto;
    private LocalDate dataValidade;
    private Double precoCompraProduto;
    private Double precoVendaProduto;
    private LocalDate dataEntrada;
    private String unidadeMedida;
    private Integer qtdEntrada;
    private String codigoBarras; // Adicionado
    private Empresa empresa;
    private List<AlertaRespostaDto> alerta;

    public ProdutoRespostaDto() {}

    public ProdutoRespostaDto(Long id, String nomeProduto, String descricaoProduto, LocalDate dataValidade, Double precoCompraProduto, Double precoVendaProduto, LocalDate dataEntrada, String unidadeMedida, Integer qtdEntrada, String codigoBarras, Empresa empresa, List<AlertaRespostaDto> alerta) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.dataValidade = dataValidade;
        this.precoCompraProduto = precoCompraProduto;
        this.precoVendaProduto = precoVendaProduto;
        this.dataEntrada = dataEntrada;
        this.unidadeMedida = unidadeMedida;
        this.qtdEntrada = qtdEntrada;
        this.codigoBarras = codigoBarras; // Adicionado
        this.empresa = empresa;
        this.alerta = alerta;
    }
}
