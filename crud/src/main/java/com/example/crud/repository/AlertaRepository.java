package com.example.crud.repository;

import com.example.crud.Model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    Alerta findFirstByOrderByIdAsc();

}
