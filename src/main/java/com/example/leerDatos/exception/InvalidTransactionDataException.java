package com.example.leerDatos.exception;

public class InvalidTransactionDataException extends RuntimeException {

    public InvalidTransactionDataException(String message) {
        super(message);
    }
}