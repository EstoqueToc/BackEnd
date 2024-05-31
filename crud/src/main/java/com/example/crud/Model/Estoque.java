package com.example.crud.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Integer qtdSaida;
    private LocalDate dataSaida;
    private Integer qtdDisponivel;

    @ManyToOne
    @JoinColumn(name = "empresa", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "produto", nullable = false)
    private Produto produto;

    public Estoque() {

    }
}
