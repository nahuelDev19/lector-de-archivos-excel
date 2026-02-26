package com.example.leerDatos.repository;

import com.example.leerDatos.entitys.Resumen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResumenRepo extends JpaRepository<Resumen, UUID> {
}
