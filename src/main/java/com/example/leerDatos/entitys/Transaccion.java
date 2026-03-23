package com.example.leerDatos.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "transaccion")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate fecha;
    private String cliente;
    private BigDecimal monto;
    private String moneda;
    private String tipo; // INGRESO o EGRESO
    private String categoria; // ejemplo: comida, sueldo, alquiler
    private String periodo;


    public Transaccion() {
    }

    public Transaccion(LocalDate fecha, String cliente, BigDecimal monto, String moneda) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.monto = monto;
        this.moneda = moneda;
    }

    public Transaccion(LocalDate fecha, String cliente, BigDecimal monto, String moneda, String tipo, String categoria) {
        this(fecha, cliente, monto, moneda);
        this.tipo = tipo;
        this.categoria = categoria;
    }
    public Transaccion(LocalDate fecha, String cliente, BigDecimal monto, String moneda, String tipo, String categoria, String periodo) {
        this(fecha, cliente, monto, moneda,tipo,categoria);
        this.periodo= periodo;
    }

}
