package com.smartstore.algorithms;

import com.smartstore.structures.GrafoLogistico;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecorridosGrafoTest {

    private GrafoLogistico grafo;

    @BeforeEach
    void setUp() {

        grafo = new GrafoLogistico();

        grafo.agregarConexion(
                "Bodega",
                "TiendaA"
        );

        grafo.agregarConexion(
                "Bodega",
                "TiendaB"
        );

        grafo.agregarConexion(
                "TiendaA",
                "ClienteA"
        );

        grafo.agregarConexion(
                "TiendaB",
                "ClienteB"
        );
    }

    @Test
    void bfsDebeRecorrerElGrafo() {

        List<String> recorrido =
                RecorridosGrafo.bfs(
                        grafo,
                        "Bodega"
                );

        assertEquals(
                "Bodega",
                recorrido.get(0)
        );

        assertTrue(
                recorrido.contains("TiendaA")
        );

        assertTrue(
                recorrido.contains("TiendaB")
        );

        assertTrue(
                recorrido.contains("ClienteA")
        );

        assertTrue(
                recorrido.contains("ClienteB")
        );

        assertEquals(
                5,
                recorrido.size()
        );
    }

    @Test
    void dfsDebeRecorrerElGrafo() {

        List<String> recorrido =
                RecorridosGrafo.dfs(
                        grafo,
                        "Bodega"
                );

        assertEquals(
                "Bodega",
                recorrido.get(0)
        );

        assertTrue(
                recorrido.contains("TiendaA")
        );

        assertTrue(
                recorrido.contains("TiendaB")
        );

        assertTrue(
                recorrido.contains("ClienteA")
        );

        assertTrue(
                recorrido.contains("ClienteB")
        );

        assertEquals(
                5,
                recorrido.size()
        );
    }

    @Test
    void debeEncontrarRutaEntrePuntos() {

        assertTrue(
                RecorridosGrafo.existeRuta(
                        grafo,
                        "Bodega",
                        "ClienteA"
                )
        );
    }

    @Test
    void noDebeEncontrarRutaEntreComponentesSeparados() {

        grafo.agregarPunto(
                "PuntoAislado"
        );

        assertFalse(
                RecorridosGrafo.existeRuta(
                        grafo,
                        "Bodega",
                        "PuntoAislado"
                )
        );
    }

    @Test
    void puntoInexistenteNoDebeTenerRuta() {

        assertFalse(
                RecorridosGrafo.existeRuta(
                        grafo,
                        "Bodega",
                        "NoExiste"
                )
        );
    }

    @Test
    void grafoNuloDebeLanzarExcepcionEnBfs() {

        assertThrows(
                IllegalArgumentException.class,
                () -> RecorridosGrafo.bfs(
                        null,
                        "Bodega"
                )
        );
    }

    @Test
    void grafoNuloDebeLanzarExcepcionEnDfs() {

        assertThrows(
                IllegalArgumentException.class,
                () -> RecorridosGrafo.dfs(
                        null,
                        "Bodega"
                )
        );
    }

    @Test
    void grafoNuloDebeLanzarExcepcionEnRuta() {

        assertThrows(
                IllegalArgumentException.class,
                () -> RecorridosGrafo.existeRuta(
                        null,
                        "A",
                        "B"
                )
        );
    }
}