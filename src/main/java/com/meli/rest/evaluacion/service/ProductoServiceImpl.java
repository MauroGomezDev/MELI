package com.meli.rest.evaluacion.service;

import com.meli.rest.evaluacion.exceptions.InvalidDataException;
import com.meli.rest.evaluacion.model.Producto;
import com.meli.rest.evaluacion.repository.ProductoRepository;
import com.meli.rest.evaluacion.validation.ProductoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    // 1. Instanciar el Logger
    private static final Logger logger = LoggerFactory.getLogger(ProductoServiceImpl.class);

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

        // 1. INFO al inicio del proceso
        logger.info("-> Iniciando proceso de guardado o actualización de producto. ID recibido: {}", producto.getId());

        try {
            logger.debug("Validando datos del producto: {}", producto);
            productoValidator.validarDatosCreacion(producto);
            logger.debug("Validación exitosa para el producto.");

            Producto productoGuardado = productoRepository.save(producto);
            logger.info("<- Producto guardado exitosamente. Nuevo ID asignado: {}", productoGuardado.getId());

            return productoGuardado;

        } catch (InvalidDataException e) {
            logger.error("Error de validación al guardar producto: {}", e.getMessage());
            throw e; // Relanzar la excepción para que el controlador la maneje
        }
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
        logger.info("-> Ejecutando método de obtención de producto por ID: {}", id);
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            logger.debug("<- Producto encontrado con ID: {} {}", id, producto.get().getTitulo());
        } else {
            logger.warn("<- No se encontró ningún producto con ID: {}", id);
        }
        return producto;
    }

    /**
     * Obtiene una lista de todos los productos disponibles en la base de datos.
     *
     * @return Una {@link List} de objetos Producto.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerTodosLosProductos() {
        logger.info("-> Ejecutando metodo get de para obtencion de todos los productos");
        return productoRepository.findAll();
    }
}