package com.example.leerDatos.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "transacion")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fecha;
    private String cliente;
    private BigDecimal monto;
    private String moneda;
    private String tipo; // INGRESO o EGRESO
    private String categoria; // ejemplo: comida, sueldo, alquiler


    public Transaccion() {
    }

    public Transaccion(String fecha, String cliente, BigDecimal monto, String moneda) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.monto = monto;
        this.moneda = moneda;
    }

    public Transaccion(String fecha, String cliente, BigDecimal monto, String moneda, String tipo, String categoria) {
        this(fecha, cliente, monto, moneda);
        this.tipo = tipo;
        this.categoria = categoria;
    }
}
