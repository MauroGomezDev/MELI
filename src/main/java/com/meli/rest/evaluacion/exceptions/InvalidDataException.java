package com.meli.rest.evaluacion.exceptions;

/**
 * Excepción personalizada utilizada para indicar fallos en la validación
 * de datos de negocio.
 * <p>
 * Se lanza típicamente desde la capa de Servicio (Service) cuando los datos
 * de entrada (como un precio negativo o un campo nulo obligatorio)
 * no cumplen con las reglas de negocio de la aplicación.
 * <p>
 * Al extender de {@code RuntimeException}, es una excepción no comprobada (unchecked),
 * lo que permite que sea manejada de forma centralizada por el {@code GlobalExceptionHandler}
 * sin necesidad de declararla en las firmas de los métodos.
 */
public class InvalidDataException extends RuntimeException {

    /**
     * Constructor que crea una nueva excepción con un mensaje detallado.
     *
     * @param message El mensaje descriptivo que indica la razón exacta de la invalidez de los datos.
     */
    public InvalidDataException(String message) {
        super(message);
    }
}