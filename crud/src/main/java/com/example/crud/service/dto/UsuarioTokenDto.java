package com.example.crud.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioTokenDto {

    private Long userId;
    private String nome;
    private String email;
    private String token;
}
