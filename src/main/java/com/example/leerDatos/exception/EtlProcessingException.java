package com.example.leerDatos.exception;


public class EtlProcessingException extends RuntimeException {

    public EtlProcessingException(String message) {
        super(message);
    }

    public EtlProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}