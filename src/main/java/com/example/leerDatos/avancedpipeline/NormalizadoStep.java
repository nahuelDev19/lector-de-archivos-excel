package com.example.leerDatos.avancedpipeline;

import com.example.leerDatos.entitys.TransaccionDTO;
import com.example.leerDatos.services.DataNormalizationService;

import java.util.List;

public class NormalizadoStep implements PipelineStepInterface<List<TransaccionDTO>, List<TransaccionDTO>>{

    private DataNormalizationService dataNormalizationService;

    public NormalizadoStep(DataNormalizationService dataNormalizationService) {
        this.dataNormalizationService = dataNormalizationService;
    }

    @Override
    public List<TransaccionDTO> excute(List<TransaccionDTO> input) {
        return dataNormalizationService.normalizar(input);
    }
}
