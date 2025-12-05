package com.meli.rest.evaluacion.validation;

import com.meli.rest.evaluacion.exceptions.InvalidDataException;
import com.meli.rest.evaluacion.model.Producto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
/**
 * Clase creada para validar datos de entrada de productos MELI.
 *
 * @author [Mauricio Gomez Farias]
 * @version 1.0
 * @since 2025-12-04
 */
@Component // Bean de Spring
public class ProductoValidator {

    /**
     * Realiza las validaciones de negocio básicas para un objeto Producto.
     * @param producto El objeto Producto a validar.
     * @throws InvalidDataException si el producto tiene datos inválidos.
     */
    public void validarDatosCreacion(Producto producto) {
        // 1. Validar que el campo precio no sea nulo
        if (producto.getPrecio() == null) {
            throw new InvalidDataException("El precio del producto es obligatorio y no puede ser nulo.");
        }

        // 2. Validar que el precio sea un numero > 0
        if (producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDataException("El precio del producto debe ser mayor que cero.");
        }

        // Aca se podrian agregar mas validaciones.
    }
}