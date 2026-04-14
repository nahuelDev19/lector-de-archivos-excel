package com.example.leerDatos.services;

import com.example.leerDatos.repository.ResumenRepo;
import com.example.leerDatos.repository.TransaccionRepo;

import org.springframework.stereotype.Service;


@Service
public class ApplicationService {


    private ExcelProcessingService excelProcessingService;
    private DataAnalysisService dataAnalysisService;
    private ExcelExportService excelExportService;
    private TransaccionRepo transaccionRepo;
    private ResumenRepo resumenRepo;

    public ApplicationService(ExcelProcessingService excelProcessingService,
                              TransaccionRepo transaccionRepo,
                              DataAnalysisService dataAnalysisService,
                              ResumenRepo resumenRepo,
                              ExcelExportService excelExportService) {
        this.excelProcessingService = excelProcessingService;
        this.transaccionRepo = transaccionRepo;
        this.dataAnalysisService = dataAnalysisService;
        this.resumenRepo= resumenRepo;
        this.excelExportService=excelExportService;
    }





}

// elao un documento de texto que documente la funcionalidades de la api segun entrada tarea y salida