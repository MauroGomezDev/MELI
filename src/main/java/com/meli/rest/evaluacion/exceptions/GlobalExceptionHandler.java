package com.meli.rest.evaluacion.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manejador Global de Excepciones (Global Exception Handler).
 * <p>
 * Esta clase centraliza el manejo de excepciones lanzadas por los controladores
 * de la aplicación. Captura tipos específicos de excepciones y las mapea
 * a respuestas HTTP adecuadas (ResponseEntity) con códigos de estado como 400 o 500.
 * La idea es mantener esta clase global para controlar todas las excepciones de manera centralizada
 *
 * @author [Mauricio Gomez Farias]
 * @version 1.0
 * @since 2025-12-04
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja las excepciones de negocio personalizadas {@code InvalidDataException}.
     *
     * @param ex La excepción {@code InvalidDataException} lanzada.
     * @return Una respuesta HTTP con estado 400 Bad Request y el mensaje de la excepción.
     */
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ResponseException> handleInvalidDataException(InvalidDataException ex) {
        log.error("Datos Invalidos: " + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseException(ex.getMessage()));
    }

    /**
     * Maneja las excepciones relacionadas con la violación de la integridad de los datos
     * en la base de datos (ej. campos nulos, violaciones de unicidad).
     *
     * @param ex La excepción {@code DataIntegrityViolationException} lanzada.
     * @return Una respuesta HTTP con estado 400 Bad Request y un mensaje genérico.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseException> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("Error de Integridad de Datos: " + ex.getMessage());
        String mensaje = "Error en los datos enviados. Asegúrese de que todos los campos obligatorios (nullable=false) están presentes y son válidos.";
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // Usamos 400 Bad Request
                .body(new ResponseException(mensaje));
    }

    /**
     * Manejador de catch-all para cualquier otra excepción no cubierta explícitamente.
     *
     * @param ex La excepción general {@code Exception} lanzada.
     * @return Una respuesta HTTP con estado 500 Internal Server Error y el mensaje de la excepción.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseException> handleGeneralException(Exception ex) {
        log.error("Error general al intentar crear un producto: " + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseException(ex.getMessage()));
    }

    /**
     * Maneja las excepciones que ocurren durante el procesamiento del JSON
     * (ej. JSON malformado o tipos de datos incorrectos en el cuerpo de la petición).
     *
     * @param ex La excepción {@code JsonProcessingException} lanzada.
     * @return Una respuesta HTTP con estado 500 Internal Server Error y un mensaje descriptivo.
     */
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ResponseException> handleJsonProcessingException(JsonProcessingException ex) {
        log.error("Error interno del servidor al procesar JSON", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseException("Error interno del servidor al procesar JSON"));
    }
}