package com.example.leerDatos.controllers;

import java.util.Collections;

import com.example.leerDatos.entitys.ResumenDto;
import com.example.leerDatos.entitys.Transaccion;
import com.example.leerDatos.entitys.TransaccionDTO;
import com.example.leerDatos.exception.*;
import com.example.leerDatos.services.FileProcessingService;
import com.example.leerDatos.services.TransactionAnalysisService;
import com.example.leerDatos.services.TransactionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Transacciones", description = "controller de transacciones")
@RestController
@RequestMapping("/api/transactions")
public class TransaccionController {



    private TransactionAnalysisService transactionAnalysisService;
    private FileProcessingService fileProcessingService;
    private TransactionsService transactionsService;

    public TransaccionController( TransactionAnalysisService transactionAnalysisService,FileProcessingService fileProcessingService, TransactionsService transactionsService) {
        this.transactionAnalysisService= transactionAnalysisService;
        this.fileProcessingService= fileProcessingService;
        this.transactionsService = transactionsService;
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
    public ResponseEntity<?> cargarArchivo(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
       if (file==null || file.isEmpty()){
           throw new InvalidFileFormatException("Archivo vacio");
       }
       String nombre= file.getOriginalFilename();
       if(nombre==null || (!nombre.endsWith(".xls") && !nombre.endsWith(".xlsx"))){
           throw new InvalidFileFormatException("Formato de Archivo no valido, Formatos validos xlsx o xls");
       }

       try{
           ResumenDto resumen = fileProcessingService.procesarArchivo(file);
           return ResponseEntity.ok(resumen);
       }catch (DatabaseOperationException e){
           return new GlobalExceptionHandler().handleDatabaseOperationException(e, request);
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
        return  transactionAnalysisService.total();
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
        return  transactionAnalysisService.porCategoria(categoria);
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
        return transactionAnalysisService.montoPorCategoria(categoria);
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
    public ResponseEntity<byte[]>exportarExcel() throws Exception {

        byte[] archivo= fileProcessingService.exportarExcel();
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=transacciones-totales.xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(archivo);
    }

    //<--------------------------------CRUD-------------------------------->


    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> findById(@PathVariable UUID id){

        Transaccion transaccion= transactionsService.findById(id);
            return ResponseEntity.ok(transaccion);

    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody TransaccionDTO dto, BindingResult result){

        if (result.hasErrors()){
           return new GlobalExceptionHandler().handleMissingCollumException(new Exception(),generateMessagesErrror(result));
        }

        TransaccionDTO transaccionDTO= transactionsService.create(dto);
        return ResponseEntity.ok(transaccionDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id,
                                    @Valid @RequestBody TransaccionDTO dto,
                                    BindingResult result) {
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(generateMessagesErrror(result));
        }
        TransaccionDTO updated = transactionsService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        transactionsService.delete(id);
        return ResponseEntity.noContent().build();
    }




    private List<String> generateMessagesErrror(BindingResult result){
        if (result.hasErrors()){
            return result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }






}
/*
✓ ? Archivo no enviado o vacío
✓Formato inválido (no es .xls / .xlsx)
✓ Archivo sin fila de encabezados
✓ Faltan columnas obligatorias (fecha, descripción, monto, categoría)
->  ? Datos inválidos dentro del Excel (montos incorrectos, celdas vacías críticas, etc.)
-> ? Error al leer el archivo (fallo técnico)
-> ? Error al guardar el resumen en base de datos
 */