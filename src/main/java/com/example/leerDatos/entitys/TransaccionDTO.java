package com.example.leerDatos.entitys;

import com.poiji.annotation.ExcelCellName;

public class TransaccionDTO {
    
	
	@ExcelCellName("fecha")
	private String fecha;
	@ExcelCellName("cliente")
	private String cliente;
	@ExcelCellName("monto")
	private String monto;
	@ExcelCellName("moneda")
	private String moneda;
	
    public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
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

    // getters y setters
    
    
}
