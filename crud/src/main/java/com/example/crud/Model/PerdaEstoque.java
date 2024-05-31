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
public class PerdaEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer qtdPerdida;
    private LocalDate dataDaPerda;

    @ManyToOne
    @JoinColumn(name = "produto", nullable = false)
    private Produto produto;


    public PerdaEstoque() {

    }
}