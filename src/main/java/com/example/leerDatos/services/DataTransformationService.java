package com.example.leerDatos.services;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.leerDatos.entitys.Transaccion;
import com.example.leerDatos.entitys.TransaccionDTO;
import org.springframework.stereotype.Service;

@Service
public class DataTransformationService {

    private List<Transaccion> lista= new ArrayList<>();
	
	public List<Transaccion> transformar(List<TransaccionDTO> datos) {
        for (TransaccionDTO dto : datos) {

            //no estoy usando variable monto;
           // BigDecimal monto= dto.getMonto();
            Transaccion transaccion= new Transaccion(dto.getFecha(),
                    dto.getCliente(),
                    dto.getMonto(),
                    dto.getMoneda(),
                    dto.getTipo(),
                    dto.getCategoria()

            );

        lista.add(transaccion);
        }
        return lista;
    }

}
