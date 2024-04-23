package com.example.crud.repository;

import com.example.crud.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findAllByOrderByFuncaoAsc();
    Boolean existsByEmailAndSenha(String email, String senha);
    List<Usuario> findAllByOrderByNomeAsc();
}
