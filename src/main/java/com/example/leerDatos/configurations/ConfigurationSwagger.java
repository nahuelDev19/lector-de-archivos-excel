package com.example.leerDatos.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "lectorDatos",
                description = "lectura, limpieza y normalizacion de tablas en formato xmlx",
                version = "1.0.0",
                contact = @Contact(
                        name = "Nahuel Perea",
                        url = "https://github.com/nahuelDev19/lector-de-archivos-excel"
                )


        ),
        servers ={
        @Server(
                description = "dev server",
                url = "http://localhost:8080"
        ),
        @Server(
                description = "produccion server",
                url = "http://localhost:8080"

        )

        }

)
public class ConfigurationSwagger {
}
