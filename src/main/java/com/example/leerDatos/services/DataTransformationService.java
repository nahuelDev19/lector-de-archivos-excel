package com.example.leerDatos.services;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.leerDatos.entitys.Transaccion;
import com.example.leerDatos.entitys.TransaccionDTO;
import org.springframework.stereotype.Service;

@Service
public class DataTransformationService {

    private List<Transaccion> lista= new ArrayList<>();

    DateTimeFormatter input= DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public List<Transaccion> transformar(List<TransaccionDTO> datos) {
        for (TransaccionDTO dto : datos) {

            LocalDate fecha= LocalDate.parse(dto.getFecha(), input);
            DateTimeFormatter fechaSalida= DateTimeFormatter.ofPattern("yyyy-MM");
            String periodo= fecha.format(fechaSalida);
            Transaccion transaccion= new Transaccion(
                    fecha,
                    dto.getCliente(),
                    new BigDecimal(dto.getMonto()),
                    dto.getMoneda(),
                    dto.getTipo(),
                    dto.getCategoria(),
                    periodo

            );

        lista.add(transaccion);
        }
        return lista;
    }

}
