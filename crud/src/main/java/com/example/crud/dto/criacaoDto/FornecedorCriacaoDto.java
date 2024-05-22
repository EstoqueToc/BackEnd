package com.example.crud.dto.criacaoDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@Setter
@Setter
public class FornecedorCriacaoDto {

    @NotBlank(message = "Nome do fornecedor é obrigatório")
    private String nome;

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

    @NotNull(message = "Preço é obrigatório")
    private Double preco;

    @NotNull(message = "Parceria é obrigatória")
    private Boolean parceria;

    // Métodos set e get gerados automaticamente pelo Lombok
}