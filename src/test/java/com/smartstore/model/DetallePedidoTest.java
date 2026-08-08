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
                true
        );

        proveedor = new Proveedor(
                1,
                "Logitech",
                "123456",
                "3001234567",
                "logitech@mail.com",
                "Bogotá",
                true
        );

        producto = new Producto(
                "P001",
                "Mouse Gamer",
                "RGB",
                80000,
                20,
                5,
                categoria,
                proveedor
        );
    }

    @Test
    void crearDetalleCorrectamente() {

        DetallePedido detalle =
                new DetallePedido(producto, 2, 80000);

        assertEquals(producto, detalle.getProducto());
        assertEquals(2, detalle.getCantidad());
        assertEquals(80000, detalle.getPrecioUnitario());
    }

    @Test
    void cambiarProducto() {

        DetallePedido detalle =
                new DetallePedido(producto, 2, 80000);

        Producto nuevoProducto = new Producto(
                "P002",
                "Teclado",
                "Mecánico",
                150000,
                10,
                2,
                categoria,
                proveedor
        );

        detalle.setProducto(nuevoProducto);

        assertEquals(
                nuevoProducto,
                detalle.getProducto()
        );
    }

    @Test
    void productoNuloDebeLanzarExcepcion() {

        DetallePedido detalle =
                new DetallePedido(producto, 2, 80000);

        assertThrows(
                IllegalArgumentException.class,
                () -> detalle.setProducto(null)
        );
    }

    @Test
    void cambiarCantidad() {

        DetallePedido detalle =
                new DetallePedido(producto, 1, 80000);

        detalle.setCantidad(5);

        assertEquals(
                5,
                detalle.getCantidad()
        );
    }

    @Test
    void cantidadCeroDebeLanzarExcepcion() {

        DetallePedido detalle =
                new DetallePedido(producto, 1, 80000);

        assertThrows(
                IllegalArgumentException.class,
                () -> detalle.setCantidad(0)
        );
    }

    @Test
    void cantidadNegativaDebeLanzarExcepcion() {

        DetallePedido detalle =
                new DetallePedido(producto, 1, 80000);

        assertThrows(
                IllegalArgumentException.class,
                () -> detalle.setCantidad(-1)
        );
    }

    @Test
    void cambiarPrecioUnitario() {

        DetallePedido detalle =
                new DetallePedido(producto, 2, 80000);

        detalle.setPrecioUnitario(100000);

        assertEquals(
                100000,
                detalle.getPrecioUnitario()
        );
    }

    @Test
    void precioCeroDebeLanzarExcepcion() {

        DetallePedido detalle =
                new DetallePedido(producto, 1, 80000);

        assertThrows(
                IllegalArgumentException.class,
                () -> detalle.setPrecioUnitario(0)
        );
    }

    @Test
    void precioNegativoDebeLanzarExcepcion() {

        DetallePedido detalle =
                new DetallePedido(producto, 1, 80000);

        assertThrows(
                IllegalArgumentException.class,
                () -> detalle.setPrecioUnitario(-100)
        );
    }

    @Test
    void calcularSubtotal() {

        DetallePedido detalle =
                new DetallePedido(producto, 3, 80000);

        assertEquals(
                240000,
                detalle.calcularSubtotal()
        );
    }

    @Test
    void verificarToString() {

        DetallePedido detalle =
                new DetallePedido(producto, 2, 80000);

        String resultado = detalle.toString();

        assertTrue(resultado.contains("Mouse Gamer"));
        assertTrue(resultado.contains("2"));
        assertTrue(resultado.contains("80000"));
        assertTrue(resultado.contains("160000"));
    }
}