package com.meli.rest.evaluacion.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Representa la entidad de un Producto dentro del sistema de inventario y venta MELI.
 *
 * Esta clase mapea la tabla productos en la base de datos y define la
 * estructura de un artículo, incluyendo información básica, características,
 * detalles del vendedor y métricas de venta.
 *
 * @author [Mauricio Gomez Farias]
 * @version 1.0
 * @since 2025-12-04
 * @see com.meli.rest.evaluacion.repository.ProductoRepository
 */
@Entity
@Table(name = "productos")
@Data // lombok
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Información Básica del Artículo ---
    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(nullable = false, length = 3)
    private String moneda = "CLP"; // Moneda por defecto

    @Column(nullable = false)
    private Integer stockDisponible;

    // --- Descripción y Características ---
    @Lob // Para textos largos (CLOB)
    @Column(nullable = false)
    private String descripcion;

    @ElementCollection // Para listas simples como especificaciones técnicas
    @CollectionTable(name = "producto_especificaciones", joinColumns = @JoinColumn(name = "producto_id"))
    @Column(name = "especificacion")
    private List<String> especificaciones;

    // --- Imágenes ---
    @ElementCollection // Para guardar una lista de URLs de imágenes
    @CollectionTable(name = "producto_imagenes", joinColumns = @JoinColumn(name = "producto_id"))
    @Column(name = "url_imagen")
    private List<String> urlsImagenes;

    // --- Información del Vendedor ---
    // En un caso real, esto sería una relación @ManyToOne con una Entity Vendedor
    // Para simplificar, usamos solo campos de referencia:
    private Long idVendedor;
    private String nombreVendedor;
    private Float reputacionVendedor; // Ejemplo: 4.5

    // --- Métricas y Tiempos ---
    private Integer cantidadVendida = 0;

    @Column(nullable = false)
    private LocalDateTime fechaPublicacion = LocalDateTime.now();

    // --- Opciones de Envío (Simulación) ---
    private Boolean envioGratis = false;
    private String metodoEnvioPrincipal; // Ej: "Mercado Envíos"
    // Podrías agregar una relación con una entidad 'Ubicacion' si es necesario

    // --- Opiniones (Simulación) ---
    private Float ratingPromedio = 0.0f; // 4.8
    private Integer totalOpiniones = 0; // 542

    // --- Campos de Auditoría (Opcional pero Recomendado) ---
    private LocalDateTime fechaActualizacion;

    // NOTA: Con Lombok, no necesitas escribir los constructores, getters y setters.
}