package com.example.crud.dto.criacaoDto;

import com.example.crud.Model.Logradouro;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;


@Getter
@Setter
public class FornecedorCriacaoDto {

    @NotBlank(message = "Nome fantasia é obrigatório")
    private String nomeFantasia;

    @NotBlank(message = "Razão social é obrigatória")
    private String razaoSocial;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "CNPJ é obrigatório")
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;

    private int ativo;

    private Logradouro logradouro;
}