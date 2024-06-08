package com.example.crud.repository;

import com.example.crud.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findAllByOrderByFuncaoAsc();
    Boolean existsByEmailAndSenha(String email, String senha);
    List<Usuario> findAllByOrderByNomeAsc();
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAllByEmpresaIdAndNomeContainingIgnoreCase(Long empresaId, String nome);
    List<Usuario> findAllByEmpresaIdOrderByNomeAsc(Long empresaId);
}
