package com.meli.rest.evaluacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Clase principal que inicializa y arranca la aplicación Spring Boot.
 * <p>
 * Utiliza la anotación {@code @SpringBootApplication} para habilitar la
 * configuración automática y el escaneo de componentes.
 * <p>
 * {@code exclude = {SecurityAutoConfiguration.class}} se utiliza para deshabilitar
 * temporalmente la configuración automática de Spring Security, lo que facilita
 * el desarrollo y las pruebas sin requerir autenticación inmediata.
 *
 * @author [Mauricio Gomez Farias]
 * @version 1.0
 * @since 2025-12-04
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class EvaluacionApplication {

	/**
	 * Método principal de la aplicación.
	 * <p>
	 * Utiliza {@code SpringApplication.run()} para lanzar la aplicación
	 * Spring Boot.
	 *
	 * @param args Argumentos de línea de comandos pasados a la aplicación.
	 */
	public static void main(String[] args) {
		SpringApplication.run(EvaluacionApplication.class, args);
	}

}