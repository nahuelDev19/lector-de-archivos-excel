package com.example.leerDatos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class AnilisisTransaccionController {
	
	@Autowired
	private AnalisisTransaccionServiceImp analisisTransaccionServiceImp;
	
	
	@PostMapping("leer/excel")
	public ResponseEntity<?> cargarArchivo(@RequestParam MultipartFile file){
		analisisTransaccionServiceImp.procesarArchivo(file);
		return ResponseEntity.ok(null);
	}

}
