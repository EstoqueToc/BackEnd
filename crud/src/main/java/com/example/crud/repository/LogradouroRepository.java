package com.example.crud.repository;

import com.example.crud.Model.Logradouro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LogradouroRepository extends JpaRepository<Logradouro, Long> {

    Optional<Logradouro> findByCep(String cep);

    List<Logradouro> findByCidade(String cidade);

    List<Logradouro> findByEstado(String estado);

    List<Logradouro> findByRua(String logradouro);

    void deleteByCep(String cep);
}
