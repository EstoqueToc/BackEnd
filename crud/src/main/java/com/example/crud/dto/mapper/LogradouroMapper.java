package com.example.crud.dto.mapper;

import com.example.crud.Model.Logradouro;
import com.example.crud.dto.consultaDto.LogradouroConsultaDto;
import com.example.crud.dto.criacaoDto.LogradouroCriacaoDto;

public class LogradouroMapper {

    public static Logradouro toEntity(LogradouroCriacaoDto logradouroCriacaoDto) {
        Logradouro logradouro = new Logradouro();
        logradouro.setRua(logradouroCriacaoDto.getRua());
        logradouro.setNumero(logradouroCriacaoDto.getNumero());
        logradouro.setComplemento(logradouroCriacaoDto.getComplemento());
        logradouro.setCidade(logradouroCriacaoDto.getCidade());
        logradouro.setEstado(logradouroCriacaoDto.getEstado());
        logradouro.setCep(logradouroCriacaoDto.getCep());
        logradouro.setPais(logradouroCriacaoDto.getPais());
        // Pode haver mais atributos a serem mapeados, dependendo da sua entidade Logradouro
        return logradouro;
    }

    public static LogradouroConsultaDto toConsultaDto(Logradouro logradouro) {
        return new LogradouroConsultaDto(logradouro);
    }
}
