package com.example.leerDatos;

import com.example.leerDatos.entitys.Transaccion;
import com.example.leerDatos.entitys.TransaccionDTO;
import com.example.leerDatos.services.ExcelProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
public class AnilisisTransaccionController {
	
	@Autowired
	private ExcelProcessingService excelProcessingService;
	
	
	@PostMapping("leer/excel")
	public ResponseEntity<?> cargarArchivo(@RequestParam MultipartFile file){
		//analisisTransaccionServiceImp.procesarArchivo(file);
		List<TransaccionDTO> transaccionDto= excelProcessingService.leerExcel(file);
		List<Transaccion> transaccions = excelProcessingService.ejecutarPipeline(transaccionDto);


		return ResponseEntity.ok(null);
	}

}
