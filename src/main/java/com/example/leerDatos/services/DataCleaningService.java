package com.example.leerDatos.services;

import java.util.List;
import java.util.stream.Collectors;

import com.example.leerDatos.entitys.TransaccionDTO;

public class DataCleaningService {

    public List<TransaccionDTO> limpiar(List<TransaccionDTO> datos){
        return  datos.stream().filter(data -> data.getCliente() != null && !data.getCliente().isBlank())
                .filter(data -> data.getMonto() != null && !data.getMonto().isBlank())
                .filter(data -> data.getMoneda() != null && !data.getMonto().isBlank())
                .collect(Collectors.toList());

    }

}
