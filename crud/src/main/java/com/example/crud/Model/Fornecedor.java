package com.example.crud.Model;

import jakarta.validation.constraints.*;

public class Fornecedor {

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotBlank
    private String telefone;

    @Email
    private String email;

    @NotBlank
    private String cnpj;

}
