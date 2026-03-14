package com.example.leerDatos.services;

import com.example.leerDatos.entitys.Resumen;
import com.example.leerDatos.entitys.ResumenDto;
import com.example.leerDatos.entitys.Transaccion;
import com.example.leerDatos.entitys.TransaccionDTO;
import com.example.leerDatos.repository.ResumenRepo;
import com.example.leerDatos.repository.TransaccionRepo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ApplicationService {


    private ExcelProcessingService excelProcessingService;
    private DataAnalysisService dataAnalysisService;
    private ExcelExportService excelExportService;
    private TransaccionRepo transaccionRepo;
    private ResumenRepo resumenRepo;

    public ApplicationService(ExcelProcessingService excelProcessingService,
                              TransaccionRepo transaccionRepo,
                              DataAnalysisService dataAnalysisService,
                              ResumenRepo resumenRepo,
                              ExcelExportService excelExportService) {
        this.excelProcessingService = excelProcessingService;
        this.transaccionRepo = transaccionRepo;
        this.dataAnalysisService = dataAnalysisService;
        this.resumenRepo= resumenRepo;
        this.excelExportService=excelExportService;
    }

    public ResumenDto procesarArchivo(MultipartFile file) {
        List<TransaccionDTO> listTransaccionDto = excelProcessingService.leerExcel(file);
        List<Transaccion> procesado = excelProcessingService.ejecutarPipeline(listTransaccionDto);
        creacionEntidadesJpaTransaccion(procesado);
        ResumenDto resumen =  dataAnalysisService.analizar(procesado);
        creacionEntidadesJpaResumen(resumen);

        return resumen;
    }

    private void creacionEntidadesJpaTransaccion(List<Transaccion> transaccions) {
        for (Transaccion tran : transaccions) {
            Transaccion transaccion = new Transaccion(
                    tran.getFecha(),
                    tran.getCliente(),
                    tran.getMonto(),
                    tran.getMoneda(),
                    tran.getTipo(),
                    tran.getCategoria()
            );
            transaccionRepo.save(transaccion);
        }
    }
    private void creacionEntidadesJpaResumen(ResumenDto resumen) {
       Resumen resumenEntity= new Resumen(
               resumen.getMontoTotal(),
               resumen.getCantidadClientes(),
               resumen.getSepararPorTipoMoneda(),
               resumen.getMontoMaximo(),
               resumen.getMontoMinimo(),
               resumen.getTipo(),
               resumen.getCategoria()
       );
       resumenRepo.save(resumenEntity);
    }

    public BigDecimal total(){
        List<Transaccion> transaccions= transaccionRepo.findAll();
        BigDecimal total= dataAnalysisService.montoTotal(transaccions);
        return total;
    }

    // era Integer, voy a cambiar por BigDecimal
/*    public Map<String, BigDecimal> porCategoria(String categoria ){
        List<Transaccion> transaccions= transaccionRepo.findAll();

        Map<String, BigDecimal> categorias= new HashMap<>();

        for (Transaccion tran: transaccions){
            if(tran.getCategoria().equalsIgnoreCase(categoria)){
            String nombreCategoria = tran.getCategoria();
            BigDecimal monto = new BigDecimal(tran.getMonto());
            categorias.put(categoria, categorias.getOrDefault(nombreCategoria,BigDecimal.ZERO).add(monto));

        return  categorias;
            }

        }
        return null;
    }
*/
        public Map<String, BigDecimal> porCategoria(String categoria){

            List<Transaccion> transaccions= transaccionRepo.findByCategoria(categoria);
            Map<String, BigDecimal> categorias= new HashMap<>();

            for(Transaccion tran: transaccions) {
                String nombreCategoria = tran.getCategoria();
                BigDecimal monto = new BigDecimal(tran.getMonto());
                categorias.put(nombreCategoria, categorias.getOrDefault(nombreCategoria,BigDecimal.ZERO).add(monto));
            }
            return categorias;
        }



    public BigDecimal montoPorCategoria(String categoria){
        List<Transaccion> transaccions= transaccionRepo.findAll();
        return dataAnalysisService.filtrarMontoPorCategoria(transaccions,categoria);
    }


    public byte[] exportarExcel() throws Exception {
        List<Transaccion> transaccions= transaccionRepo.findAll();
        return excelExportService.exportarTransacciones(transaccions);
    }



}

// elao un documento de texto que documente la funcionalidades de la api segun entrada tarea y salida