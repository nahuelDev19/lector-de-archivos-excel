package com.example.leerDatos.controllers;


import com.example.leerDatos.entitys.ResumenDto;
import com.example.leerDatos.services.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransaccionController {


    private ApplicationService applicationService;

    public TransaccionController(ApplicationService applicationService) {
        this.applicationService = applicationService;

    }

    //POST /upload → recibe el Excel, procesa y devuelve el resumen

    @PostMapping("/upload")
    public ResponseEntity<?> cargarArchivo(@RequestParam("archivo") MultipartFile file) {
       if (file==null){
           return ResponseEntity.badRequest().body("no llego el archivo");
       }else {
           System.out.println("antes de resumen dto");
           ResumenDto resumen = applicationService.procesarArchivo(file);
           return ResponseEntity.ok(resumen);
       }

    }

    @GetMapping("/total")
    public BigDecimal montoTotal() {
        //tiene que consultar la db y usar la clase de analisis
        return  applicationService.total();
    }

    @GetMapping("/por-categoria")
    public Map<String,BigDecimal> porCategoria() {
        //tiene que consultar la db y usar la clase de analisis
        return  applicationService.porCategoria();
    }

    @GetMapping("/monto-categoria/{categoria}")
    public BigDecimal montoPorCategoria(@PathVariable String categoria) {
        //tiene que consultar la db y usar la clase de analisis
        return applicationService.montoPorCategoria(categoria);
    }

    //GET /export → exporta todas las transacciones en Excel
    @GetMapping("/export")
    public byte[] exportarExcel() throws Exception {
        return applicationService.exportarExcel();
    }
}
