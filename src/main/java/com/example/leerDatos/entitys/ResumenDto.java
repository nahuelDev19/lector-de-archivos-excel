package com.example.leerDatos.entitys;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter @Setter
public class ResumenDto {

    private BigDecimal montoTotal;
    private int cantidadClientes;
    private Set<String> separarPorTipoMoneda;
    private BigDecimal montoMaximo;
    private BigDecimal montoMinimo;
    private Set<String> tipo;
    private Set<String> categoria;


    public ResumenDto() {
    }

    public ResumenDto(BigDecimal montoTotal, int cantidadClientes, Set<String> separarPorTipoMoneda, BigDecimal montoMaximo, BigDecimal montoMinimo) {
        this.montoTotal = montoTotal;
        this.cantidadClientes = cantidadClientes;
        this.separarPorTipoMoneda = separarPorTipoMoneda;
        this.montoMaximo = montoMaximo;
        this.montoMinimo = montoMinimo;
    }

    public ResumenDto(BigDecimal montoTotal, int cantidadClientes, Set<String> separarPorTipoMoneda, BigDecimal montoMaximo, BigDecimal montoMinimo,Set<String> tipo, Set<String> categoria) {
        this(montoTotal,cantidadClientes,separarPorTipoMoneda,montoMaximo,montoMinimo);
        this.tipo = tipo;
        this.categoria = categoria;
    }
}