package com.example.leerDatos;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface AnalisisTransaccionService {
	
	void procesarArchivo(MultipartFile file);
	void procesarArchivoTxt(MultipartFile file);
	void leerArchivoExcel(MultipartFile file);
	void procesarArchivoSql(MultipartFile file);

}
