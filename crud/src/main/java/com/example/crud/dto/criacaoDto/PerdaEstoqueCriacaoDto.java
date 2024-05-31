package com.example.crud.dto.criacaoDto;

import com.example.crud.Model.Produto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PerdaEstoqueCriacaoDto {

    private Integer qtdPerdida;
    private LocalDate dataDaPerda;
    private Produto produto;
}
