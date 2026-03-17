package com.example.leerDatos.repository;

import com.example.leerDatos.entitys.Resumen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ResumenRepo extends JpaRepository<Resumen, UUID> {
}
