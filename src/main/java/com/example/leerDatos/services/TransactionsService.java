package com.example.leerDatos.services;

import com.example.leerDatos.entitys.Transaccion;
import com.example.leerDatos.entitys.TransaccionDTO;
import jakarta.transaction.Transaction;

import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

public interface TransactionsService {

    Transaccion findById(UUID id);
    TransaccionDTO create(TransaccionDTO dto);
    TransaccionDTO update(UUID id, TransaccionDTO dto);
    void delete(UUID id);




}
