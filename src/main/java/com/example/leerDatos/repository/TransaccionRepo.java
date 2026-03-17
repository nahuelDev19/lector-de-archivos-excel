package com.example.leerDatos.repository;

import com.example.leerDatos.entitys.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface TransaccionRepo extends JpaRepository<Transaccion, UUID> {

    List<Transaccion> findByCategoria(String categoria);


}
