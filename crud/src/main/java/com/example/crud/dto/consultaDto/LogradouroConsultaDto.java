package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Logradouro;
import lombok.Getter;

@Getter
public class LogradouroConsultaDto {

    protected Long id;
    private String rua;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;

    public LogradouroConsultaDto(Logradouro logradouro) {
        this.id = logradouro.getId();
        this.rua = logradouro.getRua();
        this.numero = logradouro.getNumero();
        this.complemento = logradouro.getComplemento();
        this.cidade = logradouro.getCidade();
        this.estado = logradouro.getEstado();
        this.cep = logradouro.getCep();
        this.pais = logradouro.getPais();
    }
}
