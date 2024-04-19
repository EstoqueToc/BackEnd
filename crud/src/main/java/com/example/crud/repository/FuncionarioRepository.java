package com.example.crud.repository;

import com.example.crud.Model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findAllByOrderByFuncaoAsc();
    boolean existsByEmailAndSenha(String email, String senha);
}
