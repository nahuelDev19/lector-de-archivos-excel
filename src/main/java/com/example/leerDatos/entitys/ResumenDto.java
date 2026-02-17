package com.example.leerDatos.entitys;

import java.util.List;
import java.util.Set;

public class ResumenDto {

    private int montoTotal;
    private int cantidadClientes;
    private Set<String> separarPorTipoMoneda;
    private int montoMaximo;
    private int montoMinimo;

    public ResumenDto() {
    }

    public ResumenDto(int montoTotal, int cantidadClientes, Set<String> separarPorTipoMoneda, int montoMaximo, int montoMinimo) {
        this.montoTotal = montoTotal;
        this.cantidadClientes = cantidadClientes;
        this.separarPorTipoMoneda = separarPorTipoMoneda;
        this.montoMaximo = montoMaximo;
        this.montoMinimo = montoMinimo;
    }

    public int getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }

    public int getCantidadClientes() {
        return cantidadClientes;
    }

    public void setCantidadClientes(int cantidadClientes) {
        this.cantidadClientes = cantidadClientes;
    }

    public Set<String> getSepararPorTipoMoneda() {
        return separarPorTipoMoneda;
    }

    public void setSepararPorTipoMoneda(Set<String> separarPorTipoMoneda) {
        this.separarPorTipoMoneda = separarPorTipoMoneda;
    }

    public int getMontoMaximo() {
        return montoMaximo;
    }

    public void setMontoMaximo(int montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    public int getMontoMinimo() {
        return montoMinimo;
    }

    public void setMontoMinimo(int montoMinimo) {
        this.montoMinimo = montoMinimo;
    }
}
