package com.example.leerDatos.services;

import com.example.leerDatos.entitys.Resumen;
import com.example.leerDatos.entitys.ResumenDto;
import com.example.leerDatos.entitys.Transaccion;
import com.example.leerDatos.entitys.TransaccionDTO;
import com.example.leerDatos.exception.DatabaseOperationException;
import com.example.leerDatos.repository.ResumenRepo;
import com.example.leerDatos.repository.TransaccionRepo;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileProcessingService {


    private ExcelProcessingService excelProcessingService;
    private DataAnalysisService dataAnalysisService;
    private TransaccionRepo transaccionRepo;
    private ResumenRepo resumenRepo;
    private ExcelExportService excelExportService;

    public FileProcessingService(ExcelProcessingService excelProcessingService, DataAnalysisService dataAnalysisService, TransaccionRepo transaccionRepo, ResumenRepo resumenRepo,ExcelExportService excelExportService) {
        this.excelProcessingService = excelProcessingService;
        this.dataAnalysisService = dataAnalysisService;
        this.transaccionRepo = transaccionRepo;
        this.resumenRepo = resumenRepo;
        this.excelExportService = excelExportService;
    }

    public ResumenDto procesarArchivo(MultipartFile file) {
        List<TransaccionDTO> listTransaccionDto = excelProcessingService.leerExcel(file);
        List<Transaccion> procesado = excelProcessingService.ejecutarPipeline(listTransaccionDto);
        creacionEntidadesJpaTransaccion(procesado);
        ResumenDto resumen =  dataAnalysisService.analizar(procesado);
        creacionEntidadesJpaResumen(resumen);

        return resumen;
    }

    private void creacionEntidadesJpaTransaccion(List<Transaccion> transaccions) {
        for (Transaccion tran : transaccions) {
            Transaccion transaccion = new Transaccion(
                    tran.getFecha(),
                    tran.getCliente(),
                    tran.getMonto(),
                    tran.getMoneda(),
                    tran.getTipo(),
                    tran.getCategoria(),
                    tran.getPeriodo()
            );
            transaccionRepo.save(transaccion);
        }
    }

    @Transactional
    public void creacionEntidadesJpaResumen(ResumenDto resumen) {
        try {
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
        }catch (DataAccessException e){
            throw new DatabaseOperationException("Error en la base de datos al guardar Resumen",e);
        }
    }


    public byte[] exportarExcel() throws Exception {
        List<Transaccion> transaccions= transaccionRepo.findAll();
        return excelExportService.exportarTransacciones(transaccions);
    }

}
