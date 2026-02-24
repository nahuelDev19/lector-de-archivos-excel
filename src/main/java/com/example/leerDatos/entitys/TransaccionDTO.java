package com.example.leerDatos.entitys;

import com.poiji.annotation.ExcelCellName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class TransaccionDTO {
    
	
	@ExcelCellName("fecha")
	private String fecha;
	@ExcelCellName("cliente")
	private String cliente;
	@ExcelCellName("monto")
	private String monto;
	@ExcelCellName("moneda")
	private String moneda;

	private String tipo; // INGRESO o EGRESO
	private String categoria; // ejemplo: comida, sueldo, alquiler

	public TransaccionDTO() {

	}
	public TransaccionDTO(String fecha, String cliente, String monto, String moneda) {
		this.fecha = fecha;
		this.cliente = cliente;
		this.monto = monto;
		this.moneda = moneda;

	}

	public TransaccionDTO(String fecha, String cliente, String monto, String moneda, String tipo, String categoria) {
		this(fecha, cliente, monto, moneda);
		this.tipo = tipo;
		this.categoria = categoria;
	}
}

