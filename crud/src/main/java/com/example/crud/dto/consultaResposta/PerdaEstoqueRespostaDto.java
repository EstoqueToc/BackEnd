package com.example.crud.dto.consultaResposta;

import com.example.crud.dto.consultaDto.EmpresaDetalhesConsulta;
import com.example.crud.dto.consultaDto.ProdutoConsultaDto;

import java.time.LocalDate;

public class PerdaEstoqueRespostaDto {
    private Integer qtdPerdida;
    private LocalDate dataDaPerda;
    private EmpresaDetalhesConsulta empresa;
    private ProdutoConsultaDto produto;

    public PerdaEstoqueRespostaDto() {
    }

    public PerdaEstoqueRespostaDto(Integer qtdPerdida, LocalDate dataDaPerda, EmpresaDetalhesConsulta empresa, ProdutoConsultaDto produto) {
        this.qtdPerdida = qtdPerdida;
        this.dataDaPerda = dataDaPerda;
        this.empresa = empresa;
        this.produto = produto;
    }
}
