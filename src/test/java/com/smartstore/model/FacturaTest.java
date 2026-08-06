package com.smartstore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FacturaTest {

    private Pedido pedido;

    @BeforeEach
    void setUp() {

        Cliente cliente = new Cliente(
                1,
                "Jonathan",
                "Mendez",
                "123",
                "300",
                "correo@mail.com",
                "Ibagué",
                true);

        Categoria categoria = new Categoria(
                1,
                "Tecnología",
                "Periféricos",
                true);

        Proveedor proveedor = new Proveedor(
                1,
                "Logitech",
                "123",
                "300",
                "correo@mail.com",
                "Bogotá",
                true);

        Producto producto = new Producto(
                "P001",
                "Mouse",
                "RGB",
                100000,
                10,
                2,
                categoria,
                proveedor);

        pedido = new Pedido(1, cliente, "Pendiente");

        pedido.agregarDetalle(
                new DetallePedido(
                        producto,
                        2,
                        100000));

    }

    @Test
    void crearFactura() {

        Factura factura = new Factura(
                1,
                pedido,
                0.19);

        assertEquals(1, factura.getNumero());

    }

    @Test
    void calcularIVA() {

        Factura factura = new Factura(
                1,
                pedido,
                0.19);

        assertEquals(
                38000,
                factura.calcularIVA());

    }

    @Test
    void calcularTotal() {

        Factura factura = new Factura(
                1,
                pedido,
                0.19);

        assertEquals(
                238000,
                factura.calcularTotal());

    }

    @Test
    void modificarIVA() {

        Factura factura = new Factura(
                1,
                pedido,
                0.19);

        factura.setIva(0.10);

        assertEquals(
                0.10,
                factura.getIva());

    }

    @Test
    void ivaNegativoDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Factura(
                        1,
                        pedido,
                        -0.10));

    }

}