package com.smartstore.service;

import com.smartstore.exceptions.CodigoDuplicadoException;
import com.smartstore.exceptions.ProductoNoEncontradoException;
import com.smartstore.model.Categoria;
import com.smartstore.model.Producto;
import com.smartstore.model.Proveedor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para InventarioService.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class InventarioServiceTest {

    private InventarioService inventario;
    private Categoria categoria;
    private Proveedor proveedor;

    @BeforeEach
    void setUp() {

        inventario = new InventarioService();

        categoria = new Categoria(
                1,
                "Tecnología",
                "Equipos",
                true
        );

        proveedor = new Proveedor(
                1,
                "HP",
                "123",
                "300",
                "hp@hp.com",
                "Bogotá",
                true
        );
    }

    private Producto crearProducto(String codigo) {

        return new Producto(
                codigo,
                "Mouse",
                "USB",
                50000,
                10,
                2,
                categoria,
                proveedor
        );
    }

    @Test
    void inventarioDebeIniciarVacio() {

        assertEquals(
                0,
                inventario.cantidadProductos()
        );

        assertTrue(
                inventario.obtenerProductos().isEmpty()
        );
    }

    @Test
    void registrarProductoCorrectamente()
            throws CodigoDuplicadoException {

        Producto producto =
                crearProducto("P001");

        inventario.registrarProducto(producto);

        assertEquals(
                1,
                inventario.cantidadProductos()
        );
    }

    @Test
    void productoNuloDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> inventario.registrarProducto(null)
        );
    }

    @Test
    void noPermitirCodigoDuplicado()
            throws CodigoDuplicadoException {

        Producto p1 =
                crearProducto("P001");

        Producto p2 =
                new Producto(
                        "P001",
                        "Teclado",
                        "USB",
                        50000,
                        10,
                        2,
                        categoria,
                        proveedor
                );

        inventario.registrarProducto(p1);

        assertThrows(
                CodigoDuplicadoException.class,
                () -> inventario.registrarProducto(p2)
        );
    }

    @Test
    void buscarProductoPorCodigo()
            throws CodigoDuplicadoException {

        Producto producto =
                crearProducto("P001");

        inventario.registrarProducto(producto);

        Producto resultado =
                inventario.buscarPorCodigo("P001");

        assertNotNull(resultado);

        assertEquals(
                "P001",
                resultado.getCodigo()
        );
    }

    @Test
    void buscarProductoPorCodigoIgnoraMayusculas()
            throws CodigoDuplicadoException {

        Producto producto =
                crearProducto("P001");

        inventario.registrarProducto(producto);

        assertNotNull(
                inventario.buscarPorCodigo("p001")
        );
    }

    @Test
    void buscarProductoInexistente() {

        assertNull(
                inventario.buscarPorCodigo("P999")
        );
    }

    @Test
    void buscarCodigoNulo() {

        assertNull(
                inventario.buscarPorCodigo(null)
        );
    }

    @Test
    void buscarCodigoVacio() {

        assertNull(
                inventario.buscarPorCodigo("")
        );
    }

    @Test
    void buscarPorNombre()
            throws CodigoDuplicadoException {

        inventario.registrarProducto(
                new Producto(
                        "P001",
                        "Mouse Gamer",
                        "USB",
                        50000,
                        10,
                        2,
                        categoria,
                        proveedor
                )
        );

        List<Producto> resultado =
                inventario.buscarPorNombre("mouse");

        assertEquals(
                1,
                resultado.size()
        );
    }

    @Test
    void buscarPorNombreIgnoraMayusculas()
            throws CodigoDuplicadoException {

        inventario.registrarProducto(
                crearProducto("P001")
        );

        List<Producto> resultado =
                inventario.buscarPorNombre("MOUSE");

        assertEquals(
                1,
                resultado.size()
        );
    }

    @Test
    void buscarPorNombreInexistente()
            throws CodigoDuplicadoException {

        inventario.registrarProducto(
                crearProducto("P001")
        );

        List<Producto> resultado =
                inventario.buscarPorNombre("Laptop");

        assertTrue(resultado.isEmpty());
    }

    @Test
    void buscarPorNombreNulo() {

        List<Producto> resultado =
                inventario.buscarPorNombre(null);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void buscarPorNombreVacio() {

        List<Producto> resultado =
                inventario.buscarPorNombre("");

        assertTrue(resultado.isEmpty());
    }

    @Test
    void eliminarProducto()
            throws CodigoDuplicadoException,
            ProductoNoEncontradoException {

        Producto producto =
                crearProducto("P001");

        inventario.registrarProducto(producto);

        assertTrue(
                inventario.eliminarProducto("P001")
        );

        assertEquals(
                0,
                inventario.cantidadProductos()
        );
    }

    @Test
    void eliminarProductoInexistente() {

        assertThrows(
                ProductoNoEncontradoException.class,
                () -> inventario.eliminarProducto("P999")
        );
    }

    @Test
    void obtenerProductosDevuelveCopia()
            throws CodigoDuplicadoException {

        inventario.registrarProducto(
                crearProducto("P001")
        );

        List<Producto> productos =
                inventario.obtenerProductos();

        productos.clear();

        assertEquals(
                1,
                inventario.cantidadProductos()
        );
    }

    @Test
    void cantidadProductos()
            throws CodigoDuplicadoException {

        assertEquals(
                0,
                inventario.cantidadProductos()
        );

        inventario.registrarProducto(
                crearProducto("P001")
        );

        inventario.registrarProducto(
                crearProducto("P002")
        );

        assertEquals(
                2,
                inventario.cantidadProductos()
        );
    }
}