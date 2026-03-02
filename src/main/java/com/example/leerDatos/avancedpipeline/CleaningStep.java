package com.example.leerDatos.avancedpipeline;

import com.example.leerDatos.entitys.TransaccionDTO;
import com.example.leerDatos.services.DataCleaningService;

import java.util.List;

public class CleaningStep implements PipelineStepInterface<List<TransaccionDTO>, List<TransaccionDTO>>{

    private DataCleaningService dataCleaningService;

    public CleaningStep(DataCleaningService dataCleaningService) {
        this.dataCleaningService = dataCleaningService;
    }

    @Override
    public List<TransaccionDTO> excute(List<TransaccionDTO> input) {
        return dataCleaningService.limpiar(input);
    }
}
