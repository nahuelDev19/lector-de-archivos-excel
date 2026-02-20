package com.example.leerDatos.services;

import com.example.leerDatos.entitys.ResumenDto;
import com.example.leerDatos.entitys.Transaccion;

import java.math.BigDecimal;
import java.util.*;

public class DataAnalysisService {



    private BigDecimal montoTotal(List<Transaccion> transacciones){
        BigDecimal totalMonto= BigDecimal.ZERO;
        for (Transaccion tran: transacciones){
             totalMonto =totalMonto.add( tran.getMonto());
        }
        return  totalMonto;
    }
    private int cantidadClientes(List<Transaccion> transacciones){
        int total = 0;
        for (Transaccion tran: transacciones){
            total += Integer.parseInt(tran.getCliente());
        }
        return  total;
    }

    private Set<String> separarPorTipoMoneda(List<Transaccion> transacciones){
        Set<String> tipoMoneda= new HashSet<>();
        for (Transaccion tran: transacciones){
            tipoMoneda.add(tran.getMoneda());
        }
        return  tipoMoneda;
    }

    private BigDecimal montoMaximo(List<Transaccion> transacciones){
        BigDecimal montoMax= transacciones.get(0).getMonto();
       for(Transaccion tran: transacciones){
           BigDecimal monto= tran.getMonto();
           if (monto.compareTo(montoMax)>0){
               montoMax=  monto;
           }
       }
       return  montoMax;
    }



    private BigDecimal montoMinimo(List<Transaccion> transacciones){
        BigDecimal montoMin= transacciones.get(0).getMonto();
        for(Transaccion tran: transacciones){
            BigDecimal monto= tran.getMonto();
            if (monto.compareTo(montoMin)>0){
                montoMin= monto;
            }
        }
        return  montoMin;
    }

    private Set<String>  porTipo(List<Transaccion> transacciones){
        Set<String> tipo= new HashSet<>();
        for (Transaccion tran: transacciones){
            tipo.add(tran.getTipo());
        }
        return  tipo;
    }

    private Set<String>  porCategoria(List<Transaccion> transacciones){
        Set<String> categoria= new HashSet<>();
        for (Transaccion tran: transacciones){
            categoria.add(tran.getCategoria());
        }
        return  categoria;
    }


    public ResumenDto analizar(List<Transaccion> transacciones){
        return new ResumenDto(
                montoTotal(transacciones),
                cantidadClientes(transacciones),
                separarPorTipoMoneda(transacciones),
                montoMaximo(transacciones),
                montoMinimo(transacciones),
                porTipo(transacciones),
                porCategoria(transacciones));

    }

}
