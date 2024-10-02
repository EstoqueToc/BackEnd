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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
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

    //fazer metodo da service, para esse metodo 'listar' que esta na classe UsuarioController
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
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

    public List<UsuarioConsultaDto> getAll() {
        List<Usuario> lista = usuarioRepository.findAll();

        if (lista.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }

        List<UsuarioConsultaDto> listaDtos = mapper.map(lista, new TypeToken<List<UsuarioConsultaDto>>() {
        }.getType());
        return listaDtos;
    }

    public List<UsuarioSimplesDto> getSimples(Long id) {
        List<Usuario> lista = usuarioRepository.findAllByEmpresaIdOrderByNomeAsc(id);
        if(lista.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        List<UsuarioSimplesDto> listaDtos = mapper.map(lista, new TypeToken<List<UsuarioSimplesDto>>(){}.getType());
        return listaDtos;
    }

    public List<UsuarioSimplesDto> getSimplesNome(String nome, Long id) {
        List<Usuario> lista = usuarioRepository.findAllByEmpresaIdAndNomeContainingIgnoreCase(id, nome);
        if(lista.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        List<UsuarioSimplesDto> listaDtos = mapper.map(lista, new TypeToken<List<UsuarioSimplesDto>>(){}.getType());
        return listaDtos;
    }

    public void criar(UsuarioCriacaoDto usuarioCriacaoDto) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioCriacaoDto);
        String senhaCriptografada = passwordEncoder.encode(usuarioCriacaoDto.getSenha());
        usuario.setSenha(senhaCriptografada);
        var empresaId = usuarioCriacaoDto.getEmpresa().getId();
        var empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa", empresaId));
        usuario.setEmpresa(empresa);
        usuarioRepository.save(usuario);
    }

    public Usuario getUm(Long codigo) {
        validarCodigoFuncionario(codigo);
        return usuarioRepository.findById(codigo).get();
    }

    public void excluirUm(Long codigo) {
        validarCodigoFuncionario(codigo);
        usuarioRepository.deleteById(codigo);
    }

    void validarCodigoFuncionario(Long codigo) {
        if (!usuarioRepository.existsById(codigo)) {
            throw new RecursoNaoEncontradoException("Funcionário", codigo);
        }

//    public void testListar() {
//        List<Usuario> expectedUsuarios = new ArrayList<>();
//        expectedUsuarios.add(usuario);
//
//        when(usuarioRepository.findAll()).thenReturn(expectedUsuarios);
//
//        List<Usuario> actualUsuarios = usuarioService.listar();
//
//        assertEquals(expectedUsuarios.size(), actualUsuarios.size());
//        assertEquals(expectedUsuarios.get(0), actualUsuarios.get(0));
//    }
//        @Test
//        public void testAutenticarSenha() {
//            String email = "test@example.com";
//            String senha = "testPassword";
//
//            when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
//            when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
//
//            assertTrue(usuarioService.autenticarSenha(email, senha));
//        }
//
//        @Test
//        public void testExcluirUmNotFound() {
//            Long codigo = 1L;
//            when(usuarioRepository.existsById(codigo)).thenReturn(false);
//
//            assertThrows(RecursoNaoEncontradoException.class, () -> usuarioService.excluirUm(codigo));
//        }


    }

    public void atualizar(Long codigo, UsuarioCriacaoDto usuarioCriacaoDto) {
        validarCodigoFuncionario(codigo);
        final Usuario usuario = UsuarioMapper.toEntity(usuarioCriacaoDto);
        usuario.setId(codigo);
        String senhaCriptografada = passwordEncoder.encode(usuarioCriacaoDto.getSenha());
        usuario.setSenha(senhaCriptografada);
        var empresa = empresaRepository.findById(usuarioCriacaoDto.getEmpresa().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa", usuarioCriacaoDto.getEmpresa().getId()));
        usuario.setEmpresa(empresa);
        this.usuarioRepository.save(usuario);
    }

    public void salvarUsuariosEmLote(MultipartFile file) throws IOException {
        List<Usuario> usuarios = lerXlsx(file);
        usuarioRepository.saveAll(usuarios);
    }

    private List<Usuario> lerXlsx(MultipartFile file) throws IOException {
        List<Usuario> usuarios = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);  // Pega a primeira aba do Excel
            Iterator<Row> rowIterator = sheet.iterator();

            // Ignora a primeira linha se for o cabeçalho
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Usuario usuario = new Usuario();

                usuario.setNome(getCellValue(row.getCell(0)));
                usuario.setEmail(getCellValue(row.getCell(1)));
                usuario.setCpf(getCellValue(row.getCell(2)));

                usuarios.add(usuario);
            }
        }

        return usuarios;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}
