package com.example.crud.Interface;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface IUpDate {
    //criar uma interface que de desconto no material e desconto no produto
    ResponseEntity<String> aplicarDesconto(int indice, double percentualDesconto);
}
