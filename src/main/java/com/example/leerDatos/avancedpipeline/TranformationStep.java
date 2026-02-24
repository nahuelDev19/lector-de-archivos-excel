package com.example.leerDatos.avancedpipeline;

import com.example.leerDatos.entitys.Transaccion;
import com.example.leerDatos.entitys.TransaccionDTO;
import com.example.leerDatos.services.DataTransformationService;

import java.util.List;

public class TranformationStep implements PipelineStepInterface<List<TransaccionDTO>,List<Transaccion>>{

    private final DataTransformationService transformationService;

    public TranformationStep(DataTransformationService transformationService) {
        this.transformationService = transformationService;
    }

    @Override
    public List<Transaccion> excute(List<TransaccionDTO> input) {
        return transformationService.transformar(input);
    }
}
