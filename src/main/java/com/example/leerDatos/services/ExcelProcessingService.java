package com.example.leerDatos.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.leerDatos.avancedpipeline.CleaningStep;
import com.example.leerDatos.avancedpipeline.NormalizadoStep;
import com.example.leerDatos.avancedpipeline.PipelineExecutor;
import com.example.leerDatos.avancedpipeline.TranformationStep;
import com.example.leerDatos.entitys.Transaccion;
import com.example.leerDatos.exception.MissingRequiredColumnsException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.leerDatos.entitys.TransaccionDTO;

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

			Row headerRow= sheet.getRow(0);
			if (headerRow==null){
				throw new MissingRequiredColumnsException("El documento debe contener los Encabezados");
			}

			validarColumna(headerRow);

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

		}
		catch (MissingRequiredColumnsException e) {
			throw e;
		}catch (Exception e) {
			throw new RuntimeException("Error al procesar archivo Excel", e);
		}

		return lista;
	}



	/*private void validarEncabezado(Row headerRow){
		List<String> esperados= List.of("fecha","cliente","monto","moneda","categoria","tipo");

		for(int i =0 ; i< esperados.size(); i++){
			Cell cel = headerRow.getCell(i);
			if (cel== null){
				throw new MissingRequiredColumnsException("Falta encabezado en columna " + i);
			}

			String valor= cel.toString().toLowerCase();
			if(!valor.equals(esperados.get(i))){
				throw new MissingRequiredColumnsException("Encabezado incorrecto en columna "+ i);
			}
		}
	}

	 */

	private void validarColumna(Row row){
		List<String> columnaObligatoria= List.of("fecha","cliente","monto","moneda","categoria","tipo");
		List<String> columnasExistentes= new ArrayList<>();
		List<String> faltante= new ArrayList<>();
		for (Cell cel:row){
			columnasExistentes.add(cel.getStringCellValue().trim().toLowerCase());
		}

		for (String col: columnaObligatoria){
			if (!columnasExistentes.contains(col)){
				faltante.add(col);
			}
		}

		if (!faltante.isEmpty()){
			throw new MissingRequiredColumnsException("no pueden faltar columnas: "+ faltante);
		}
	}


}