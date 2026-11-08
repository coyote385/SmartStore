package com.smartstore.algorithms;

import com.smartstore.model.Categoria;
import com.smartstore.model.Cliente;
import com.smartstore.model.Producto;
import com.smartstore.model.Proveedor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BusquedasTest {

    @Test
    void busquedaLinealDebeEncontrarElemento() {

        int[] productos = {
                10, 20, 30, 40, 50
        };

        int resultado =
                Busquedas.busquedaLineal(
                        productos,
                        30
                );

        assertEquals(2, resultado);
    }

    @Test
    void busquedaLinealDebeRetornarMenosUnoSiNoEncuentra() {

        int[] productos = {
                10, 20, 30, 40, 50
        };

        int resultado =
                Busquedas.busquedaLineal(
                        productos,
                        99
                );

        assertEquals(-1, resultado);
    }

    @Test
    void busquedaLinealDebeEncontrarPrimerElemento() {

        int[] productos = {
                10, 20, 30
        };

        assertEquals(
                0,
                Busquedas.busquedaLineal(
                        productos,
                        10
                )
        );
    }

    @Test
    void busquedaLinealDebeEncontrarUltimoElemento() {

        int[] productos = {
                10, 20, 30
        };

        assertEquals(
                2,
                Busquedas.busquedaLineal(
                        productos,
                        30
                )
        );
    }

    @Test
    void busquedaBinariaDebeEncontrarElemento() {

        int[] productos = {
                10, 20, 30, 40, 50
        };

        int resultado =
                Busquedas.busquedaBinaria(
                        productos,
                        40
                );

        assertEquals(3, resultado);
    }

    @Test
    void busquedaBinariaDebeRetornarMenosUnoSiNoEncuentra() {

        int[] productos = {
                10, 20, 30, 40, 50
        };

        int resultado =
                Busquedas.busquedaBinaria(
                        productos,
                        99
                );

        assertEquals(-1, resultado);
    }

    @Test
    void busquedaBinariaDebeEncontrarPrimerElemento() {

        int[] productos = {
                10, 20, 30, 40
        };

        assertEquals(
                0,
                Busquedas.busquedaBinaria(
                        productos,
                        10
                )
        );
    }

    @Test
    void busquedaBinariaDebeEncontrarUltimoElemento() {

        int[] productos = {
                10, 20, 30, 40
        };

        assertEquals(
                3,
                Busquedas.busquedaBinaria(
                        productos,
                        40
                )
        );
    }

    @Test
    void arregloNuloEnBusquedaLinealDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> Busquedas.busquedaLineal(
                        null,
                        10
                )
        );
    }

    @Test
    void arregloNuloEnBusquedaBinariaDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> Busquedas.busquedaBinaria(
                        null,
                        10
                )
        );
    }

    @Test
    void testBuscarProductoPorCodigoBinaria() {
        Categoria cat = new Categoria(1, "Cat", "Desc", true);
        Proveedor prov = new Proveedor(1, "Prov", "123", "300", "prov@test.com", "Bogota", true);

        List<Producto> productos = new ArrayList<>(List.of(
                new Producto("P001", "Prod A", "Desc", 100.0, 5, 1, cat, prov),
                new Producto("P002", "Prod B", "Desc", 200.0, 5, 1, cat, prov),
                new Producto("P003", "Prod C", "Desc", 300.0, 5, 1, cat, prov)
        ));

        Producto encontrado = Busquedas.buscarProductoPorCodigoBinaria(productos, "P002");
        assertNotNull(encontrado);
        assertEquals("P002", encontrado.getCodigo());

        assertNull(Busquedas.buscarProductoPorCodigoBinaria(productos, "P999"));
        assertNull(Busquedas.buscarProductoPorCodigoBinaria(productos, null));
        assertNull(Busquedas.buscarProductoPorCodigoBinaria(productos, "   "));
        assertThrows(IllegalArgumentException.class, () -> Busquedas.buscarProductoPorCodigoBinaria(null, "P001"));
    }

    @Test
    void testBuscarClientePorCedulaBinaria() {
        List<Cliente> clientes = new ArrayList<>(List.of(
                new Cliente(1, "Ana", "Gomez", "100", "300", "ana@test.com", "Cali", true),
                new Cliente(2, "Carlos", "Lopez", "200", "301", "carlos@test.com", "Medellin", true),
                new Cliente(3, "Zulma", "Rios", "300", "302", "zulma@test.com", "Bogota", true)
        ));

        Cliente c = Busquedas.buscarClientePorCedulaBinaria(clientes, "200");
        assertNotNull(c);
        assertEquals("Carlos", c.getNombre());

        assertNull(Busquedas.buscarClientePorCedulaBinaria(clientes, "999"));
        assertNull(Busquedas.buscarClientePorCedulaBinaria(clientes, null));
        assertNull(Busquedas.buscarClientePorCedulaBinaria(clientes, "   "));
        assertThrows(IllegalArgumentException.class, () -> Busquedas.buscarClientePorCedulaBinaria(null, "100"));
    }
}