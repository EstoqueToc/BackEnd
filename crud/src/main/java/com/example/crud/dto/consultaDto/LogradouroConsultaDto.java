package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Logradouro;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogradouroConsultaDto {

    protected Long id;
    private String rua;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;

    public LogradouroConsultaDto() {
    }

    public LogradouroConsultaDto(Logradouro logradouro) {
        this.id = logradouro.getId();
        this.rua = logradouro.getRuaLogradouro();
        this.numero = logradouro.getNumeroLogradouro();
        this.complemento = logradouro.getComplementoLogradouro();
        this.cidade = logradouro.getCidadeLogradouro();
        this.estado = logradouro.getEstadoLogradouro();
        this.cep = logradouro.getCepLogradouro();
    }
}
