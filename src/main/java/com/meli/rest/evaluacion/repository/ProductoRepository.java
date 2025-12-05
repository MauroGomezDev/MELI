package com.meli.rest.evaluacion.repository;

import com.meli.rest.evaluacion.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de repositorio para la gestión de datos de la entidad Producto.
 *
 * Proporciona métodos de persistencia básicos (CRUD: Create, Read, Update, Delete),
 * hereda de {@code JpaRepository<Producto, Long>}.
 * Spring Data JPA genera la implementación en tiempo de ejecución.
 *
 * @author [Mauricio Gomez Farias]
 * @version 1.0
 * @since 2025-12-04
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // No se definieron métodos en esta clase ya que
    // utilizare los que JpaRepository proporciona automáticamente.

}
