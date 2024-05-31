package com.example.crud.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeProduto;
    private String descricaoProduto;
    private LocalDate dataValidade;
    private Double precoCompraProduto;
    private Double precoVendaProduto;
    private LocalDate dataEntrada;
    private String unidadeMedida;
    private Integer qtdEntrada;

    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "fornecedor", nullable = false)
    private Fornecedor fornecedor;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alerta> alertaEstoque;

    @OneToMany(mappedBy = "produto")
    private List<Alerta> alertas;

    public Produto() {
    }
}