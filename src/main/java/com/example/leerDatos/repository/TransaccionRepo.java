package com.example.leerDatos.repository;

import com.example.leerDatos.entitys.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface TransaccionRepo extends JpaRepository<Transaccion, UUID> {

    List<Transaccion> findByCategoria(String categoria);


}
