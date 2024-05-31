package com.example.crud.repository;

import com.example.crud.Model.Logradouro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LogradouroRepository extends JpaRepository<Logradouro, Long> {

    Optional<Logradouro> findByCepLogradouro(String cep);

    List<Logradouro> findByCidadeLogradouro(String cidade);

    List<Logradouro> findByEstadoLogradouro(String estado);

    List<Logradouro> findByRuaLogradouro(String logradouro);

    void deleteByCepLogradouro(String cep);
}
