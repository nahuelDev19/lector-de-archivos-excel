package com.example.leerDatos.entitys;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter @Setter
public class ResumenDto {

    private int montoTotal;
    private int cantidadClientes;
    private Set<String> separarPorTipoMoneda;
    private int montoMaximo;
    private int montoMinimo;
    private Set<String> tipo;
    private Set<String> categoria;


    public ResumenDto() {
    }

    public ResumenDto(int montoTotal, int cantidadClientes, Set<String> separarPorTipoMoneda, int montoMaximo, int montoMinimo) {
        this.montoTotal = montoTotal;
        this.cantidadClientes = cantidadClientes;
        this.separarPorTipoMoneda = separarPorTipoMoneda;
        this.montoMaximo = montoMaximo;
        this.montoMinimo = montoMinimo;
    }

    public ResumenDto(int montoTotal, int cantidadClientes, Set<String> separarPorTipoMoneda, int montoMaximo, int montoMinimo,Set<String> tipo, Set<String> categoria) {
        this(montoTotal,cantidadClientes,separarPorTipoMoneda,montoMaximo,montoMinimo);
        this.tipo = tipo;
        this.categoria = categoria;
    }
}