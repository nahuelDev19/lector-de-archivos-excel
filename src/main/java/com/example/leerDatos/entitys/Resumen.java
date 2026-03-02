package com.example.leerDatos.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity @Table(name = "resumen")
public class Resumen {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private BigDecimal montoTotal;
    private int cantidadClientes;
    private Set<String> separarPorTipoMoneda;
    private BigDecimal montoMaximo;
    private BigDecimal montoMinimo;
    private Set<String> tipo;
    private Set<String> categoria;

    public Resumen() {
    }

    public Resumen(BigDecimal montoTotal, int cantidadClientes, Set<String> separarPorTipoMoneda, BigDecimal montoMaximo, BigDecimal montoMinimo, Set<String> tipo, Set<String> categoria) {
        this.montoTotal = montoTotal;
        this.cantidadClientes = cantidadClientes;
        this.separarPorTipoMoneda = separarPorTipoMoneda;
        this.montoMaximo = montoMaximo;
        this.montoMinimo = montoMinimo;
        this.tipo = tipo;
        this.categoria = categoria;
    }
}
