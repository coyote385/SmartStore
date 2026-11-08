package com.smartstore.service;

import com.smartstore.model.Categoria;
import com.smartstore.model.Cliente;
import com.smartstore.model.DetallePedido;
import com.smartstore.model.Factura;
import com.smartstore.model.Pedido;
import com.smartstore.model.Producto;
import com.smartstore.model.Proveedor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoServiceTest {

    private PedidoService service;

    private Cliente cliente;
    private Categoria categoria;
    private Proveedor proveedor;
    private Producto producto;
    private Pedido pedido;
    private DetallePedido detalle;

    @BeforeEach
    void setUp() {

        service = new PedidoService();

        cliente = new Cliente(
                1,
                "Jonathan",
                "Mendez",
                "123456",
                "3001234567",
                "jonathan@email.com",
                "Ibague",
                true
        );

        categoria = new Categoria(
                1,
                "Tecnologia",
                "Productos tecnologicos",
                true
        );

        proveedor = new Proveedor(
                1,
                "Lenovo",
                "900123456",
                "3111111111",
                "contacto@lenovo.com",
                "Bogota",
                true
        );

        producto = new Producto(
                "P001",
                "Portatil",
                "Laptop Lenovo",
                2500000,
                20,
                5,
                categoria,
                proveedor
        );

        pedido = new Pedido(
                1,
                cliente,
                "Pendiente"
        );

        detalle = new DetallePedido(
                producto,
                2,
                producto.getPrecio()
        );
    }

    @Test
    void crearPedidoCorrectamente() {

        service.crearPedido(pedido);

        assertEquals(
                1,
                service.cantidadPedidos()
        );
    }

    @Test
    void noDebePermitirPedidoDuplicado() {

        service.crearPedido(pedido);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.crearPedido(pedido)
        );
    }

    @Test
    void buscarPedidoPorId() {

        service.crearPedido(pedido);

        Pedido encontrado =
                service.buscarPedido(1);

        assertNotNull(encontrado);

        assertEquals(
                1,
                encontrado.getId()
        );
    }

    @Test
    void eliminarPedido() {

        service.crearPedido(pedido);

        assertTrue(
                service.eliminarPedido(1)
        );

        assertEquals(
                0,
                service.cantidadPedidos()
        );
    }

    @Test
    void agregarProductoAlPedido() {

        service.agregarProducto(
                pedido,
                detalle
        );

        assertEquals(
                1,
                pedido.cantidadProductos()
        );
    }

    @Test
    void quitarProductoDelPedido() {

        service.agregarProducto(
                pedido,
                detalle
        );

        assertTrue(
                service.quitarProducto(
                        pedido,
                        detalle
                )
        );

        assertEquals(
                0,
                pedido.cantidadProductos()
        );
    }

    @Test
    void calcularTotalPedido() {

        service.agregarProducto(
                pedido,
                detalle
        );

        assertEquals(
                5000000,
                service.calcularTotal(pedido)
        );
    }

    @Test
    void generarFactura() {

        service.agregarProducto(
                pedido,
                detalle
        );

        Factura factura =
                service.generarFactura(pedido);

        assertNotNull(factura);

        assertEquals(
                1,
                factura.getNumero()
        );
    }

    @Test
    void cancelarPedido() {

        service.cancelarPedido(pedido);

        assertEquals(
                "Cancelado",
                pedido.getEstado()
        );
    }

    @Test
    void buscarPedidosPorCliente() {

        service.crearPedido(pedido);

        List<Pedido> pedidos =
                service.buscarPedidosPorCliente(cliente);

        assertEquals(
                1,
                pedidos.size()
        );
    }

    @Test
    void listarPedidos() {

        service.crearPedido(pedido);

        List<Pedido> pedidos =
                service.listarPedidos();

        assertEquals(
                1,
                pedidos.size()
        );
    }

    @Test
    void testCargarPedidosYLimpiar() {
        Pedido p1 = new Pedido(1, cliente, "Pendiente");
        Pedido p2 = new Pedido(2, cliente, "Procesado");

        service.cargarPedidos(List.of(p1, p2));
        assertEquals(2, service.cantidadPedidos());

        service.limpiar();
        assertEquals(0, service.cantidadPedidos());

        service.cargarPedidos(null);
        assertEquals(0, service.cantidadPedidos());
    }

    @Test
    void testIntegracionColaPendientesYDespacho() {
        Pedido p1 = new Pedido(10, cliente, "Pendiente");
        service.crearPedido(p1);

        assertTrue(service.tienePedidosPendientes());
        assertEquals(1, service.cantidadPedidosPendientes());
        assertEquals(p1, service.consultarProximoPedido());

        Pedido despachado = service.despacharSiguientePedido();
        assertNotNull(despachado);
        assertEquals("Despachado", despachado.getEstado());
        assertEquals(0, service.cantidadPedidosPendientes());
        assertNull(service.despacharSiguientePedido());

        assertNotNull(service.getHistorial());
        assertTrue(service.obtenerUltimaOperacion().contains("Despachado"));
    }
}