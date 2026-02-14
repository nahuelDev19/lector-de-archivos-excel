package com.example.leerDatos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.leerDatos.entitys.TransaccionDTO;

public class ExcelProcessingService {

	 public ExcelProcessingService(DataCleaningService cleaningService,
	DataNormalizationService normalizationService,
    DataTransformationService transformationService){
		 
		this.cleaningService = cleaningService;
		this.normalizationService = normalizationService;
		this.transformationService = transformationService;
	}

	public void procesarExcel(MultipartFile file) {
		List<TransaccionDTO> datos = leerExcel(file);

		datos = cleaningService.limpiar(datos);
		datos = normalizationService.normalizar(datos);
		datos = transformationService.transformar(datos);

//acá teóricamente enviamos para a análisis(no hace falta que lo hagas aún)
	}

	private List<TransaccionDTO> leerExcel(MultipartFile file) {
		List<TransaccionDTO> lista = new ArrayList<>();

		return lista;
	}

}
