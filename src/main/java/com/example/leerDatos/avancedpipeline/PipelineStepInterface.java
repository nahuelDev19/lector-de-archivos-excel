package com.example.leerDatos.avancedpipeline;

public interface PipelineStepInterface<I,O> {

   O excute(I input);

}
