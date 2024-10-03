package com.example.crud.service;

import com.example.crud.Model.Usuario;
import com.example.crud.configuration.security.jwt.GerenciadorTokenJwt;
import com.example.crud.dto.consultaDto.UsuarioConsultaDto;
import com.example.crud.dto.consultaDto.UsuarioSimplesDto;
import com.example.crud.dto.criacaoDto.UsuarioCriacaoDto;
import com.example.crud.dto.mapper.UsuarioMapper;
import com.example.crud.excecoes.RecursoNaoEncontradoException;
import com.example.crud.repository.EmpresaRepository;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.service.dto.UsuarioLoginDto;
import com.example.crud.service.dto.UsuarioTokenDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final ModelMapper mapper;
    private final EmpresaRepository empresaRepository;

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

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    public List<UsuarioConsultaDto> getAll() {
        List<Usuario> lista = usuarioRepository.findAll();

        if (lista.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }

        return mapper.map(lista, new TypeToken<List<UsuarioConsultaDto>>() {
        }.getType());
    }

    public List<UsuarioSimplesDto> getSimples(Long id) {
        List<Usuario> lista = usuarioRepository.findAllByEmpresaIdOrderByNomeAsc(id);
        if(lista.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return mapper.map(lista, new TypeToken<List<UsuarioSimplesDto>>(){}.getType());
    }

    public List<UsuarioSimplesDto> getSimplesNome(String nome, Long id) {
        List<Usuario> lista = usuarioRepository.findAllByEmpresaIdAndNomeContainingIgnoreCase(id, nome);
        if(lista.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return mapper.map(lista, new TypeToken<List<UsuarioSimplesDto>>(){}.getType());
    }

    public void criar(UsuarioCriacaoDto usuarioCriacaoDto) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioCriacaoDto);
        String senhaCriptografada = passwordEncoder.encode(usuarioCriacaoDto.getSenha());
        usuario.setSenha(senhaCriptografada);
        var empresaId = usuarioCriacaoDto.getEmpresa().getId();
        validadeUsuario(usuario);

        var empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa", empresaId));
        usuario.setEmpresa(empresa);
        usuarioRepository.save(usuario);
    }

    public Usuario getUm(Long codigo) {
        validarCodigoFuncionario(codigo);
        return usuarioRepository.findById(codigo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", codigo));
    }

    public void excluirUm(Long codigo) {
        validarCodigoFuncionario(codigo);
        usuarioRepository.deleteById(codigo);
    }

    void validarCodigoFuncionario(Long codigo) {
        if (!usuarioRepository.existsById(codigo)) {
            throw new RecursoNaoEncontradoException("Funcionário", codigo);
        }
    }

    public void atualizar(Long codigo, UsuarioCriacaoDto usuarioCriacaoDto) {
        validarCodigoFuncionario(codigo);
        final Usuario usuario = UsuarioMapper.toEntity(usuarioCriacaoDto);
        usuario.setId(codigo);
        String senhaCriptografada = passwordEncoder.encode(usuarioCriacaoDto.getSenha());
        usuario.setSenha(senhaCriptografada);
        validadeUsuario(usuario);

        var empresa = empresaRepository.findById(usuarioCriacaoDto.getEmpresa().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa", usuarioCriacaoDto.getEmpresa().getId()));
        usuario.setEmpresa(empresa);
        this.usuarioRepository.save(usuario);
    }

    private boolean usuarioExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    private void validadeUsuario(Usuario usuario){
        if(usuarioExiste(usuario.getEmail())){
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email já cadastrado");
        }

        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Nome é obrigatório");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Email é obrigatório");
        }

        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Senha é obrigatória");
        }

        if (usuario.getFuncao() == null || usuario.getFuncao().isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Função é obrigatória");
        }

        if (usuario.getEmpresa() == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Empresa é obrigatória");
        }
    }

}
