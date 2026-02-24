package com.example.leerDatos.avancedpipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PipelineExecutor<I,O> {


    private final List<PipelineStepInterface <?,?> > steps= new ArrayList<>();

    public void addStep(PipelineStepInterface<?,?> step){
        steps.add(step);
    }

    @SuppressWarnings("unchecket")
    public O excute(I input){
        Object current= input;
        for ( PipelineStepInterface s: steps){
            current= s.excute(current);
        }
        return (O)current;
    }


}
