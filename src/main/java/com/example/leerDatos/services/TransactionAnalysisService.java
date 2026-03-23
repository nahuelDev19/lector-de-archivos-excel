package com.example.leerDatos.services;

import com.example.leerDatos.entitys.Transaccion;
import com.example.leerDatos.repository.TransaccionRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionAnalysisService {

    private DataAnalysisService dataAnalysisService;
    private TransaccionRepo transaccionRepo;

    public TransactionAnalysisService(DataAnalysisService dataAnalysisService, TransaccionRepo transaccionRepo) {
        this.dataAnalysisService = dataAnalysisService;
        this.transaccionRepo = transaccionRepo;
    }

    public BigDecimal total(){
        List<Transaccion> transaccions= transaccionRepo.findAll();
        BigDecimal total= dataAnalysisService.montoTotal(transaccions);
        return total;
    }



    public Map<String, BigDecimal> porCategoria(String categoria){

        List<Transaccion> transaccions= transaccionRepo.findByCategoria(categoria);
        Map<String, BigDecimal> categorias= new HashMap<>();

        for(Transaccion tran: transaccions) {
            String nombreCategoria = tran.getCategoria();
            BigDecimal monto = tran.getMonto();
            categorias.put(nombreCategoria, categorias.getOrDefault(nombreCategoria,BigDecimal.ZERO).add(monto));
        }
        return categorias;
    }



    public BigDecimal montoPorCategoria(String categoria){
        List<Transaccion> transaccions= transaccionRepo.findAll();
        return dataAnalysisService.filtrarMontoPorCategoria(transaccions,categoria);
    }

}
