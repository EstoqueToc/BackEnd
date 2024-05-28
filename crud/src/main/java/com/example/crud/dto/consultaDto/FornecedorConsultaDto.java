package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Fornecedor;
import lombok.Getter;
<<<<<<< HEAD
import lombok.Setter;
=======
>>>>>>> f11054b516eed028f8e5aee53dc094d77f2529e1

@Getter
@Setter
public class FornecedorConsultaDto {


    private Long id;
    private String nome;
    private String nomeFantasia;
    private String razaoSocial;
    private String telefone;
    private String email;
    private String cnpj;
    private Double preco;
    private Boolean parceria;

    public FornecedorConsultaDto() {
    }

    public FornecedorConsultaDto(Fornecedor fornecedor) {
        this.id = fornecedor.getId();
        this.nome = fornecedor.getNome();
        this.nomeFantasia = fornecedor.getNomeFantasia();
        this.razaoSocial = fornecedor.getRazaoSocial();
        this.telefone = fornecedor.getTelefone();
        this.email = fornecedor.getEmail();
        this.cnpj = fornecedor.getCnpj();
        this.preco = fornecedor.getPreco();
        this.parceria = fornecedor.getParceria();
    }
}
