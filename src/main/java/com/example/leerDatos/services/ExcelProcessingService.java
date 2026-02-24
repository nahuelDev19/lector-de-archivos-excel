package com.example.leerDatos.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.leerDatos.entitys.ResumenDto;
import com.example.leerDatos.entitys.Transaccion;
import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.leerDatos.entitys.TransaccionDTO;
import org.apache.poi.ss.usermodel.*;
import java.time.LocalDate;
import java.time.ZoneId;

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

	public List<Transaccion> ejecutarPipeline(List<TransaccionDTO> datos){
		 //limpieza
		List<TransaccionDTO> datosLimpios= cleaningService.limpiar(datos);
		List<TransaccionDTO> normalizado= normalizationService.normalizar(datosLimpios);
		return transformationService.transformar(normalizado);

	}

	public List<TransaccionDTO> leerExcel(MultipartFile file) {
		 //mi codigo
		List<TransaccionDTO> lista = new ArrayList<>();
		TransaccionDTO transaccionDTO= new TransaccionDTO();

		 try(InputStream is = file.getInputStream();
			 Workbook workbook= new XSSFWorkbook(is);) {
			 Sheet sheet = workbook.getSheetAt(0);
			 for (Row row : sheet) {
				TransaccionDTO dto= new TransaccionDTO();
				dto.setFecha(obtenerValorCelda(row.getCell(0)));
				dto.setCliente(obtenerValorCelda(row.getCell(1)));
				dto.setMonto(obtenerValorCelda(row.getCell(2)));
				dto.setMoneda(obtenerValorCelda(row.getCell(3)));
				dto.setCategoria(obtenerValorCelda(row.getCell(4)));
				dto.setTipo(obtenerValorCelda(row.getCell(5)));
				lista.add(dto);
			 }

		 }catch (Exception e){
			 e.printStackTrace();
		 }

		return lista;
	}




	private String obtenerValorCelda(Cell cell) {

		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {

			case STRING:
				return cell.getStringCellValue().trim();

			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					LocalDate fecha = cell.getDateCellValue()
							.toInstant()
							.atZone(ZoneId.systemDefault())
							.toLocalDate();
					return fecha.toString(); // formato ISO yyyy-MM-dd
				} else {
					double valor = cell.getNumericCellValue();

					// Evita que 1500.0 salga como "1500.0"
					if (valor == (long) valor) {
						return String.valueOf((long) valor);
					}

					return String.valueOf(valor);
				}

			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());

			case FORMULA:
				return cell.getCellFormula();
			// Alternativa profesional: evaluar fórmula con FormulaEvaluator

			case BLANK:
				return "";

			default:
				return "";
		}
	}

}
