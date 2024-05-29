package com.example.crud.dto.criacaoDto;

import com.example.crud.Model.Logradouro;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;


@Getter
@Setter
public class EmpresaCriacaoDto {


    @NotBlank(message = "Nome da empresa é obrigatório")
    @Size(max = 50, message = "Nome da empresa deve ter no máximo 50 caracteres")
    private String nomeEmpresa;

    @Size(max = 50, message = "Razão social deve ter no máximo 50 caracteres")
    private String razaoSocial;

    @CNPJ(message = "CNPJ inválido")
    @NotBlank(message = "CNPJ é obrigatório")
    private String CNPJ; /* 52.254.752/0001-82 */

    //    @Pattern(regexp = "\\(\\d{2}\\)\\d{4,5}-\\d{4}", message = "Telefone deve estar no formato (99) 99999-9999 ou (99) 9999-9999")
    private String telefone; /* (99) 99999-9999 | (99) 9999-9999 */

    @Email(message = "Email corporativo inválido")
//    @NotBlank(message = "Email corporativo é obrigatório")
    private String emailCorporativo;

    private boolean ativo;
}

