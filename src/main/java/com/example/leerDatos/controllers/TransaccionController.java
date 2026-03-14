package com.example.leerDatos.controllers;


import com.example.leerDatos.entitys.ResumenDto;
import com.example.leerDatos.services.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Map;

@Tag(name = "Transacciones", description = "controller de transacciones")
@RestController
@RequestMapping("/api/transactions")
public class TransaccionController {


    private ApplicationService applicationService;

    public TransaccionController(ApplicationService applicationService) {
        this.applicationService = applicationService;

    }

    //POST /upload → recibe el Excel, procesa y devuelve el resumen

    @Operation(
            summary = "Subir archivo Excel",
            description = "Carga un archivo XLSX y devuelve un resumen"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Archivo procesado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResumenDto.class)
            )
    )
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
    @Operation(
            summary = "obtener monto total",
            description = "devuelve la suma total de todas las transferencias"
    )
    @ApiResponse(
            responseCode = "200",
            description = "monto total calculado currectamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "number", example = "15000.50")
            )
    )
    @GetMapping("/total")
    public BigDecimal montoTotal() {
        return  applicationService.total();
    }

    @Operation(
            summary = "obtener monto total por categoria",
            description = "devuelve un map con categoria y monto"
    )
    @ApiResponse(
            responseCode = "200",
            description = "montos agrupados por categorias"

    )
    @GetMapping("/por-categoria/{categoria}")
    public Map<String,BigDecimal> porCategoria(@PathVariable String categoria) {
        return  applicationService.porCategoria(categoria);
    }

    @Operation(
            summary = "Obtener monto por categoría",
            description = "Devuelve el monto total correspondiente a una categoría específica"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Monto calculado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "number", example = "1500.00")
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Categoría no encontrada"
    )
    @GetMapping("/monto-categoria/{categoria}")
    public BigDecimal montoPorCategoria(@PathVariable String categoria) {
        //tiene que consultar la db y usar la clase de analisis
        return applicationService.montoPorCategoria(categoria);
    }

    //GET /export → exporta todas las transacciones en Excel
    @Operation(
            summary = "Exportar datos a Excel",
            description = "Genera y descarga un archivo Excel con las transacciones"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Archivo Excel generado correctamente"
            )

    @GetMapping("/export")
    public byte[] exportarExcel() throws Exception {
        return applicationService.exportarExcel();
    }
}
