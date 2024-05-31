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

    private String ruaLogradouro;
    private String numeroLogradouro;
    private String complementoLogradouro;
    private String cidadeLogradouro;
    private String estadoLogradouro;
    private String cepLogradouro;

    public Logradouro() {
    }

    public Logradouro(Long id, String rua, String numero, String complemento, String cidade, String estado, String cep) {
        this.id = id;
        this.ruaLogradouro = rua;
        this.numeroLogradouro = numero;
        this.complementoLogradouro = complemento;
        this.cidadeLogradouro = cidade;
        this.estadoLogradouro = estado;
        this.cepLogradouro = cep;
    }

    public void setEndereco(String ruaExemplo) {
        String[] partesEndereco = ruaExemplo.split(", ");

        if (partesEndereco.length == 4) {
            this.ruaLogradouro = partesEndereco[0];
            this.numeroLogradouro = partesEndereco[1];
            this.complementoLogradouro = partesEndereco[2];
            String[] cidadeEstado = partesEndereco[3].split(" - ");
            this.cidadeLogradouro = cidadeEstado[0];
            this.estadoLogradouro = cidadeEstado[1];
        } else {
            // Lidar com um formato de endereço inválido
            System.err.println("Formato de endereço inválido: " + ruaExemplo);
        }
    }

}