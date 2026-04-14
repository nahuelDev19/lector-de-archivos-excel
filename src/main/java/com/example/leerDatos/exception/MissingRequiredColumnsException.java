package com.example.leerDatos.exception;


public class MissingRequiredColumnsException extends RuntimeException {

    public MissingRequiredColumnsException(String message) {
        super(message);
    }
}