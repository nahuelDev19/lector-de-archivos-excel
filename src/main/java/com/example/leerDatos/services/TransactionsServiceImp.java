package com.example.leerDatos.services;

import com.example.leerDatos.entitys.Transaccion;
import com.example.leerDatos.entitys.TransaccionDTO;
import com.example.leerDatos.exception.InvalidTransactionDataException;
import com.example.leerDatos.exception.ResourceNotFoundException;
import com.example.leerDatos.repository.TransaccionRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class TransactionsServiceImp implements  TransactionsService{

    private TransaccionRepo transaccionRepo;

    public TransactionsServiceImp(TransaccionRepo transaccionRepo) {
        this.transaccionRepo = transaccionRepo;
    }

    @Override
    public Transaccion findById(UUID id) {
        return  transaccionRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Transaccion no encontrada"));
    }

    @Override
    public TransaccionDTO create(TransaccionDTO dto) {
        Transaccion transaccion = new Transaccion(
                LocalDate.parse(dto.getFecha()),
                dto.getCliente(),
                new BigDecimal(dto.getMonto()),
                dto.getMoneda(),
                dto.getTipo(),
                dto.getCategoria()
        );
        transaccionRepo.save(transaccion);
        return dto;
    }

    @Override
    public TransaccionDTO update(UUID id, TransaccionDTO dto) {

        Transaccion transaccion = transaccionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la transacción"));

        // actualizar campos
        transaccion.setFecha(LocalDate.parse(dto.getFecha()));
        transaccion.setCliente(dto.getCliente());
        transaccion.setMonto(new BigDecimal(dto.getMonto()));
        transaccion.setMoneda(dto.getMoneda());
        transaccion.setTipo(dto.getTipo());
        transaccion.setCategoria(dto.getCategoria());

        transaccionRepo.save(transaccion);

        return dto;
    }


    @Override
    public void delete(UUID id) {
        transaccionRepo.deleteById(id);
    }
}
