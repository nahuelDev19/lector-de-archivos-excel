# 📊 API de Procesamiento de Transacciones Excel

API REST desarrollada con Spring Boot que procesa archivos Excel 
de transacciones de forma automática a través de un pipeline de 
datos, persiste la información en base de datos y genera resúmenes 
estadísticos.

## 🚀 Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL (NeonTech)
- Apache POI
- Swagger / OpenAPI
- Maven

## ⚙️ ¿Cómo funciona?

Al cargar un archivo Excel, el sistema ejecuta un pipeline que:

1. **Lee** el archivo usando Apache POI
2. **Filtra y normaliza** los datos (limpieza de valores inválidos)
3. **Persiste** cada transacción en la base de datos
4. **Genera un resumen** con total, máximo y mínimo en una tabla aparte

## 📂 Funcionalidades

- Carga y procesamiento de archivos Excel
- Pipeline de limpieza y normalización de datos
- CRUD completo de transacciones
- Resumen estadístico automático (total, máximo, mínimo)
- Documentación interactiva con Swagger

## 🧪 Documentación API

Disponible en: `http://localhost:8080/swagger-ui.html`

## ⚙️ Configuración

Configurar las siguientes variables de entorno:

DB_URL=...
DB_USERNAME=...
DB_PASSWORD=...
