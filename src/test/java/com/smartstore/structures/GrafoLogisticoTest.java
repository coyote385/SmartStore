package com.smartstore.structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    void bfsDebeRecorrerElGrafo() {

        grafo.agregarConexion(
                "A",
                "B"
        );

        grafo.agregarConexion(
                "A",
                "C"
        );

        grafo.agregarConexion(
                "B",
                "D"
        );

        List<String> recorrido =
                grafo.bfs("A");

        assertEquals(
                "A",
                recorrido.get(0)
        );

        assertTrue(
                recorrido.contains("B")
        );

        assertTrue(
                recorrido.contains("C")
        );

        assertTrue(
                recorrido.contains("D")
        );

        assertEquals(
                4,
                recorrido.size()
        );
    }

    @Test
    void dfsDebeRecorrerElGrafo() {

        grafo.agregarConexion(
                "A",
                "B"
        );

        grafo.agregarConexion(
                "A",
                "C"
        );

        grafo.agregarConexion(
                "B",
                "D"
        );

        List<String> recorrido =
                grafo.dfs("A");

        assertEquals(
                "A",
                recorrido.get(0)
        );

        assertTrue(
                recorrido.contains("B")
        );

        assertTrue(
                recorrido.contains("C")
        );

        assertTrue(
                recorrido.contains("D")
        );

        assertEquals(
                4,
                recorrido.size()
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
    void bfsDePuntoInexistenteDebeRetornarListaVacia() {

        List<String> resultado =
                grafo.bfs("Inexistente");

        assertTrue(resultado.isEmpty());
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