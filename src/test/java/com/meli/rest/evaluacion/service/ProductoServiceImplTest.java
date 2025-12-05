package com.meli.rest.evaluacion.service;

import com.meli.rest.evaluacion.exceptions.InvalidDataException;
import com.meli.rest.evaluacion.model.Producto;
import com.meli.rest.evaluacion.repository.ProductoRepository;
import com.meli.rest.evaluacion.validation.ProductoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// 1. Usa MockitoExtension para inicializar los mocks
@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {

    // 2. Mockea las dependencias inyectadas en el constructor
    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoValidator productoValidator;

    // 3. Inyecta los mocks en la clase a probar
    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto productoValido;

    @BeforeEach
    void setUp() {
        // Objeto base para pruebas válidas
        productoValido = new Producto();
        productoValido.setId(1L);
        productoValido.setTitulo("Test Item");
        productoValido.setPrecio(new BigDecimal("100.00"));
    }

    // -------------------------------------------------------------------------
    // TEST: guardarProducto()
    // -------------------------------------------------------------------------

    @Test
    void guardarProducto_DatosValidos_LlamaValidadorYGuarda() {
        // ARRANGE:
        // 1. Configurar el validador para que no haga nada (simula que la validación pasa).
        doNothing().when(productoValidator).validarDatosCreacion(any(Producto.class));

        // 2. Configurar el repositorio para devolver el producto guardado.
        when(productoRepository.save(any(Producto.class))).thenReturn(productoValido);

        // ACT:
        Producto resultado = productoService.guardarProducto(productoValido);

        // ASSERT:
        assertNotNull(resultado, "El producto no debe ser nulo.");

        // 1. Verificar que el validador fue llamado exactamente una vez.
        verify(productoValidator, times(1)).validarDatosCreacion(productoValido);

        // 2. Verificar que el método save() del repositorio fue llamado DESPUÉS.
        verify(productoRepository, times(1)).save(productoValido);

        // 3. El orden de las verificaciones es implícito por la lógica del servicio.
    }

    @Test
    void guardarProducto_DatosInvalidos_LanzaInvalidDataException() {
        // ARRANGE:
        // 1. Configurar el validador para que LANCE la excepción al ser llamado.
        // Simulamos el fallo de negocio.
        doThrow(new InvalidDataException("Precio inválido"))
                .when(productoValidator)
                .validarDatosCreacion(any(Producto.class));

        // ACT & ASSERT:
        // 1. Verificar que el servicio lanza la excepción esperada.
        Exception exception = assertThrows(InvalidDataException.class, () -> {
            productoService.guardarProducto(productoValido);
        }, "Debe lanzar InvalidDataException.");

        assertEquals("Precio inválido", exception.getMessage());

        // 2. Verificar que el repositorio NUNCA fue llamado (porque la validación falló antes).
        verify(productoRepository, never()).save(any(Producto.class));
    }


    // -------------------------------------------------------------------------
    // TEST: obtenerProductoPorId()
    // -------------------------------------------------------------------------

    @Test
    void obtenerProductoPorId_ProductoExiste_RetornaOptionalConProducto() {
        // ARRANGE:
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoValido));

        // ACT:
        Optional<Producto> resultado = productoService.obtenerProductoPorId(1L);

        // ASSERT:
        assertTrue(resultado.isPresent(), "El producto debe estar presente.");
        assertEquals("Test Item", resultado.get().getTitulo());
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerProductoPorId_ProductoNoExiste_RetornaOptionalVacio() {
        // ARRANGE:
        when(productoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // ACT:
        Optional<Producto> resultado = productoService.obtenerProductoPorId(99L);

        // ASSERT:
        assertFalse(resultado.isPresent(), "El Optional debe estar vacío.");
        verify(productoRepository, times(1)).findById(99L);
    }

    // -------------------------------------------------------------------------
    // TEST: obtenerTodosLosProductos()
    // -------------------------------------------------------------------------

    @Test
    void obtenerTodosLosProductos_ConDatos_RetornaListaConProductos() {
        // ARRANGE:
        List<Producto> listaMock = List.of(productoValido);
        when(productoRepository.findAll()).thenReturn(listaMock);

        // ACT:
        List<Producto> resultado = productoService.obtenerTodosLosProductos();

        // ASSERT:
        assertNotNull(resultado, "La lista no debe ser nula.");
        assertEquals(1, resultado.size(), "La lista debe contener un elemento.");
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void obtenerTodosLosProductos_SinDatos_RetornaListaVacia() {
        // ARRANGE:
        when(productoRepository.findAll()).thenReturn(Collections.emptyList());

        // ACT:
        List<Producto> resultado = productoService.obtenerTodosLosProductos();

        // ASSERT:
        assertTrue(resultado.isEmpty(), "La lista debe estar vacía.");
        verify(productoRepository, times(1)).findAll();
    }
}