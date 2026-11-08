package com.smartstore.algorithms;

import com.smartstore.model.Categoria;
import com.smartstore.model.Cliente;
import com.smartstore.model.Producto;
import com.smartstore.model.Proveedor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrdenamientosTest {

    @Test
    void bubbleSortDebeOrdenarAscendente() {

        int[] datos = {
                50, 20, 40, 10, 30
        };

        Ordenamientos.bubbleSort(
                datos,
                true
        );

        assertArrayEquals(
                new int[]{10, 20, 30, 40, 50},
                datos
        );
    }

    @Test
    void bubbleSortDebeOrdenarDescendente() {

        int[] datos = {
                10, 50, 20, 40, 30
        };

        Ordenamientos.bubbleSort(
                datos,
                false
        );

        assertArrayEquals(
                new int[]{50, 40, 30, 20, 10},
                datos
        );
    }

    @Test
    void bubbleSortDebeManejarElementosRepetidos() {

        int[] datos = {
                30, 10, 30, 20, 10
        };

        Ordenamientos.bubbleSort(
                datos,
                true
        );

        assertArrayEquals(
                new int[]{10, 10, 20, 30, 30},
                datos
        );
    }

    @Test
    void bubbleSortArregloVacio() {

        int[] datos = {};

        Ordenamientos.bubbleSort(
                datos,
                true
        );

        assertArrayEquals(
                new int[]{},
                datos
        );
    }

    @Test
    void quickSortDebeOrdenarAscendente() {

        int[] datos = {
                50, 20, 40, 10, 30
        };

        Ordenamientos.quickSort(
                datos,
                true
        );

        assertArrayEquals(
                new int[]{10, 20, 30, 40, 50},
                datos
        );
    }

    @Test
    void quickSortDebeOrdenarDescendente() {

        int[] datos = {
                10, 50, 20, 40, 30
        };

        Ordenamientos.quickSort(
                datos,
                false
        );

        assertArrayEquals(
                new int[]{50, 40, 30, 20, 10},
                datos
        );
    }

    @Test
    void quickSortDebeManejarElementosRepetidos() {

        int[] datos = {
                30, 10, 30, 20, 10
        };

        Ordenamientos.quickSort(
                datos,
                true
        );

        assertArrayEquals(
                new int[]{10, 10, 20, 30, 30},
                datos
        );
    }

    @Test
    void quickSortArregloVacio() {

        int[] datos = {};

        Ordenamientos.quickSort(
                datos,
                true
        );

        assertArrayEquals(
                new int[]{},
                datos
        );
    }

    @Test
    void bubbleSortArregloNuloDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> Ordenamientos.bubbleSort(
                        null,
                        true
                )
        );
    }

    @Test
    void quickSortArregloNuloDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> Ordenamientos.quickSort(
                        null,
                        true
                )
        );
    }

    private List<Producto> crearListaProductos() {
        Categoria cat = new Categoria(1, "Cat", "Desc", true);
        Proveedor prov = new Proveedor(1, "Prov", "123", "300", "p@p.com", "Bogota", true);

        return new ArrayList<>(List.of(
                new Producto("P003", "Mouse", "Optico", 50000.0, 20, 5, cat, prov),
                new Producto("P001", "Teclado", "Mecanico", 150000.0, 10, 2, cat, prov),
                new Producto("P002", "Monitor", "LED 24", 600000.0, 5, 1, cat, prov)
        ));
    }

    @Test
    void testBubbleSortProductosPorPrecio() {
        List<Producto> lista = crearListaProductos();

        Ordenamientos.bubbleSortProductosPorPrecio(lista, true);
        assertEquals(50000.0, lista.get(0).getPrecio());
        assertEquals(150000.0, lista.get(1).getPrecio());
        assertEquals(600000.0, lista.get(2).getPrecio());

        Ordenamientos.bubbleSortProductosPorPrecio(lista, false);
        assertEquals(600000.0, lista.get(0).getPrecio());
        assertEquals(50000.0, lista.get(2).getPrecio());

        assertThrows(IllegalArgumentException.class, () -> Ordenamientos.bubbleSortProductosPorPrecio(null, true));
    }

    @Test
    void testQuickSortProductosPorPrecio() {
        List<Producto> lista = crearListaProductos();

        Ordenamientos.quickSortProductosPorPrecio(lista, true);
        assertEquals(50000.0, lista.get(0).getPrecio());
        assertEquals(600000.0, lista.get(2).getPrecio());

        Ordenamientos.quickSortProductosPorPrecio(lista, false);
        assertEquals(600000.0, lista.get(0).getPrecio());
        assertEquals(50000.0, lista.get(2).getPrecio());

        assertThrows(IllegalArgumentException.class, () -> Ordenamientos.quickSortProductosPorPrecio(null, true));
    }

    @Test
    void testQuickSortProductosPorNombreYCodigo() {
        List<Producto> lista = crearListaProductos();

        Ordenamientos.quickSortProductosPorNombre(lista, true);
        assertEquals("Monitor", lista.get(0).getNombre());
        assertEquals("Mouse", lista.get(1).getNombre());
        assertEquals("Teclado", lista.get(2).getNombre());

        Ordenamientos.quickSortProductosPorCodigo(lista, true);
        assertEquals("P001", lista.get(0).getCodigo());
        assertEquals("P002", lista.get(1).getCodigo());
        assertEquals("P003", lista.get(2).getCodigo());

        assertThrows(IllegalArgumentException.class, () -> Ordenamientos.quickSortProductosPorNombre(null, true));
        assertThrows(IllegalArgumentException.class, () -> Ordenamientos.quickSortProductosPorCodigo(null, true));
    }

    @Test
    void testQuickSortClientesPorNombreYCedula() {
        List<Cliente> clientes = new ArrayList<>(List.of(
                new Cliente(3, "Zulma", "Rios", "300", "302", "z@z.com", "Bogota", true),
                new Cliente(1, "Ana", "Gomez", "100", "300", "a@a.com", "Cali", true),
                new Cliente(2, "Carlos", "Lopez", "200", "301", "c@c.com", "Medellin", true)
        ));

        Ordenamientos.quickSortClientesPorNombre(clientes, true);
        assertEquals("Ana", clientes.get(0).getNombre());
        assertEquals("Carlos", clientes.get(1).getNombre());
        assertEquals("Zulma", clientes.get(2).getNombre());

        Ordenamientos.quickSortClientesPorCedula(clientes, true);
        assertEquals("100", clientes.get(0).getCedula());
        assertEquals("200", clientes.get(1).getCedula());
        assertEquals("300", clientes.get(2).getCedula());

        assertThrows(IllegalArgumentException.class, () -> Ordenamientos.quickSortClientesPorNombre(null, true));
        assertThrows(IllegalArgumentException.class, () -> Ordenamientos.quickSortClientesPorCedula(null, true));
    }
}