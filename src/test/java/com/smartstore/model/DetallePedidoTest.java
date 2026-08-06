package com.smartstore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DetallePedidoTest {

    private Categoria categoria;
    private Proveedor proveedor;
    private Producto producto;

    @BeforeEach
    void setUp() {

        categoria = new Categoria(
                1,
                "Tecnología",
                "Periféricos",
                true);

        proveedor = new Proveedor(
                1,
                "Logitech",
                "123456",
                "3001234567",
                "logitech@mail.com",
                "Bogotá",
                true);

        producto = new Producto(
                "P001",
                "Mouse Gamer",
                "RGB",
                80000,
                20,
                5,
                categoria,
                proveedor);

    }

    @Test
    void crearDetalleCorrectamente() {

        DetallePedido detalle =
                new DetallePedido(producto,2,80000);

        assertEquals(2, detalle.getCantidad());

    }

    @Test
    void calcularSubtotal() {

        DetallePedido detalle =
                new DetallePedido(producto,3,80000);

        assertEquals(
                240000,
                detalle.calcularSubtotal());

    }

    @Test
    void cambiarCantidad() {

        DetallePedido detalle =
                new DetallePedido(producto,1,80000);

        detalle.setCantidad(5);

        assertEquals(5,
                detalle.getCantidad());

    }

    @Test
    void cantidadInvalida() {

        DetallePedido detalle =
                new DetallePedido(producto,1,80000);

        assertThrows(
                IllegalArgumentException.class,
                () -> detalle.setCantidad(0));

    }

    @Test
    void precioInvalido() {

        DetallePedido detalle =
                new DetallePedido(producto,1,80000);

        assertThrows(
                IllegalArgumentException.class,
                () -> detalle.setPrecioUnitario(-100));

    }

}