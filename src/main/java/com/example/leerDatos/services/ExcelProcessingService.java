package com.example.leerDatos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.leerDatos.entitys.TransaccionDTO;

public class ExcelProcessingService {

	private DataAnalysisService dataAnalysisService;
	private  DataNormalizationService normalizationService;
	private  DataTransformationService transformationService;
	private DataCleaningService cleaningService;


	 public ExcelProcessingService(DataCleaningService cleaningService,
	DataNormalizationService normalizationService,
    DataTransformationService transformationService, DataAnalysisService dataAnalysisService){
		 
		this.cleaningService = cleaningService;
		this.normalizationService = normalizationService;
		this.transformationService = transformationService;
		this.dataAnalysisService = dataAnalysisService;
	}

	public ResumDto procesarExcel(MultipartFile file) {
		List<TransaccionDTO> datos = leerExcel(file);
//definir un metodo pipeline que itere todos los procesos que deben atravezar los datos
		datos = cleaningService.limpiar(datos);
		datos = normalizationService.normalizar(datos);
		datos = transformationService.transformar(datos);
		 return	dataAnalysisService.analizar(datos);

	}

	private List<TransaccionDTO> leerExcel(MultipartFile file) {
		List<TransaccionDTO> lista = new ArrayList<>();

		return lista;
	}



}
