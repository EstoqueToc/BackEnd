package com.example.crud.dto.consultaDto;

import com.example.crud.Model.Usuario;
import com.example.crud.dto.criacaoDto.UsuarioCriacaoDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioConsultaDto {

    private String nome;
    private String CPF;
    private String email;
    private String senha;
    private LocalDate dtNascimento;
    private String funcao;
    private boolean acesso;
    private EmpresaDetalhesConsulta fkEmpresa;

    public UsuarioConsultaDto() {
    }

    public UsuarioConsultaDto(Usuario usuario) {
        this.nome = usuario.getNome();
        this.CPF = usuario.getCPF();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.dtNascimento = usuario.getDtNascimento();
        this.funcao = usuario.getFuncao();
        this.acesso = usuario.isAcesso();
    }

    public UsuarioConsultaDto(UsuarioCriacaoDto usuario) {
        this.nome = usuario.getNome();
        this.CPF = usuario.getCPF();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.dtNascimento = usuario.getDtNascimento();
        this.funcao = usuario.getFuncao();
        this.acesso = usuario.isAcesso();
    }
}
