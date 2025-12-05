# Nombre del Proyecto: Evaluación Meli / Mauricio Gomez Farias

Este microservicio fue desarrollado con Spring Boot y Maven. Su objetivo principal es servir como evaluación técnica para el personal de MELI, en el marco de una postulación a una vacante de desarrollador backend. 

---

## 🚀 Tecnologías Utilizadas

El proyecto fue construido con las siguientes herramientas y tecnologías clave:

* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.x
* **Herramienta de Build:** Apache Maven
* **Testing:** JUnit 5, Mockito
* **Control de Versiones:** Git

---

## 🛠️ Requisitos Previos

Antes de ejecutar la aplicación, asegúrate de tener instalado lo siguiente:

1.  **JDK (Java Development Kit):** Versión 17 o superior.
2.  **Maven:** Herramienta de gestión de dependencias.
3.  **IDE:** IntelliJ IDEA, Eclipse o VS Code (Opcional, pero recomendado).
4.  Dependencias de Spring Boot (Spring Web, Spring Data JPA, lombok, jaxb, jackson, etc)
5.  Edita el archivo `application.properties` para configurar la base de datos y otras propiedades según tus necesidades.
   Actualmente tiene configurada una base de datos H2.

---

## ⚙️ Configuración y Ejecución

Sigue estos pasos para levantar el microservicio localmente:

### 1. Clonar el Repositorio

Abre tu terminal y clona el proyecto:

```bash
git clone [https://github.com/MauroGomezDev/MELI.git](https://github.com/MauroGomezDev/MELI.git)
cd MELI

### 2. Construir el proyecto
Utiliza Maven para compilar el código y descargar las dependencias.
mvn clean install

### 3. Ejecutar la aplicacion
Puedes ejecutar la aplicación directamente desde el JAR generado (opción A) o usando el plugin de Spring Boot (opción B).

java -jar target/[nombre-del-archivo-jar].jar
mvn spring-boot:run

El servicio estará disponible en http://localhost:8080 (o el puerto configurado en application.properties).

🧪 Pruebas Unitarias
Para ejecutar todas las pruebas unitarias y de integración del proyecto:
mvn test
Se crearon 2 

📝 Endpoints Principales
Aquí se detallan los endpoints (rutas API) disponibles en el servicio:
GET  :/api/evaluacion	(Obtiene la lista completa de productos).
POST :/api/evaluacion	(Crea un nuevo producto).
GET	 :/api/productos/{id}	(Obtiene un producto por ID).

### A. Sección: 🧪 Pruebas y Verificación
La aplicación incluye pruebas unitarias y de integración para asegurar la robustez de la lógica de negocio y la capa de servicio.
### Ejecución de Pruebas
Para ejecutar el *test suite* completo, use el siguiente comando Maven:
```bash
mvn test

### B. Sección: 📝 Documentación de Endpoints
Detalle de ruta, propósito y ejemplos de *payloads*.

```markdown
## 📝 Endpoints de la API (v1)
El servicio expone los siguientes endpoints para la gestión de productos.
### 1. Crear Producto (POST /api/evaluacion)

**Propósito:** Permite crear un nuevo registro de producto o actualizar uno existente.
**Validación:** Requiere que el campo 'precio' sea > 0.

#### Ejemplo de Solicitud (Request Body - JSON de Entrada)

```json
{
  "titulo": "Samsung Galaxy S23 Ultra 512GB Verde",
  "precio": 999.99,
  "moneda": "USD",
  "stockDisponible": 15,
  "descripcion": "El último modelo de Samsung con cámara de 200MP, S Pen integrado y batería de larga duración. Perfecto para profesionales y entusiastas de la fotografía.",
  "especificaciones": [
    "Pantalla: Dynamic AMOLED 2X de 6.8 pulgadas",
    "Almacenamiento: 512 GB",
    "RAM: 12 GB",
    "Color: Verde Esmeralda"
  ],
  "urlsImagenes": [
    "https://http2.mlstatic.com/D_NQ_NP_2X_721843-MLU73651065883_122023-F.webp",
    "https://http2.mlstatic.com/D_NQ_NP_2X_969932-MLA79659554479_092024-F.webp",
    "https://http2.mlstatic.com/D_NQ_NP_2X_929123-MLU80276924341_102024-F.webp"
  ],
  "idVendedor": 1005,
  "nombreVendedor": "TecnoExpress Limitada",
  "reputacionVendedor": 4.9,
  "envioGratis": true,
  "metodoEnvioPrincipal": "Chilexpress Prioritario",
  "ratingPromedio": 4.8,
  "totalOpiniones": 85
  // Nota: Los campos 'id', 'cantidadVendida', 'fechaPublicacion' y 'fechaActualizacion' 
  // serán manejados (generados o actualizados) por el backend (Spring/JPA) 
  // y no necesitan ser enviados en el JSON inicial.
}

#### Ejemplo de Respuesta Exitosa (Status 201 Created)
retorna ademas un json del objeto creado incluyendo el indice creado.

### 2. Obtener Producto por ID (GET /api/productos/{id})
Propósito: Recupera los detalles de un producto específico.

Ejemplo de Solicitud (URL)
GET http://localhost:8080/api/evaluacion/1

Ejemplo de Respuesta (Status 200 OK)
{
    "id": 1,
    "titulo": "Samsung Galaxy S23 Ultra 512GB Verde",
    "precio": 999.99,
    "moneda": "USD",
    "stockDisponible": 15,
    "descripcion": "El último modelo de Samsung con cámara de 200MP, S Pen integrado y batería de larga duración. Perfecto para profesionales y entusiastas de la fotografía.",
    "especificaciones": [
        "Pantalla: Dynamic AMOLED 2X de 6.8 pulgadas",
        "Almacenamiento: 512 GB",
        "RAM: 12 GB",
        "Color: Verde Esmeralda"
    ],
    "urlsImagenes": [
        "https://http2.mlstatic.com/D_NQ_NP_2X_721843-MLU73651065883_122023-F.webp",
        "https://http2.mlstatic.com/D_NQ_NP_2X_969932-MLA79659554479_092024-F.webp",
        "https://http2.mlstatic.com/D_NQ_NP_2X_929123-MLU80276924341_102024-F.webp"
    ],
    "idVendedor": 1005,
    "nombreVendedor": "TecnoExpress Limitada",
    "reputacionVendedor": 4.9,
    "cantidadVendida": 0,
    "fechaPublicacion": "2025-12-05T19:04:39.5405388",
    "envioGratis": true,
    "metodoEnvioPrincipal": "Chilexpress Prioritario",
    "ratingPromedio": 4.8,
    "totalOpiniones": 85,
    "fechaActualizacion": null
}


🔎 Monitorización y Logging
La aplicación implementa un sistema robusto de registro (logging) para facilitar la depuración, el seguimiento del flujo de negocio y la monitorización en entornos productivos.

Librería Principal: Se utiliza SLF4J como facade (interfaz) de logging.
Implementación: Se utiliza Logback como el sistema de registro subyacente, el cual viene configurado por defecto en Spring Boot.
Configuración: Los niveles de registro pueden ajustarse dinámicamente mediante un archivo dedicado logback-spring.xml para configuraciones avanzadas (rotación de archivos, appenders específicos, etc.).
Niveles de Uso: Los registros se manejan con niveles estándar, priorizando:
* INFO: Para eventos importantes del flujo de negocio (ej. inicio/fin de un proceso).
* DEBUG: Para rastrear valores de datos y pasos internos de los servicios.
* WARN/ERROR: Para registrar fallos de validación (WARN) o excepciones críticas (ERROR).

🧪 Pruebas y Cobertura

