package com.example.crud.dto.mapper;

import com.example.crud.Model.Usuario;
import com.example.crud.dto.consultaDto.EmpresaDetalhesConsulta;
import com.example.crud.dto.consultaDto.UsuarioConsultaDto;
import com.example.crud.dto.criacaoDto.UsuarioCriacaoDto;
import com.example.crud.repository.EmpresaRepository;
import com.example.crud.service.dto.UsuarioTokenDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class UsuarioMapper {

    private final EmpresaRepository empresaRepository;
    private final ModelMapper modelMapper;

    public static Usuario toEntity(UsuarioCriacaoDto usuarioCriacaoDto) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioCriacaoDto.getNome());
        usuario.setCPF(usuarioCriacaoDto.getCPF());
        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setSenha(usuarioCriacaoDto.getSenha());
        usuario.setDtNascimento(usuarioCriacaoDto.getDtNascimento());
        usuario.setFuncao(usuarioCriacaoDto.getFuncao());
        usuario.setAcesso(usuarioCriacaoDto.isAcesso());
        return usuario;
    }

    public static UsuarioConsultaDto toConsultaDto(Usuario usuario) {
        return new UsuarioConsultaDto(usuario);
    }

    public static UsuarioTokenDto of(Usuario usuario, String token){
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();
        usuarioTokenDto.setUserId(usuario.getId());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setToken(token);
        usuarioTokenDto.setTipo(usuario.getFuncao());
        usuarioTokenDto.setFkEmpresa(new EmpresaDetalhesConsulta(usuario.getFkEmpresa()));
        return usuarioTokenDto;
    }
}
