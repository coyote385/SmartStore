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
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para PersistenciaService.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class PersistenciaServiceTest {

    @TempDir
    Path tempDir;

    private PersistenciaService persistenciaService;

    @BeforeEach
    void setUp() {
        persistenciaService = new PersistenciaService(tempDir.toString());
    }

    private Cliente crearCliente() {
        return new Cliente(
                1,
                "Jonathan",
                "Mendez",
                "123456",
                "3001234567",
                "jonathan@email.com",
                "Ibague",
                true
        );
    }

    private Producto crearProducto() {
        Categoria categoria = new Categoria(1, "Tecnologia", "Dispositivos electronicos", true);
        Proveedor proveedor = new Proveedor(1, "Sony", "NIT-900", "3009999", "sony@smartstore.com", "Bogota", true);
        return new Producto(
                "P001",
                "Laptop Gamer",
                "Portatil de alto rendimiento",
                3500000.0,
                10,
                2,
                categoria,
                proveedor
        );
    }

    @Test
    void testConstructorDirectorioInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new PersistenciaService(null));
        assertThrows(IllegalArgumentException.class, () -> new PersistenciaService("   "));
    }

    @Test
    void testConstructorPorDefecto() {
        PersistenciaService servicioDefecto = new PersistenciaService();
        assertNotNull(servicioDefecto.getDirectorioBase());
        assertFalse(servicioDefecto.getDirectorioBase().isBlank());
    }

    @Test
    void testPersistirYRecuperarClientes() throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(crearCliente());

        persistenciaService.guardarClientes(clientes);
        List<Cliente> recuperados = persistenciaService.cargarClientes();

        assertNotNull(recuperados);
        assertEquals(1, recuperados.size());
        assertEquals("Jonathan", recuperados.get(0).getNombre());
        assertEquals("123456", recuperados.get(0).getCedula());
    }

    @Test
    void testPersistirYRecuperarProductos() throws Exception {
        List<Producto> productos = new ArrayList<>();
        productos.add(crearProducto());

        persistenciaService.guardarProductos(productos);
        List<Producto> recuperados = persistenciaService.cargarProductos();

        assertNotNull(recuperados);
        assertEquals(1, recuperados.size());
        assertEquals("P001", recuperados.get(0).getCodigo());
        assertEquals("Laptop Gamer", recuperados.get(0).getNombre());
        assertEquals("Tecnologia", recuperados.get(0).getCategoria().getNombre());
        assertEquals("Sony", recuperados.get(0).getProveedor().getNombre());
    }

    @Test
    void testPersistirYRecuperarPedidos() throws Exception {
        Cliente cliente = crearCliente();
        Producto producto = crearProducto();

        Pedido pedido = new Pedido(100, cliente, "Pendiente");
        pedido.agregarDetalle(new DetallePedido(producto, 2, producto.getPrecio()));

        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(pedido);

        persistenciaService.guardarPedidos(pedidos);
        List<Pedido> recuperados = persistenciaService.cargarPedidos();

        assertNotNull(recuperados);
        assertEquals(1, recuperados.size());
        assertEquals(100, recuperados.get(0).getId());
        assertEquals("Jonathan", recuperados.get(0).getCliente().getNombre());
        assertEquals(1, recuperados.get(0).getDetalles().size());
        assertEquals(2, recuperados.get(0).getDetalles().get(0).getCantidad());
    }

    @Test
    void testPersistirYRecuperarFacturas() throws Exception {
        Cliente cliente = crearCliente();
        Producto producto = crearProducto();
        Pedido pedido = new Pedido(1, cliente, "Facturado");
        pedido.agregarDetalle(new DetallePedido(producto, 1, 100000.0));

        Factura factura = new Factura(500, pedido, 0.19);
        List<Factura> facturas = new ArrayList<>();
        facturas.add(factura);

        persistenciaService.guardarFacturas(facturas);
        List<Factura> recuperadas = persistenciaService.cargarFacturas();

        assertNotNull(recuperadas);
        assertEquals(1, recuperadas.size());
        assertEquals(500, recuperadas.get(0).getNumero());
        assertEquals(0.19, recuperadas.get(0).getIva());
    }

    @Test
    void testCargarArchivoInexistenteDevuelveListaVacia() throws Exception {
        List<Cliente> clientes = persistenciaService.cargarClientes();
        assertNotNull(clientes);
        assertTrue(clientes.isEmpty());
    }

    @Test
    void testCargarArchivoVacioDevuelveListaVacia() throws Exception {
        File archivo = persistenciaService.obtenerArchivo(PersistenciaService.ARCHIVO_CLIENTES);
        assertTrue(archivo.createNewFile());
        assertEquals(0, archivo.length());

        List<Cliente> clientes = persistenciaService.cargarClientes();
        assertNotNull(clientes);
        assertTrue(clientes.isEmpty());
    }

    @Test
    void testGuardarListasNulasGuardaListaVacia() throws Exception {
        persistenciaService.guardarClientes(null);
        persistenciaService.guardarProductos(null);
        persistenciaService.guardarPedidos(null);
        persistenciaService.guardarFacturas(null);

        assertTrue(persistenciaService.cargarClientes().isEmpty());
        assertTrue(persistenciaService.cargarProductos().isEmpty());
        assertTrue(persistenciaService.cargarPedidos().isEmpty());
        assertTrue(persistenciaService.cargarFacturas().isEmpty());
    }
}
