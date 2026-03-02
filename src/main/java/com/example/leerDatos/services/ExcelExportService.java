package com.example.leerDatos.services;

import com.example.leerDatos.entitys.Transaccion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
@Service
public class ExcelExportService {


    public byte[] exportarTransacciones(List<Transaccion> transacciones) throws Exception {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Transacciones");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Fecha");
        header.createCell(1).setCellValue("Cliente");
        header.createCell(2).setCellValue("Monto");
        header.createCell(3).setCellValue("Moneda");
        header.createCell(4).setCellValue("Categoria");
        header.createCell(5).setCellValue("Tipo");

        int rowNum = 1;

        for (Transaccion t : transacciones) {
            Row row= sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(t.getFecha());
            row.createCell(1).setCellValue(t.getCliente());
            row.createCell(2).setCellValue(t.getMonto());
            row.createCell(3).setCellValue(t.getMoneda());
            row.createCell(4).setCellValue(t.getCategoria());
            row.createCell(5).setCellValue(t.getTipo());
        }

        ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] bytes= outputStream.toByteArray();
        workbook.close();
        outputStream.close();

        return bytes;
    }

}
