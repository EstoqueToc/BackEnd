package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Empresa;
import com.example.crud.Model.Usuario;
import com.example.crud.dto.criacaoDto.UsuarioCriacaoDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioConsultaDto {

    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private String funcao;
    private int acesso;
    private int ativo;
    private EmpresaDetalhesConsulta empresa;

    public UsuarioConsultaDto() {
    }

    public UsuarioConsultaDto(Usuario usuario) {
        this.nome = usuario.getNome();
        this.cpf = usuario.getCpf();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.dataNascimento = usuario.getDataNascimento();
        this.funcao = usuario.getFuncao();
        this.acesso = usuario.getAcesso();
        this.ativo = usuario.getAtivo();
    }

    public UsuarioConsultaDto(UsuarioCriacaoDto usuario) {
        this.nome = usuario.getNome();
        this.cpf = usuario.getCpf();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.dataNascimento = usuario.getDataNascimento();
        this.funcao = usuario.getFuncao();
        this.acesso = usuario.getAcesso();
        this.ativo = usuario.getAtivo();
    }
}
