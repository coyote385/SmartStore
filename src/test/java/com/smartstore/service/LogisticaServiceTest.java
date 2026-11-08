package com.smartstore.service;

import com.smartstore.structures.GrafoLogistico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para LogisticaService.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class LogisticaServiceTest {

    private LogisticaService logisticaService;

    @BeforeEach
    void setUp() {
        logisticaService = new LogisticaService();
    }

    @Test
    void testConstructorPersonalizadoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new LogisticaService(null));
    }

    @Test
    void testConstructorPersonalizadoValido() {
        GrafoLogistico grafo = new GrafoLogistico();
        grafo.agregarPunto("A");
        LogisticaService servicio = new LogisticaService(grafo);
        assertEquals(1, servicio.cantidadCentros());
    }

    @Test
    void testRedPorDefectoContieneCiudadesPrincipales() {
        assertTrue(logisticaService.cantidadCentros() >= 6);
        assertTrue(logisticaService.existeRuta("Bogota", "Cali"));
        assertTrue(logisticaService.existeRuta("Ibague", "Barranquilla"));
    }

    @Test
    void testCalcularRutaEntregaOptima() {
        List<String> ruta = logisticaService.calcularRutaEntrega("Bogota", "Ibague");
        assertNotNull(ruta);
        assertEquals(2, ruta.size());
        assertEquals("Bogota", ruta.get(0));
        assertEquals("Ibague", ruta.get(1));
    }

    @Test
    void testCalcularRutaEntregaConSaltosIntermedios() {
        List<String> ruta = logisticaService.calcularRutaEntrega("Ibague", "Cali");
        assertNotNull(ruta);
        assertEquals(3, ruta.size());
        assertEquals("Ibague", ruta.get(0));
        assertEquals("Pereira", ruta.get(1));
        assertEquals("Cali", ruta.get(2));
    }

    @Test
    void testAgregarCentroDistribucionYConectar() {
        logisticaService.agregarCentroDistribucion("Villavicencio");
        logisticaService.conectarCentros("Bogota", "Villavicencio");

        assertTrue(logisticaService.existeRuta("Villavicencio", "Bogota"));
        assertTrue(logisticaService.consultarCentrosConectados("Bogota").contains("Villavicencio"));
    }

    @Test
    void testRecorridosBFSYDFS() {
        List<String> bfs = logisticaService.recorridoBFS("Bogota");
        List<String> dfs = logisticaService.recorridoDFS("Bogota");

        assertNotNull(bfs);
        assertNotNull(dfs);
        assertFalse(bfs.isEmpty());
        assertFalse(dfs.isEmpty());
        assertEquals(bfs.size(), dfs.size());
    }

    @Test
    void testListarCentrosYGetRed() {
        assertNotNull(logisticaService.listarCentros());
        assertNotNull(logisticaService.getRedDistribucion());
    }
}
