package com.meli.rest.evaluacion.service;

import com.meli.rest.evaluacion.model.Producto;

import java.util.List;
import java.util.Optional;

/**
 * Interface del servicio de Productos.
 * <p>
 * Definicion de los metodos que seran implementados en ProductoServiceImpl
 *
 * @author [Mauricio Gomez Farias]
 * @version 1.0
 * @since 2025-12-04
 */
public interface ProductoService {

    /**
     * Guarda (inserta o actualiza) un producto en la base de datos.
     * @param producto El objeto Producto a guardar.
     * @return El Producto guardado con su ID generado.
     */
    Producto guardarProducto(Producto producto);

    /**
     * Obtiene todos los campos de un producto por su ID.
     * @param id El ID del producto.
     * @return Un Optional que contiene el Producto si existe, o vacío si no.
     */
    Optional<Producto> obtenerProductoPorId(Long id);

    /**
     * Obtiene una lista de todos los productos.
     * @return Una lista de todos los productos.
     */
    List<Producto> obtenerTodosLosProductos();
}