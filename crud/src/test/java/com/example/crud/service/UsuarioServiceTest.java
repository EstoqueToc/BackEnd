//package com.example.crud.service;
//
//import com.example.crud.Model.Usuario;
//import com.example.crud.excecoes.RecursoNaoEncontradoException;
//import com.example.crud.repository.UsuarioRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class UsuarioServiceTest {
//    UsuarioService service;
//    UsuarioRepository usuarioRepository;
//
//
//    @BeforeEach
//    void setUp() {
//        usuarioRepository = mock(UsuarioRepository.class);
//        service = new UsuarioService(usuarioRepository);
//    }
//
//    @DisplayName(
//            "Se o id existir, deve retornar o funcionário")
//    @Test
//    void getUmEncontrado() {
//        Long codigo = 1L;
//        Usuario esperado = mock(Usuario.class);
//
//        when(usuarioRepository.existsById(codigo))
//                .thenReturn(true);
//        when(usuarioRepository.findById(codigo))
//                .thenReturn(Optional.of(esperado));
//
//        var resultado = service.getUm(codigo);
//        assertEquals(esperado,resultado);
//    }
//
//    @DisplayName(
//            " Se o código NÃO existir, deve lançar exceção")
//    @Test
//    void getUmNaoEcontra(){
//        Long codigo = 51L;
//        when(usuarioRepository.existsById(codigo))
//                .thenReturn(false);
//        assertThrows(RecursoNaoEncontradoException.class, () -> service.getUm(codigo));
//    }
//
//    @DisplayName("Se o codigo existir, ao tentar excluir e der certo")
//    @Test
//    void excluirUmOk(){
//        Long codigo = 1L;
//
//        when(usuarioRepository.existsById(codigo))
//                .thenReturn(Boolean.valueOf(true));
//        assertDoesNotThrow(()->service.excluirUm(codigo));
//    }
//
//    @DisplayName("Se o codigo NÃO existir, ao tentar excluir, deve lançar erro")
//    @Test
//    void excluirUmBad(){
//        Long codigo = 51L;
//        assertThrows(RecursoNaoEncontradoException.class, () -> service.excluirUm(codigo));
//    }
//
//    @DisplayName("Caso existam funcionarios, deve retornar a lista")
//    @Test
//    void getAllComValores(){
//
//        var listaEsperada = List.of(mock(Usuario.class),
//                mock(Usuario.class),
//                mock(Usuario.class));
//
//        when(usuarioRepository.findAll()).thenReturn(listaEsperada);
//
//        var resultado = service.getAll();
//
//        assertEquals(listaEsperada.size(), resultado.size());
//        for(int f =0; f<listaEsperada.size(); f++){
//            assertEquals(listaEsperada.get(f), resultado.get(f));
//        }
//    }
//
//}