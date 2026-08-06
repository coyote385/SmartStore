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
                true);

        categoria = new Categoria(
                1,
                "Tecnología",
                "Periféricos",
                true);

        proveedor = new Proveedor(
                1,
                "Logitech",
                "123",
                "300",
                "correo@mail.com",
                "Bogotá",
                true);

        producto = new Producto(
                "P001",
                "Mouse",
                "RGB",
                80000,
                20,
                5,
                categoria,
                proveedor);

    }

    @Test
    void crearPedido() {

        Pedido pedido =
                new Pedido(1, cliente, "Pendiente");

        assertEquals(cliente, pedido.getCliente());

    }

    @Test
    void agregarDetalle() {

        Pedido pedido =
                new Pedido(1, cliente, "Pendiente");

        pedido.agregarDetalle(
                new DetallePedido(producto,2,80000));

        assertEquals(1,
                pedido.cantidadProductos());

    }

    @Test
    void calcularTotal() {

        Pedido pedido =
                new Pedido(1, cliente, "Pendiente");

        pedido.agregarDetalle(
                new DetallePedido(producto,2,80000));

        pedido.agregarDetalle(
                new DetallePedido(producto,1,80000));

        assertEquals(
                240000,
                pedido.calcularTotal());

    }

    @Test
    void eliminarDetalle() {

        Pedido pedido =
                new Pedido(1, cliente, "Pendiente");

        DetallePedido detalle =
                new DetallePedido(producto,1,80000);

        pedido.agregarDetalle(detalle);

        assertTrue(
                pedido.eliminarDetalle(detalle));

    }

    @Test
    void cambiarEstado() {

        Pedido pedido =
                new Pedido(1, cliente, "Pendiente");

        pedido.setEstado("Facturado");

        assertEquals(
                "Facturado",
                pedido.getEstado());

    }

}