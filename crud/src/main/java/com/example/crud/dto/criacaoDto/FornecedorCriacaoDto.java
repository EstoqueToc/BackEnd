package com.example.crud.dto.criacaoDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
public class FornecedorCriacaoDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String nomeFantasia;
    @NotBlank
    private String razaoSocial;
    @NotBlank
    private String telefone;
    @Email
    private String email;
    @NotBlank
    @CNPJ
    private String cnpj;
    @NotNull
    private Double preco;
    @NotNull
    private Boolean parceria;

}
