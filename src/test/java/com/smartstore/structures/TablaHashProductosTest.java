package com.smartstore.structures;

import com.smartstore.model.Categoria;
import com.smartstore.model.Producto;
import com.smartstore.model.Proveedor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TablaHashProductosTest {

    private TablaHashProductos tabla;

    private Categoria categoria;
    private Proveedor proveedor;

    @BeforeEach
    void setUp() {

        tabla = new TablaHashProductos();

        categoria = new Categoria(
                1,
                "Tecnologia",
                "Productos tecnologicos",
                true
        );

        proveedor = new Proveedor(
                1,
                "HP Colombia",
                "900123456",
                "3201112233",
                "ventas@hp.com",
                "Bogota",
                true
        );
    }

    private Producto crearProducto(String codigo) {

        return new Producto(
                codigo,
                "Laptop HP",
                "Laptop para trabajo",
                2500000,
                10,
                2,
                categoria,
                proveedor
        );
    }

    @Test
    void tablaDebeIniciarVacia() {

        assertTrue(tabla.isEmpty());
        assertEquals(0, tabla.size());
    }

    @Test
    void agregarProductoCorrectamente() {

        Producto producto = crearProducto("P001");

        tabla.agregar(producto);

        assertEquals(1, tabla.size());
        assertTrue(tabla.contiene("P001"));
    }

    @Test
    void buscarProductoPorCodigo() {

        Producto producto = crearProducto("P001");

        tabla.agregar(producto);

        Producto resultado =
                tabla.buscar("P001");

        assertNotNull(resultado);
        assertEquals(
                "P001",
                resultado.getCodigo()
        );
    }

    @Test
    void buscarProductoInexistenteDebeRetornarNull() {

        assertNull(
                tabla.buscar("P999")
        );
    }

    @Test
    void noDebePermitirCodigoDuplicado() {

        Producto producto1 =
                crearProducto("P001");

        Producto producto2 =
                crearProducto("P001");

        tabla.agregar(producto1);

        assertThrows(
                IllegalArgumentException.class,
                () -> tabla.agregar(producto2)
        );
    }

    @Test
    void eliminarProductoCorrectamente() {

        Producto producto =
                crearProducto("P001");

        tabla.agregar(producto);

        Producto eliminado =
                tabla.eliminar("P001");

        assertEquals(
                producto,
                eliminado
        );

        assertFalse(
                tabla.contiene("P001")
        );

        assertEquals(
                0,
                tabla.size()
        );
    }

    @Test
    void codigoVacioDebeLanzarExcepcionAlBuscar() {

        assertThrows(
                IllegalArgumentException.class,
                () -> tabla.buscar("")
        );
    }

    @Test
    void codigoNuloDebeLanzarExcepcionAlBuscar() {

        assertThrows(
                IllegalArgumentException.class,
                () -> tabla.buscar(null)
        );
    }

    @Test
    void productoNuloDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> tabla.agregar(null)
        );
    }

    @Test
    void clearDebeVaciarLaTabla() {

        tabla.agregar(
                crearProducto("P001")
        );

        tabla.agregar(
                crearProducto("P002")
        );

        tabla.clear();

        assertTrue(tabla.isEmpty());
        assertEquals(0, tabla.size());
    }
}