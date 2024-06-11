package com.example.crud.dto.consultaDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaturamentoMensal {
    private Object mesAno;
    private Double valorVendaMensal;

    public FaturamentoMensal(Object mesAno, Double valorVendaMensal) {
        this.mesAno = mesAno;
        this.valorVendaMensal = valorVendaMensal;
    }
}
