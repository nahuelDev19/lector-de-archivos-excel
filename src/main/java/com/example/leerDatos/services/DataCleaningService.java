package com.example.leerDatos.services;

import java.util.List;
import java.util.stream.Collectors;

import com.example.leerDatos.entitys.TransaccionDTO;
import org.springframework.stereotype.Service;

@Service
public class DataCleaningService {

    public List<TransaccionDTO> limpiar(List<TransaccionDTO> datos){
        return  datos.stream().filter(data -> data.getCliente() != null && !data.getCliente().isBlank())
                .filter(data -> data.getMonto() != null  &&  !data.getMonto().isBlank())
                .filter(data -> data.getMoneda() != null  && !data.getMonto().isBlank())
                // agregamos tipo y categoria
                .filter(data -> data.getTipo() != null && !data.getTipo().isBlank())
                .filter(data -> data.getCategoria() != null && !data.getCategoria().isBlank())
                .collect(Collectors.toList());

    }

}
