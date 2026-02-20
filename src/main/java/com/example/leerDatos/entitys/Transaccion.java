package com.example.leerDatos.entitys;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class Transaccion {



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
