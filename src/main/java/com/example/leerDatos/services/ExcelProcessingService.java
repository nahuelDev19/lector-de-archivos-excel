package com.example.leerDatos.services;

import java.util.ArrayList;
import java.util.List;

import com.example.leerDatos.entitys.ResumenDto;
import com.example.leerDatos.entitys.Transaccion;
import org.springframework.web.multipart.MultipartFile;

import com.example.leerDatos.entitys.TransaccionDTO;

public class ExcelProcessingService {

	private DataAnalysisService dataAnalysisService;
	private  DataNormalizationService normalizationService;
	private  DataTransformationService transformationService;
	private DataCleaningService cleaningService;


	 public ExcelProcessingService(DataCleaningService cleaningService,
	DataNormalizationService normalizationService,
    DataTransformationService transformationService,
	DataAnalysisService dataAnalysisService){
		 
		this.cleaningService = cleaningService;
		this.normalizationService = normalizationService;
		this.transformationService = transformationService;
		this.dataAnalysisService = dataAnalysisService;
	}

	public ResumenDto procesarExcel(MultipartFile file) {
		List<TransaccionDTO> datos = leerExcel(file);
		//definir un metodo pipeline que itere todos los procesos que deben atravezar los datos

		return	dataAnalysisService.analizar(miPipeline(datos));

	}

	private List<TransaccionDTO> leerExcel(MultipartFile file) {
		List<TransaccionDTO> lista = new ArrayList<>();

		return lista;
	}


	//mio
	private List<Transaccion> miPipeline(List<TransaccionDTO> datos){
		 List<Transaccion> transacciones= new ArrayList<>();
		 for (TransaccionDTO dto: datos){
		datos = cleaningService.limpiar(datos);
		datos = normalizationService.normalizar(datos);
		transacciones = transformationService.transformar(datos);
		 }

        return transacciones;
    }


}
