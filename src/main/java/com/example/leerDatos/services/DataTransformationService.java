package com.example.leerDatos.services;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.leerDatos.entitys.Transaccion;
import com.example.leerDatos.entitys.TransaccionDTO;
import com.example.leerDatos.exception.InvalidTransactionDataException;
import org.springframework.stereotype.Service;

@Service
public class DataTransformationService {

    private List<Transaccion> lista= new ArrayList<>();

    DateTimeFormatter input= DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public List<Transaccion> transformar(List<TransaccionDTO> datos) {

        for (TransaccionDTO dto : datos) {


            validarDatosCorrectos(dto);
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


    private void validarDatosCorrectos(TransaccionDTO dto){
        List<String> errores= new ArrayList<>();
        BigDecimal monto= null;

        try {
            monto= new BigDecimal(dto.getMonto());
        }catch (NumberFormatException e){
            errores.add("el monto debe contener valoress numericos");
        }
        if(!dto.getTipo().equalsIgnoreCase("ingreso") && ! dto.getTipo().equalsIgnoreCase("egreso")){
            errores.add("El campo Tipo debe contener solo EGRESO e INGRESO");
        }
        if(!dto.getMoneda().equalsIgnoreCase("ARS") &&
                !dto.getMoneda().equalsIgnoreCase("EUR") &&
                !dto.getMoneda().equalsIgnoreCase("USD")
        ){
            errores.add("El campo Moneda debe Contener solo ARS, EUR, USD");
        }

        List<String> listCat= List.of("tecnologia","alimentos","transporte","servicios");
        if(!listCat.contains(dto.getCategoria().toLowerCase())){
            errores.add("La categoría no es válida");
        }

        if (!errores.isEmpty()){
            throw new InvalidTransactionDataException(String.join(",",errores));
        }
    }

}
