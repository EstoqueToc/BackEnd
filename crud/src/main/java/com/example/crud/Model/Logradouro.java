package com.example.crud.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Logradouro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String rua;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;

    @ManyToOne
    private Empresa fkEmpresa;

    public Logradouro() {
    }

    public Logradouro(Long id, String rua, String numero, String complemento, String cidade, String estado, String cep, String pais) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.pais = pais;
    }

    public void setEndereco(String ruaExemplo) {
        String[] partesEndereco = ruaExemplo.split(", ");

        if (partesEndereco.length == 4) {
            this.rua = partesEndereco[0];
            this.numero = partesEndereco[1];
            this.complemento = partesEndereco[2];
            String[] cidadeEstado = partesEndereco[3].split(" - ");
            this.cidade = cidadeEstado[0];
            this.estado = cidadeEstado[1];
        } else {
            // Lidar com um formato de endereço inválido
            System.err.println("Formato de endereço inválido: " + ruaExemplo);
        }
    }

}