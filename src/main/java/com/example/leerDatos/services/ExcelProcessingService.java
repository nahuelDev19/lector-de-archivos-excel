package com.example.leerDatos.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.leerDatos.avancedpipeline.CleaningStep;
import com.example.leerDatos.avancedpipeline.NormalizadoStep;
import com.example.leerDatos.avancedpipeline.PipelineExecutor;
import com.example.leerDatos.avancedpipeline.TranformationStep;
import com.example.leerDatos.entitys.ResumenDto;
import com.example.leerDatos.entitys.Transaccion;
import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.leerDatos.entitys.TransaccionDTO;
import org.apache.poi.ss.usermodel.*;
import java.time.LocalDate;
import java.time.ZoneId;

import static com.example.leerDatos.services.DataAnalysisService.obtenerValorCelda;

@Service
public class ExcelProcessingService {

	private DataAnalysisService dataAnalysisService;
	private DataNormalizationService normalizationService;
	private DataTransformationService transformationService;
	private DataCleaningService cleaningService;


	public ExcelProcessingService(DataCleaningService cleaningService,
								  DataNormalizationService normalizationService,
								  DataTransformationService transformationService,
								  DataAnalysisService dataAnalysisService) {

		this.cleaningService = cleaningService;
		this.normalizationService = normalizationService;
		this.transformationService = transformationService;
		this.dataAnalysisService = dataAnalysisService;
	}

	public List<Transaccion> ejecutarPipeline(List<TransaccionDTO> datos) {

		//pipeline automatizado por lote
		PipelineExecutor<List<TransaccionDTO>, List<Transaccion>> executor= new PipelineExecutor<>();
		executor.addStep(new CleaningStep(cleaningService));
		executor.addStep(new NormalizadoStep(normalizationService));
		executor.addStep(new TranformationStep(transformationService));


		//pipelline fracmentado
		//List<TransaccionDTO> datosLimpios = cleaningService.limpiar(datos);
		//List<TransaccionDTO> normalizado = normalizationService.normalizar(datosLimpios);
		//return transformationService.transformar(normalizado);
		return executor.excute(datos);
	}

	public List<TransaccionDTO> leerExcel(MultipartFile file) {
		//mi codigo
		List<TransaccionDTO> lista = new ArrayList<>();
		TransaccionDTO transaccionDTO = new TransaccionDTO();

		try (InputStream is = file.getInputStream();
			 Workbook workbook = new XSSFWorkbook(is);) {
			Sheet sheet = workbook.getSheetAt(0);

			boolean primeraFila= true;
			for (Row row : sheet) {
				if (primeraFila){
					primeraFila= false;
					continue;
				}

				TransaccionDTO dto = new TransaccionDTO();
				dto.setFecha(obtenerValorCelda(row.getCell(0)));
				dto.setCliente(obtenerValorCelda(row.getCell(1)));
				dto.setMonto(obtenerValorCelda(row.getCell(2)));
				dto.setMoneda(obtenerValorCelda(row.getCell(3)));
				dto.setCategoria(obtenerValorCelda(row.getCell(4)));
				dto.setTipo(obtenerValorCelda(row.getCell(5)));
				lista.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}





}