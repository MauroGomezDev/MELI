# Nombre del Proyecto: Evaluación Meli / [Tu Nombre Aquí]

Este es un proyecto de microservicio desarrollado con Spring Boot y Maven, diseñado para [Describir brevemente el propósito del servicio: ej. gestionar datos de productos, procesar pagos, etc.].

---

## 🚀 Tecnologías Utilizadas

El proyecto fue construido con las siguientes herramientas y tecnologías clave:

* **Lenguaje:** Java 17+ (o la versión que estés utilizando)
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

---

## ⚙️ Configuración y Ejecución

Sigue estos pasos para levantar el microservicio localmente:

### 1. Clonar el Repositorio

Abre tu terminal y clona el proyecto:

```bash
git clone [https://github.com/MauroGomezDev/MELI.git](https://github.com/MauroGomezDev/MELI.git)
cd MELI

2. Construir el ProyectoUtiliza Maven para compilar el código y descargar las dependencias.Bashmvn clean install
3. Ejecutar la AplicaciónPuedes ejecutar la aplicación directamente desde el JAR generado (opción A) o usando el plugin de Spring Boot (opción B).Opción A (Ejecutar JAR):Bashjava -jar target/[nombre-del-archivo-jar].jar
Opción B (Ejecutar con Spring Boot):Bashmvn spring-boot:run
El servicio estará disponible en http://localhost:8080 (o el puerto configurado en application.properties).🧪 Pruebas UnitariasPara ejecutar todas las pruebas unitarias y de integración del proyecto:Bashmvn test
📝 Endpoints PrincipalesAquí se detallan los endpoints (rutas API) disponibles en el servicio:MétodoRutaDescripciónGET/api/v1/productosObtiene la lista completa de productos.POST/api/v1/productosCrea un nuevo producto.GET/api/v1/productos/{id}Obtiene un producto por ID.
