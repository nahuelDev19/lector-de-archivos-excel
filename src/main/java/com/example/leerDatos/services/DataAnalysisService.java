package com.example.leerDatos.services;

import com.example.leerDatos.entitys.ResumenDto;
import com.example.leerDatos.entitys.Transaccion;

import java.util.*;

public class DataAnalysisService {


    private int montoTotal(List<Transaccion> transacciones){
        int totalMonto =0 ;
        for (Transaccion tran: transacciones){
            totalMonto += Integer.parseInt(tran.getMonto());
        }
        return  totalMonto;
    }
    private int cantidadClientes(List<Transaccion> transacciones){
        int total =0 ;
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

    private int montoMaximo(List<Transaccion> transacciones){
       int montoMax=0;
       for(Transaccion tran: transacciones){
           int monto= Integer.parseInt(tran.getMonto());
           if (monto>montoMax){
               montoMax= monto;
           }
       }
       return  montoMax;
    }



    private int montoMinimo(List<Transaccion> transacciones){
        int montoMin=0;
        for(Transaccion tran: transacciones){
            int monto= Integer.parseInt(tran.getMonto());
            if (monto<montoMin){
                montoMin= monto;
            }
        }
        return  montoMin;
    }


    public ResumenDto analizar(List<Transaccion> transacciones){
        ResumenDto newResumen= new ResumenDto(
                montoTotal(transacciones),
                cantidadClientes(transacciones),
                separarPorTipoMoneda(transacciones),
                montoMaximo(transacciones),
                montoMinimo(transacciones));
        return null;
    }

}
