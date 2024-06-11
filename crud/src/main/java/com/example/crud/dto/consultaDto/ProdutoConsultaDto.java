package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Alerta;
import com.example.crud.Model.Categoria;
import com.example.crud.Model.Fornecedor;
import com.example.crud.Model.Produto;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoConsultaDto {

    private Long id;
    private String nomeProduto;
    private String descricaoProduto;
    private LocalDate dataValidade;
    private Double precoCompraProduto;
    private Double precoVendaProduto;
    private LocalDate dataEntrada;
    private String unidadeMedida;
    private Integer qtdEntrada;
    private Categoria categoria;
    private Fornecedor fornecedor;
    private List<Alerta> alerta;

    public ProdutoConsultaDto(Produto produto) {
        this.id = produto.getId();
        this.nomeProduto = produto.getNomeProduto();
        this.descricaoProduto = produto.getDescricaoProduto();
        this.dataValidade = produto.getDataValidade();
        this.precoCompraProduto = produto.getPrecoCompraProduto();
        this.precoVendaProduto = produto.getPrecoVendaProduto();
        this.dataEntrada = produto.getDataEntrada();
        this.unidadeMedida = produto.getUnidadeMedida();
        this.qtdEntrada = produto.getQtdEntrada();
        this.categoria = produto.getCategoria();
        this.fornecedor = produto.getFornecedor();
        this.alerta = produto.getAlerta().isEmpty() ? null : produto.getAlerta();
    }

}
