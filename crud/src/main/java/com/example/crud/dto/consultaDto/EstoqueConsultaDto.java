package com.example.crud.dto.consultaDto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EstoqueConsultaDto {

    private Integer qtdSaida;
    private LocalDate dataSaida;
    private Integer qtdDisponivel;
    private EmpresaDetalhesConsulta empresa;
    private ProdutoConsultaDto produto;
}
