package com.example.crud.service.usuario;

import com.example.crud.Model.Usuario;
import com.example.crud.configuration.security.jwt.GerenciadorTokenJwt;
import com.example.crud.dto.criacaoDto.UsuarioCriacaoDto;
import com.example.crud.dto.mapper.UsuarioMapper;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.service.usuario.autenticacao.dto.UsuarioLoginDto;
import com.example.crud.service.usuario.autenticacao.dto.UsuarioTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    //fazer metodo da service, para esse metodo 'listar' que esta na classe UsuarioController
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public void criar(UsuarioCriacaoDto usuarioCriacaoDto) {
        final Usuario usuario = UsuarioMapper.toEntity(usuarioCriacaoDto);
        String senhaCriptografada = passwordEncoder.encode(usuarioCriacaoDto.getSenha());
        usuario.setSenha(senhaCriptografada);
        this.usuarioRepository.save(usuario);
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());
        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado = usuarioRepository.findByEmail(usuarioLoginDto.getEmail())
                .orElseThrow(
                        () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = gerenciadorTokenJwt.generateToken(authentication);
        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public Boolean autenticarSenha(String email, String senha) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, senha);
        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado = usuarioRepository.findByEmail(email)
                .orElseThrow(
                        () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }
}
