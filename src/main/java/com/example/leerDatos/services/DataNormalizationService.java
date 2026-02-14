package com.example.leerDatos.services;

import java.time.LocalDate;
import java.util.List;

import com.example.leerDatos.entitys.TransaccionDTO;
import  java.time.format.DateTimeFormatter;
public class DataNormalizationService {

	 public List<TransaccionDTO> normalizar(List<TransaccionDTO> datos) {
		 for (TransaccionDTO dto: datos ) {
			  dto.setFecha(convertirFechaISO(dto.getFecha()));
			  dto.setMoneda(dto.getMoneda().toUpperCase().trim());
			  dto.setCliente(dto.getCliente().toUpperCase().trim());

		 }
		 return  datos;
	    }

	    private String convertirFechaISO(String fecha) {
		 try {
			 DateTimeFormatter input = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			 LocalDate date = LocalDate.parse(fecha,input);
			 return  date.toString();
		 }catch (Exception e){

		 return fecha;
		 }

	    }
}
