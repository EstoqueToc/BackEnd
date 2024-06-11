package com.example.crud.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer alertaModerado;

    private Integer alertaGrave;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

//    @ManyToOne
//    @JoinColumn(name = "empresa_id", nullable = false)
//    private Empresa empresa;
}

