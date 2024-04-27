package com.example.crud.dto.criacaoDto;

import com.example.crud.Model.Logradouro;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
public class EmpresaCriacaoDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String razaoSocial;
    @CNPJ
    @Size(min = 18, max = 18)
    private String CNPJ; /* 52.254.752/0001-82 */
    @NotBlank
    @Size(min = 13, max = 16)
    private String telefone; /* (99) 99999-9999 | (99)99999-9999 | (99) 9 9999-9999 | 99 99999-9999 | 99 9 9999-9999*/
    @Email
    private String email;
    @ManyToOne
    private Logradouro logradouro;
    @NotNull
    private boolean ativo;

}
