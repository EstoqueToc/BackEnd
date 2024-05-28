package com.example.crud.excecoes;

public class ErroInternoException extends RuntimeException {

    public ErroInternoException(String message) {
        super(message);
    }
}