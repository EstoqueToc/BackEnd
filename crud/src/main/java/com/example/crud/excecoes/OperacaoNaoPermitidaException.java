package com.example.crud.excecoes;

public class OperacaoNaoPermitidaException extends RuntimeException {

    public OperacaoNaoPermitidaException(String message) {
        super(message);
    }
}