package com.example.crud.Interface;

import java.time.LocalDate;

public interface IUpDate {

    Double attPrecoProduto();

    Double descontoPrecoProduto();

    void atualizarFuncionario(String nome, String CPF, String email, String senha, LocalDate idade, String cargo);

}
