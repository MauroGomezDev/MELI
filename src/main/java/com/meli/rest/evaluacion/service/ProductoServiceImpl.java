package com.meli.rest.evaluacion.service;

import com.meli.rest.evaluacion.exceptions.InvalidDataException;
import com.meli.rest.evaluacion.model.Producto;
import com.meli.rest.evaluacion.repository.ProductoRepository;
import com.meli.rest.evaluacion.validation.ProductoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de Productos.
 * <p>
 * Contiene la lógica de negocio para la gestión de la entidad {@link Producto},
 * incluyendo validación y delegación de la persistencia al repositorio.
 *
 * @author [Mauricio Gomez Farias]
 * @version 1.0
 * @since 2025-12-04
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoValidator productoValidator;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param productoRepository Repositorio para la persistencia de Producto.
     * @param productoValidator Componente para la validación de reglas de negocio.
     */
    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoValidator productoValidator) {
        this.productoRepository = productoRepository;
        this.productoValidator = productoValidator;
    }

    /**
     * Metodo de implementa guardar los registros recinidos en el metodo post
     *
     * Este método primero valida los datos del producto usando {@link ProductoValidator}
     * y luego guarda o actualiza el registro.
     *
     * @param producto El objeto Producto a guardar en formato json.
     * @return objeto del producto creado
     * @throws InvalidDataException si el producto no cumple con las reglas de negocio.
     */
    @Override
    @Transactional // Garantiza que toda la operación se ejecute como una transacción
    public Producto guardarProducto(Producto producto) {

        // Validamos en la capa de Servicio (Regla de negocio)
        productoValidator.validarDatosCreacion(producto);

        // JpaRepository.save() realiza INSERT si el objeto no tiene ID,
        // o UPDATE si ya tiene un ID existente.
        return productoRepository.save(producto);
    }

    /**
     * Busca y obtiene un producto por su identificador único.
     *
     * @param id Identificador único del producto.
     * @return Un {@link Optional} que contiene el Producto si se encuentra, o vacío si no existe.
     */
    @Override
    @Transactional(readOnly = true) // Optimización: indica que es una operación de lectura
    public Optional<Producto> obtenerProductoPorId(Long id) {
        // JpaRepository.findById() devuelve un Optional<T>
        return productoRepository.findById(id);
    }

    /**
     * Obtiene una lista de todos los productos disponibles en la base de datos.
     *
     * @return Una {@link List} de objetos Producto.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }
}