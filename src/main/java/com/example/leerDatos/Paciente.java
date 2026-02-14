package com.example.leerDatos;

import java.time.LocalDateTime;

import com.poiji.annotation.ExcelCellName;

public class Paciente {

	@ExcelCellName("ID")
	private Long id;
	@ExcelCellName("NOMBRE")
	private String nombre;
	@ExcelCellName("EDAD")
	private int edad;
	@ExcelCellName("SEXO")
	private String sexo;
	@ExcelCellName("DIAGNÓSTICO")
	private String diagnostico;
	@ExcelCellName("FECHA DE INGRESO")
	private LocalDateTime fechaIngreso;

	public Paciente() {

	}

	public Paciente(Long id, String nombre, int edad, String sexo, String diagnostico, LocalDateTime fechaIngreso) {
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
		this.sexo = sexo;
		this.diagnostico = diagnostico;
		this.fechaIngreso = fechaIngreso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public LocalDateTime getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDateTime fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	@Override
	public String toString() {
		return "Paciente [id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", sexo=" + sexo + ", diagnostico="
				+ diagnostico + ", fechaIngreso=" + fechaIngreso + "]";
	}

	
	
}
