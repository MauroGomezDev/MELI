🛍️ API de Productos (Simulación Mercado Libre)🌟 Resumen del ProyectoEsta es una API RESTful desarrollada en Spring Boot que simula el backend de un servicio de gestión de productos, diseñada para ser consumida por un frontend de comercio electrónico (similar a Mercado Libre). Permite a los clientes crear nuevos registros, consultar productos individualmente y obtener la lista completa del inventario.🚀 Tecnologías y EntornoComponenteTecnologíaVersiónPropósitoFrameworkSpring Boot3.1.5Configuración de la aplicación REST.LenguajeJava17Lenguaje principal del proyecto.Build ToolMaven4.0.0Gestión de dependencias y ciclo de vida del build.IDEIntelliJ IDEA(Cualquiera)Entorno de desarrollo.Base de DatosH2 DatabaseRuntimeBase de datos relacional en memoria para desarrollo.PersistenciaSpring Data JPA / HibernateInherenteMapeo Objeto-Relacional (ORM).UtilidadesLombok1.18+Reducción de código boilerplate (getters, setters, etc.).⚙️ Configuración y Ejecución LocalRequisitos PreviosJava Development Kit (JDK): Versión 17.Maven: Versión 3.6+ (o superior).Pasos para EjecutarClonar el Repositorio:Bashgit clone [URL_DEL_REPOSITORIO]
cd evaluacion
Compilar y Ejecutar (Maven):Asegúrate de que tu puerto 8080 esté libre.Bashmvn clean install
mvn spring-boot:run
Acceso: La API se levantará en: http://localhost:8080💻 Endpoints de la API (Recurso: /api/productos)Todos los endpoints utilizan la ruta base /api/productos.MétodoURLDescripciónCódigo de ÉxitoPOST/api/productosCrea un nuevo registro de producto en la base de datos.201 CreatedGET/api/productosLista todos los productos disponibles en el inventario.200 OKGET/api/productos/{id}Consulta los detalles de un producto específico por su ID.200 OK o 404 Not Found1. Crear Producto (POST /api/productos)Cuerpo de la Petición (JSON Ejemplo):JSON{
"titulo": "iPhone 15 Pro Max",
"precio": 1299.99,
"moneda": "USD",
"stockDisponible": 50,
"descripcion": "El último modelo con cámara de 48MP y chip A17 Bionic.",
"especificaciones": ["Color: Azul Titanio", "Memoria: 256GB"],
"urlsImagenes": ["url_img_1", "url_img_2"],
"idVendedor": 1001,
"nombreVendedor": "Apple Store Oficial",
"reputacionVendedor": 4.9
}
2. Obtener Lista (GET /api/productos)Respuesta Exitosa (200 OK):JSON[
   {
   "id": 1,
   "titulo": "iPhone 15 Pro Max",
   "precio": 1299.99,
   // ... otros campos
   }
   ]
3. Consultar por ID (GET /api/productos/1)Respuesta Exitosa (200 OK) para ID=1:JSON{
   "id": 1,
   "titulo": "iPhone 15 Pro Max",
   "precio": 1299.99,
   // ... todos los detalles del producto
   }
   💾 Configuración de la Base de DatosEl proyecto utiliza H2 en modo en memoria (jdbc:h2:mem:evaluaciondb).La configuración se encuentra en src/main/resources/application.properties:Propertiesspring.datasource.url=jdbc:h2:mem:evaluaciondb
   spring.datasource.username=sa
   spring.datasource.password=
   spring.jpa.hibernate.ddl-auto=update
