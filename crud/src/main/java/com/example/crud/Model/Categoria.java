package com.example.crud.Model;

import jakarta.validation.constraints.NotBlank;

public class Categoria {
    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;


}
