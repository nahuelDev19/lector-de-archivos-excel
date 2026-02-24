package com.example.leerDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.plaf.multi.MultiPopupMenuUI;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;

@Service
public class AnalisisTransaccionServiceImp implements AnalisisTransaccionService {

	@Override
	public void procesarArchivo(MultipartFile file) {
		String name = file.getOriginalFilename();
		if (name.endsWith(".txt")) {
			procesarArchivoTxt(file);
		} else if (name.endsWith(".xlsx")) {
			leerArchivoExcel(file);
		} else if (name.endsWith(".sql")) {
			procesarArchivoSql(file);
		} else {
			new RuntimeException("formato de archivo incorrecto");
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void procesarArchivoTxt(MultipartFile file) {

	}

	@Override
	public void leerArchivoExcel(MultipartFile file) {

		try (	InputStream is = file.getInputStream();
				Workbook workbook = new XSSFWorkbook(is);) {

			CellStyle estiloFecha= workbook.createCellStyle();
			estiloFecha.setDataFormat(workbook.createDataFormat().getFormat("dd/MM/yyyy"));
			Sheet sheet = workbook.getSheetAt(0);
			
			limpiezaDatosExcel(sheet);
			for (Row row : sheet) {
				for (Cell cell : row) {
					normalizadorPorTipo(cell, estiloFecha);
					System.out.print(cell.toString() + "\t");
				}
				System.out.println(); // salto de línea por fila
			}

			try {
				FileOutputStream excelModificado = new FileOutputStream("pacientesModificados.xlsx");				
				workbook.write(excelModificado);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			crearPacientes();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void procesarArchivoSql(MultipartFile file) {
		// TODO Auto-generated method stub

	}
	
	public static void limpiezaDatosExcel(Sheet sheet) {
		eliminarColumnasVacias(sheet);
		eliminarFilasVacias(sheet);
		//eliminarFilasConCeldasVacias(sheet);
		eliminarFilasConUnSoloValor(sheet);
	}

	public static void eliminarColumnasVacias(Sheet sheet) {
		

		Row header = sheet.getRow(0);

		int totalColumnas = header.getLastCellNum();

		for (int col = totalColumnas - 1; col >= 0; col--) {

		    boolean columnaVacia = true;

		    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
		        Row row = sheet.getRow(i);
		        if (row == null) continue;

		        Cell cell = row.getCell(col);
		        if (cell != null && cell.getCellType() != CellType.BLANK) {
		            columnaVacia = false;
		            break;
		        }
		    }

		    if (columnaVacia) {

		    	for (Row row : sheet) {

		    	    for (int c = col; c < totalColumnas - 1; c++) {

		    	        Cell derecha = row.getCell(c + 1);

		    	        // 1️⃣ borrar la celda actual
		    	        Cell actual = row.getCell(c);
		    	        if (actual != null) {
		    	            row.removeCell(actual);
		    	        }

		    	        // 2️⃣ copiar la de la derecha
		    	        if (derecha != null) {
		    	            Cell nueva = row.createCell(c, derecha.getCellType());
		    	            nueva.setCellValue(derecha.toString());
		    	        }
		    	    }

		    	    // 3️⃣ borrar última columna
		    	    Cell ultima = row.getCell(totalColumnas - 1);
		    	    if (ultima != null) {
		    	        row.removeCell(ultima);
		    	    }
		    	}


		        totalColumnas--;
		    }
		}


		

	
	}
	
	public static void normalizadorPorTipo(Cell celda, CellStyle estiloFecha) {
		Pattern namePattern = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
		if (celda.getCellType() == CellType.STRING) {
			String valor = celda.getStringCellValue().trim();
			if (namePattern.matcher(valor).matches()) {
				celda.setCellValue(valor.toUpperCase());
			}
		}
		if(celda.getCellType()== CellType.NUMERIC){
			if (DateUtil.isCellDateFormatted(celda)) {
				celda.setCellStyle(estiloFecha);
				
			}else {
				double valor= celda.getNumericCellValue();
				celda.setCellValue((int) valor);
			}
		}
		
	}



	public static void eliminarFilasVacias(Sheet sheet) {

	    for (int i = sheet.getLastRowNum(); i > 0; i--) { // salta cabecera
	        Row row = sheet.getRow(i);

	        if (row == null) {
	            sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
	            continue;
	        }

	        if (filaVacia(row)) {
	            sheet.removeRow(row);
	            sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
	        }
	    }
	}
	
	


	public static void eliminarFilasConUnSoloValor(Sheet sheet) {

	    for (int i = sheet.getLastRowNum(); i >= sheet.getFirstRowNum(); i--) {
	        Row row = sheet.getRow(i);

	        if (tieneUnSoloValor(row)) {
	            sheet.removeRow(row);

	            // reordenar filas
	            if (i < sheet.getLastRowNum()) {
	                sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
	            }
	        }
	    }
	}

	private static boolean tieneUnSoloValor(Row row) {
	    if (row == null) return false;

	    int contador = 0;

	    for (Cell cell : row) {
	        if (cell != null && cell.getCellType() != CellType.BLANK) {
	            contador++;
	            if (contador > 1) {
	                return false; // ya tiene más de un valor
	            }
	        }
	    }
	    return contador == 1;
	}


	
	private static boolean filaVacia(Row row) {
	    if (row == null) return true;

	    for (Cell cell : row) {
	        if (cell != null && cell.getCellType() != CellType.BLANK) {
	            return false;
	        }
	    }
	    return true;
	}
	
	/*private boolean tieneAlgunaCeldaVacia(Row row, int totalColumnas) {
    if (row == null) return true; // fila inexistente = vacía

    for (int i = 0; i < totalColumnas; i++) {
        Cell cell = row.getCell(i);

        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return true;
        }
    }
    return false;
}
*/
	
	public static void crearPacientes(){
		File file = new File("pacientesModificados.xlsx");
		
		PoijiOptions poi= PoijiOptions.PoijiOptionsBuilder
										.settings()
										.sheetIndex(0)
										.build();
		
		List<Paciente> listado= Poiji.fromExcel(file, Paciente.class, poi);
		
		listado.forEach(System.out::print);
		
		
	}


}
