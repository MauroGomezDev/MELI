package com.meli.rest.evaluacion.controller;

import com.meli.rest.evaluacion.model.Producto;
import com.meli.rest.evaluacion.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de productos MELI.
 * <p>
 * Expone los endpoints de la API para realizar operaciones CRUD (Crear y Leer) de la tabla Producto
 * La ruta base para este controlador es {/api/productos}.
 * * @author [Mauricio Gomez Farias]
 * @version 1.0
 * @since 2025-12-04
 */
@RestController
@RequestMapping(value = "/api/evaluacion")
public class ProductosController {

    private static final Logger log = LoggerFactory.getLogger(ProductosController.class);

    @Autowired
    private ProductoService productoService;

    //public EvaluacionController(ProductoService userService) {
    //    this.productoService = userService;
    //}

    /**
     * Metodo POST para insertar registros en la tabla productos
     * @param producto json con datos de productos
     * @return ResponseEntity que incluye objeto creado, id creado y http status
     */
    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        // Llama al servicio para guardar el objeto Producto
        Producto nuevoProducto = productoService.guardarProducto(producto);

        // Retorna el producto guardado con el código HTTP 201 (CREATED)
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    /**
     * Metodo GET que obtiene los datos del registro de acuerdo con el ID recibido como variable de path
     * @param id Identificador del registro
     * @return ResponseEntity que contiene:
     *  Si el producto es encontrado: El objeto Producto y el código de estado HTTP 200 OK
     *  Si el producto NO es encontrado: Un cuerpo vacío y el código de estado HTTP 404 Not Found
     *
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        // El Optional maneja el caso de que el producto no exista
        Optional<Producto> productoOptional = productoService.obtenerProductoPorId(id);

        if (productoOptional.isPresent()) {
            // Retorna el producto encontrado con el código HTTP 200 (OK)
            return new ResponseEntity<>(productoOptional.get(), HttpStatus.OK);
        } else {
            // Retorna un código HTTP 404 (NOT FOUND) si no existe
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Recupera una lista de todos los productos disponibles en el sistema.
     * Si no existen productos, devuelve una lista vacía.
     *
     * @return ResponseEntity que contiene:
     * Una lista de objetos (List<Producto>)
     * El código de estado HTTP 200 OK si la operación fue exitosa
     */
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos () {
        List<Producto> productos = productoService.obtenerTodosLosProductos();

        // Retorna la lista de productos con el código HTTP 200 (OK)
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
}
