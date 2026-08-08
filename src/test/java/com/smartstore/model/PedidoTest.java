package com.smartstore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    private Cliente cliente;
    private Categoria categoria;
    private Proveedor proveedor;
    private Producto producto;

    @BeforeEach
    void setUp() {

        cliente = new Cliente(
                1,
                "Jonathan",
                "Mendez",
                "123",
                "300",
                "correo@mail.com",
                "Ibagué",
                true
        );

        categoria = new Categoria(
                1,
                "Tecnología",
                "Periféricos",
                true
        );

        proveedor = new Proveedor(
                1,
                "Logitech",
                "123",
                "300",
                "correo@mail.com",
                "Bogotá",
                true
        );

        producto = new Producto(
                "P001",
                "Mouse",
                "RGB",
                80000,
                20,
                5,
                categoria,
                proveedor
        );
    }

    @Test
    void crearPedidoCorrectamente() {

        Pedido pedido =
                new Pedido(
                        1,
                        cliente,
                        "Pendiente"
                );

        assertEquals(
                1,
                pedido.getId()
        );

        assertEquals(
                cliente,
                pedido.getCliente()
        );

        assertEquals(
                "Pendiente",
                pedido.getEstado()
        );

        assertNotNull(
                pedido.getFecha()
        );

        assertNotNull(
                pedido.getDetalles()
        );

        assertTrue(
                pedido.getDetalles().isEmpty()
        );
    }

    @Test
    void agregarDetalle() {

        Pedido pedido =
                new Pedido(
                        1,
                        cliente,
                        "Pendiente"
                );

        DetallePedido detalle =
                new DetallePedido(
                        producto,
                        2,
                        80000
                );

        pedido.agregarDetalle(detalle);

        assertEquals(
                1,
                pedido.cantidadProductos()
        );

        assertEquals(
                detalle,
                pedido.getDetalles().get(0)
        );
    }

    @Test
    void detalleNuloDebeLanzarExcepcion() {

        Pedido pedido =
                new Pedido(
                        1,
                        cliente,
                        "Pendiente"
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> pedido.agregarDetalle(null)
        );
    }

    @Test
    void calcularTotalSinDetalles() {

        Pedido pedido =
                new Pedido(
                        1,
                        cliente,
                        "Pendiente"
                );

        assertEquals(
                0,
                pedido.calcularTotal()
        );
    }

    @Test
    void calcularTotal() {

        Pedido pedido =
                new Pedido(
                        1,
                        cliente,
                        "Pendiente"
                );

        pedido.agregarDetalle(
                new DetallePedido(
                        producto,
                        2,
                        80000
                )
        );

        pedido.agregarDetalle(
                new DetallePedido(
                        producto,
                        1,
                        80000
                )
        );

        assertEquals(
                240000,
                pedido.calcularTotal()
        );
    }

    @Test
    void eliminarDetalleExistente() {

        Pedido pedido =
                new Pedido(
                        1,
                        cliente,
                        "Pendiente"
                );

        DetallePedido detalle =
                new DetallePedido(
                        producto,
                        1,
                        80000
                );

        pedido.agregarDetalle(detalle);

        assertTrue(
                pedido.eliminarDetalle(detalle)
        );

        assertEquals(
                0,
                pedido.cantidadProductos()
        );
    }

    @Test
    void eliminarDetalleInexistente() {

        Pedido pedido =
                new Pedido(
                        1,
                        cliente,
                        "Pendiente"
                );

        DetallePedido detalle =
                new DetallePedido(
                        producto,
                        1,
                        80000
                );

        assertFalse(
                pedido.eliminarDetalle(detalle)
        );
    }

    @Test
    void cambiarEstado() {

        Pedido pedido =
                new Pedido(
                        1,
                        cliente,
                        "Pendiente"
                );

        pedido.setEstado("Facturado");

        assertEquals(
                "Facturado",
                pedido.getEstado()
        );
    }

    @Test
    void estadoNuloDebeLanzarExcepcion() {

        Pedido pedido =
                new Pedido(
                        1,
                        cliente,
                        "Pendiente"
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> pedido.setEstado(null)
        );
    }

    @Test
    void estadoVacioDebeLanzarExcepcion() {

        Pedido pedido =
                new Pedido(
                        1,
                        cliente,
                        "Pendiente"
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> pedido.setEstado("")
        );
    }

    @Test
    void verificarToString() {

        Pedido pedido =
                new Pedido(
                        1,
                        cliente,
                        "Pendiente"
                );

        pedido.agregarDetalle(
                new DetallePedido(
                        producto,
                        2,
                        80000
                )
        );

        String resultado =
                pedido.toString();

        assertTrue(
                resultado.contains("1")
        );

        assertTrue(
                resultado.contains("Jonathan")
        );

        assertTrue(
                resultado.contains("Pendiente")
        );

        assertTrue(
                resultado.contains("160000")
        );
    }
}