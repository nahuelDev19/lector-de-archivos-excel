package com.example.leerDatos.services;

import com.example.leerDatos.entitys.Resumen;
import com.example.leerDatos.entitys.ResumenDto;
import com.example.leerDatos.entitys.Transaccion;
import com.example.leerDatos.entitys.TransaccionDTO;
import com.example.leerDatos.repository.ResumenRepo;
import com.example.leerDatos.repository.TransaccionRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Service
public class ApplicationService {


    private ExcelProcessingService excelProcessingService;
    private TransaccionRepo transaccionRepo;
    private DataAnalysisService dataAnalysisService;
    private ResumenRepo resumenRepo;


    public ApplicationService(ExcelProcessingService excelProcessingService, TransaccionRepo transaccionRepo, DataAnalysisService dataAnalysisService,ResumenRepo resumenRepo) {
        this.excelProcessingService = excelProcessingService;
        this.transaccionRepo = transaccionRepo;
        this.dataAnalysisService = dataAnalysisService;
        this.resumenRepo= resumenRepo;
    }

    public void lecturaArchivo(MultipartFile file) {
        List<TransaccionDTO> listTransaccionDto = excelProcessingService.leerExcel(file);
        List<Transaccion> procesado = excelProcessingService.ejecutarPipeline(listTransaccionDto);
        creacionEntidadesJpaTransaccion(procesado);
        ResumenDto resumen =  dataAnalysisService.analizar(procesado);
        creacionEntidadesJpaResumen(resumen);

    }

    private void creacionEntidadesJpaTransaccion(List<Transaccion> transaccions) {
        for (Transaccion tran : transaccions) {
            Transaccion transaccion = new Transaccion(
                    tran.getFecha(),
                    tran.getCliente(),
                    tran.getMonto(),
                    tran.getMoneda(),
                    tran.getTipo(),
                    tran.getCategoria()
            );
            transaccionRepo.save(transaccion);
        }
    }
    private void creacionEntidadesJpaResumen(ResumenDto resumen) {
       Resumen resumenEntity= new Resumen(
               resumen.getMontoTotal(),
               resumen.getCantidadClientes(),
               resumen.getSepararPorTipoMoneda(),
               resumen.getMontoMaximo(),
               resumen.getMontoMinimo(),
               resumen.getTipo(),
               resumen.getCategoria()
       );
       resumenRepo.save(resumenEntity);
    }


}
