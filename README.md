
# Lector de archivos excel

API de Procesamiento de Transacciones
📌 Descripción

API REST desarrollada con Spring Boot que permite cargar un archivo Excel de transacciones y procesarlo automáticamente.

El sistema:

Lee el Excel usando Apache POI
Limpia y normaliza los datos
Procesa la información
Guarda cada transacción en la base de datos (NeonTech)
Genera un resumen en una tabla adicional
Permite realizar operaciones CRUD sobre las transacciones

🚀 Tecnologías
Java 17
Spring Boot
Spring Data JPA
MySQL / NeonTech
Apache POI
Swagger (OpenAPI)
Maven

⚙️ Configuración

Las credenciales de la base de datos se configuran mediante variables de entorno:

DB_URL=...
DB_USERNAME=...
DB_PASSWORD=...

📂 Funcionalidades principales
Carga de archivo Excel de transacciones
Procesamiento de datos (limpieza y normalización)
Persistencia en base de datos
Generación de resumen de transacciones
CRUD completo de transacciones

🧪 Documentación API

La documentación interactiva está disponible con Swagger:
http://localhost:8080/swagger-ui.html
