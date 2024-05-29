package com.example.crud.Controller;

import com.example.crud.GerenciadorArquivo.UsuarioCSV;
import com.example.crud.Helpers.ListaObj;
import com.example.crud.Model.Usuario;
import com.example.crud.dto.consultaDto.UsuarioConsultaDto;
import com.example.crud.dto.criacaoDto.UsuarioCriacaoDto;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.service.UsuarioService;
import com.example.crud.service.dto.UsuarioLoginDto;
import com.example.crud.service.dto.UsuarioTokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository repository;

    private final UsuarioService usuarioService;

    private final ModelMapper modelMapper;

    @Operation(summary = "Lista todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum usuário disponível", content = @Content)
    })
    @GetMapping
   @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<UsuarioConsultaDto>> listar() {
        return status(200).body(usuarioService.getAll());
    }

    @Operation(summary = "Pesquisa um usuário pelo índice na lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @GetMapping("/{indice}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioConsultaDto> pesquisarUsuario(
            @Parameter(description = "Índice do usuário na lista") @PathVariable Long indice) {
        return repository.findById(indice)
                .map(usuario -> status(200).body(modelMapper.map(usuario, UsuarioConsultaDto.class)))
                .orElse(status(404).build());
    }

    @Operation(summary = "Cadastra um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping("/cadastro")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> cadastrar(
            @Parameter(description = "Objeto do usuário com dados para cadastro") @RequestBody @Valid UsuarioCriacaoDto usuarioCriacaoDto) {
        this.usuarioService.criar(usuarioCriacaoDto);
        return status(201).build();
    }

    @Operation(summary = "Atualiza os dados de um usuário pelo índice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do usuário atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(hidden = true)))
    })
    @PutMapping("/{indice}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioConsultaDto> atualizarUsuario(
            @Parameter(description = "Índice do usuário na lista") @PathVariable Long indice,
            @Parameter(description = "Objeto do usuário com dados atualizados") @RequestBody @Valid UsuarioCriacaoDto usuarioAtualizadoDto) {
        if (repository.existsById(indice)) {
            Usuario usuarioAtualizado = modelMapper.map(usuarioAtualizadoDto, Usuario.class);
            usuarioAtualizado.setId(indice);
            repository.save(usuarioAtualizado);
            return status(200).body(modelMapper.map(usuarioAtualizado, UsuarioConsultaDto.class));
        }
        return status(404).build();
    }

    @Operation(summary = "Remove um usuário da lista pelo índice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @DeleteMapping("/{indice}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> removerUsuario(
            @Parameter(description = "Índice do usuário na lista para remoção") @PathVariable Long indice) {
        if (repository.existsById(indice)) {
            repository.deleteById(indice);
            return status(204).build();
        }
        return status(404).build();
    }

    @Operation(summary = "Lista os usuários em ordem alfabética")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários listados em ordem alfabética com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum usuário para listar", content = @Content)
    })
    @GetMapping("/lista-usuario")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<UsuarioConsultaDto>> listarUsuariosOrdenados() {
        List<UsuarioConsultaDto> listaOrdenada = repository.findAllByOrderByNomeAsc()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioConsultaDto.class))
                .collect(Collectors.toList());

        return listaOrdenada.isEmpty()
                ? status(204).build()
                : status(200).body(listaOrdenada);
    }

    @Operation(summary = "Ordena os usuários por Função")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários ordenados por função com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum usuário para ordenar por função", content = @Content)
    })
    @GetMapping("/lista-funcao")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<UsuarioConsultaDto>> listarUsuariosPorCargo() {
        List<UsuarioConsultaDto> usuarios = repository.findAllByOrderByFuncaoAsc()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioConsultaDto.class))
                .collect(Collectors.toList());

        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(usuarios);
    }

    @Operation(summary = "Valida se o usuário existe para realizar o login")
    @GetMapping("/login/{email}/{senha}")
    public ResponseEntity<Boolean> autenticarSenha(
            @Parameter(description = "Email do usuário") @PathVariable String email,
            @Parameter(description = "Senha do usuário") @PathVariable String senha) {
        return status(200).body(this.usuarioService.autenticarSenha(email, senha));
    }

    @Operation(summary = "Valida se o usuário existe para realizar o login")
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        UsuarioTokenDto usuarioTokenDto = this.usuarioService.autenticar(usuarioLoginDto);
        return status(200).body(usuarioTokenDto);
    }

    //endpoints para consumir as classes 'UsuarioCSV'
    @Operation(summary = "Grava arquivo CSV de Usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo CSV de Usuário gravado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao gravar arquivo CSV de Usuário", content = @Content)
    })
    @PostMapping("/csv/usuario")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<String> gravaArquivoCsvUsuario() {
        ListaObj<Usuario> lista = new ListaObj<>(100);
        lista.adicionaLista(repository.findAll());
        UsuarioCSV.gravaArquivoCsv(lista, "usuarios");
        return ok("Gravando arquivo CSV de Usuário");
    }

    @Operation(summary = "Lê arquivo CSV de Usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo CSV de Usuário lido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao ler arquivo CSV de Usuário", content = @Content)
    })
    @GetMapping("/csv/usuario")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<String> leArquivoCsvUsuario() {
        UsuarioCSV.lerArquivoCsv("usuarios");
        return ok("Lendo arquivo CSV de Usuário");
    }
}
