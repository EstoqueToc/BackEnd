package com.example.crud.dto.consultaDto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PerdaEstoqueConsultaDto {

    private Integer qtdPerdida;
    private LocalDate dataDaPerda;

    private EmpresaDetalhesConsulta empresa;
}
