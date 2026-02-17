package com.example.leerDatos.entitys;

import com.poiji.annotation.ExcelCellName;

public class Transaccion {



    private String fecha;
    private String cliente;
    private String monto;
    private String moneda;

    public Transaccion() {
    }

    public Transaccion(String fecha, String cliente, String monto, String moneda) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.monto = monto;
        this.moneda = moneda;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
