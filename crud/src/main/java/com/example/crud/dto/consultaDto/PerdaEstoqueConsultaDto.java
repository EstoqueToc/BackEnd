package com.example.crud.dto.consultaDto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PerdaEstoqueConsultaDto {

    private Integer qtdPerdida;
    private LocalDate dataDaPerda;
    private EmpresaDetalhesConsulta empresa;
    private ProdutoConsultaDto produto;

    public PerdaEstoqueConsultaDto() {
    }

    public PerdaEstoqueConsultaDto(Integer qtdPerdida, LocalDate dataDaPerda, EmpresaDetalhesConsulta empresa, ProdutoConsultaDto produto) {
        this.qtdPerdida = qtdPerdida;
        this.dataDaPerda = dataDaPerda;
        this.empresa = empresa;
        this.produto = produto;
    }

}
