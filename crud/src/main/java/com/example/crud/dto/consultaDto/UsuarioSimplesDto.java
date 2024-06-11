package com.example.crud.dto.consultaDto;

import lombok.Getter;

@Getter
public class UsuarioSimplesDto {

    private Long id;
    private String nome;
    private String email;
    private String funcao;
    private boolean ativo;
}
