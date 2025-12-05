package com.meli.rest.evaluacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.rest.evaluacion.exceptions.InvalidDataException;
import com.meli.rest.evaluacion.model.Producto;
import com.meli.rest.evaluacion.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest carga solo el contexto del Controller (la capa web)
// se asume que la InvalidDataException es manejada como 400 Bad Request
@WebMvcTest(
        controllers = ProductosController.class,
        // EXCLUIR SPRING SECURITY: Esto evita los errores 401 y 403
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
public class ProductosControllerTest {

    @Autowired
    private MockMvc mockMvc; // Utilizado para simular las peticiones HTTP

    // Mockea la dependencia de servicio.
    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos Java a JSON

    private Producto productoValido;
    private final String BASE_URL = "/api/evaluacion"; // Ruta definida en @RequestMapping

    @BeforeEach
    void setUp() {
        // Objeto base que se usará para el cuerpo de las peticiones
        productoValido = new Producto();
        productoValido.setId(1L);
        productoValido.setTitulo("Laptop Gaming");
        productoValido.setPrecio(new BigDecimal("1500.00"));
        productoValido.setDescripcion("Producto válido para test.");
    }

   /**
     * Prueba que el endpoint POST /api/evaluacion devuelve el código 201 created
     * evalua la incersion de un registro (Guardar Producto)
     */
    @Test
    void guardarProducto_DatosValidos_Retorna201Created() throws Exception {
        // ARRANGE: Simula que el servicio guarda el producto y lo devuelve
        when(productoService.guardarProducto(any(Producto.class))).thenReturn(productoValido);

        // ACT & ASSERT: Simula la petición POST
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoValido))) // Envía el objeto a JSON
                .andExpect(status().isCreated()) // Espera HTTP 201
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Laptop Gaming"));
    }

    /**
     * Prueba que el endpoint POST /api/evaluacion devuelve el código 400 Bad Request
     * cuando el servicio lanza una InvalidDataException debido a que el precio
     * del producto es menor o igual a cero.
     */
    @Test
    void guardarProducto_DatosInvalidos_Retorna400BadRequest() throws Exception {
        // Creamos un producto con datos que activarían una excepción en el Servicio
        Producto productoInvalido = new Producto();
        productoInvalido.setPrecio(new BigDecimal("0.00")); // Precio inválido

        // ARRANGE: Simula que el servicio lanza una excepción al recibir datos inválidos
        when(productoService.guardarProducto(any(Producto.class)))
                .thenThrow(new InvalidDataException("El precio debe ser positivo."));

        // ACT & ASSERT: El GlobalExceptionHandler debería capturar la excepción y devolver 400
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoInvalido)))
                .andExpect(status().isBadRequest()) // Espera HTTP 400
                // Asume que tu GlobalExceptionHandler mapea la excepción a un campo 'mensaje'
                .andExpect(jsonPath("$.mensaje").value("El precio debe ser positivo."));
    }

// -------------------------------------------------------------------------
// TEST: GET /api/evaluacion/{id} (Obtener por ID)
// -------------------------------------------------------------------------
    /**
     * Prueba que el endpoint GET /api/evaluacion/{id} devuelve el código 200 success
     * cuando el registro existe
     */
    @Test
    void obtenerProductoPorId_Existe_Retorna200Ok() throws Exception {
        // ARRANGE: Simula que el producto se encuentra
        when(productoService.obtenerProductoPorId(1L)).thenReturn(Optional.of(productoValido));

        // ACT & ASSERT:
        mockMvc.perform(get(BASE_URL + "/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Espera HTTP 200
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Laptop Gaming"));
    }

    /**
     * Prueba que el endpoint GET /api/evaluacion/{id} devuelve el código 404 Not found
     * cuando el registro no existe
     */
    @Test
    void obtenerProductoPorId_NoExiste_Retorna404NotFound() throws Exception {
        // ARRANGE: Simula que el producto no se encuentra (Optional vacío)
        when(productoService.obtenerProductoPorId(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT:
        mockMvc.perform(get(BASE_URL + "/{id}", 99L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Espera HTTP 404
    }

// -------------------------------------------------------------------------
// TEST: GET /api/evaluacion (Obtener Todos)
// -------------------------------------------------------------------------

    /**
     * Prueba que el endpoint GET /api/evaluacion devuelve el código 200 success
     * retorna lista de registros
     */
    @Test
    void obtenerTodosLosProductos_ConDatos_Retorna200OkConLista() throws Exception {
        Producto otroProducto = new Producto();
        otroProducto.setId(2L);
        otroProducto.setTitulo("Mousepad XL");

        List<Producto> productosList = Arrays.asList(productoValido, otroProducto);

        // ARRANGE: Simula que se devuelve una lista de 2 productos
        when(productoService.obtenerTodosLosProductos()).thenReturn(productosList);

        // ACT & ASSERT:
        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Espera HTTP 200
                .andExpect(jsonPath("$").isArray()) // Verifica que la respuesta es un array
                .andExpect(jsonPath("$.length()").value(2)) // Verifica el tamaño
                .andExpect(jsonPath("$[0].titulo").value("Laptop Gaming"));
    }

    /**
     * Prueba que el endpoint GET /api/evaluacion devuelve el código 200 success
     * retorna lista vacia
     */
    @Test
    void obtenerTodosLosProductos_ListaVacia_Retorna200OkConListaVacia() throws Exception {
        // ARRANGE: Simula que no hay productos (lista vacía)
        when(productoService.obtenerTodosLosProductos()).thenReturn(List.of());

        // ACT & ASSERT:
        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Espera HTTP 200
                .andExpect(jsonPath("$.length()").value(0)); // Verifica que el array está vacío
    }
}