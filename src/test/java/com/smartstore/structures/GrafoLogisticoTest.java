package com.smartstore.structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrafoLogisticoTest {

    private GrafoLogistico grafo;

    @BeforeEach
    void setUp() {
        grafo = new GrafoLogistico();
    }

    @Test
    void grafoDebeIniciarVacio() {

        assertTrue(grafo.isEmpty());
        assertEquals(0, grafo.cantidadPuntos());
    }

    @Test
    void agregarPuntoCorrectamente() {

        grafo.agregarPunto("Bodega");

        assertTrue(
                grafo.contienePunto("Bodega")
        );

        assertEquals(
                1,
                grafo.cantidadPuntos()
        );
    }

    @Test
    void agregarConexionDebeCrearLosPuntos() {

        grafo.agregarConexion(
                "Bodega",
                "Tienda"
        );

        assertTrue(
                grafo.contienePunto("Bodega")
        );

        assertTrue(
                grafo.contienePunto("Tienda")
        );
    }

    @Test
    void conexionDebeSerBidireccional() {

        grafo.agregarConexion(
                "Bodega",
                "Tienda"
        );

        assertTrue(
                grafo.obtenerVecinos("Bodega")
                        .contains("Tienda")
        );

        assertTrue(
                grafo.obtenerVecinos("Tienda")
                        .contains("Bodega")
        );
    }

    @Test
    void puntoNuloDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> grafo.agregarPunto(null)
        );
    }

    @Test
    void puntoVacioDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> grafo.agregarPunto("")
        );
    }

    @Test
    void clearDebeVaciarElGrafo() {

        grafo.agregarConexion(
                "A",
                "B"
        );

        grafo.clear();

        assertTrue(grafo.isEmpty());
        assertEquals(0, grafo.cantidadPuntos());
    }
}