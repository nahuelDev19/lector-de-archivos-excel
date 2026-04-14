package com.example.leerDatos.entitys;

import com.poiji.annotation.ExcelCellName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class TransaccionDTO {
    

	@NotBlank(message = "Fecha no debe estar vacío")
	@ExcelCellName("fecha")
	private String fecha;
	@NotBlank(message = "Cliente no debe estar vacío")
	@ExcelCellName("cliente")
	private String cliente;
	@NotBlank(message = "Monto no debe estar vacío")
	@Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "monto inválido")
	@ExcelCellName("monto")
	private String monto;
	@NotBlank(message = "Moneda no debe estar vacío")
	@ExcelCellName("moneda")
	private String moneda;
	@NotBlank(message = "Tipo no debe estar vacío")
	private String tipo; // INGRESO o EGRESO
	@NotBlank(message = "Categoria no debe estar vacío")
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

