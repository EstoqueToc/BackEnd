package com.example.crud.dto.consultaResposta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertaRespostaDto {

    private Long id;
    private Integer alertaModerado;
    private Integer alertaGrave;

}
