package com.example.crud.Controller;

import com.example.crud.Model.Usuario;
import com.example.crud.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Operation(summary = "Lista todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum usuário disponível", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        var lista = repository.findAll();
        return lista.isEmpty()
                ? status(204).build()
                : status(200).body(lista);
    }

    @Operation(summary = "Pesquisa um usuário pelo índice na lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @GetMapping("/{indice}")
    public ResponseEntity<Usuario> pesquisarUsuario(
            @Parameter(description = "Índice do usuário na lista") @PathVariable Long indice) {
        return of(repository.findById(indice));
    }

    @Operation(summary = "Cadastra um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping
    public ResponseEntity<Usuario> cadastrar(
            @Parameter(description = "Objeto do usuário com dados para cadastro") @RequestBody @Valid Usuario usuarioNovo) {
        repository.save(usuarioNovo);
        return status(201).body(usuarioNovo);
    }

    @Operation(summary = "Atualiza os dados de um usuário pelo índice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do usuário atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content(schema = @Schema(hidden = true)))
    })
    @PutMapping("/{indice}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @Parameter(description = "Índice do usuário na lista") @PathVariable Long indice,
            @Parameter(description = "Objeto do usuário com dados atualizados") @RequestBody @Valid Usuario usuarioAtualizado) {
        if (repository.existsById(indice)) {
            usuarioAtualizado.setId(indice);
            repository.save(usuarioAtualizado);
            return status(200).body(usuarioAtualizado);
        }
        return status(404).build();
    }

    @Operation(summary = "Remove um usuário da lista pelo índice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @DeleteMapping("/{indice}")
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
    public ResponseEntity<List<Usuario>> listarUsuariosOrdenados() {
        var listaOrdenada = repository.findAllByOrderByNomeAsc();
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
    public ResponseEntity<List<Usuario>> listarUsuariosPorCargo() {
        List<Usuario> usuarios = repository.findAllByOrderByFuncaoAsc();
        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(usuarios);
    }

    @Operation(summary = "Valida se o usuário existe para login")
    @GetMapping("/login/{email}/{senha}")
    public ResponseEntity<Boolean> validarUsuario(@PathVariable String email, @PathVariable String senha) {
        Boolean usuario = repository.existsByEmailAndSenha(email, senha);

        return usuario == false
                ? status(404).build()
                : status(200).body(usuario);
    }

}
