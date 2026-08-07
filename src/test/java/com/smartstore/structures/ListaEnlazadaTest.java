package com.smartstore.structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListaEnlazadaTest {

    private ListaEnlazada<String> lista;

    @BeforeEach
    void setUp() {
        lista = new ListaEnlazada<>();
    }

    @Test
    void listaDebeIniciarVacia() {

        assertTrue(lista.isEmpty());
        assertEquals(0, lista.size());
    }

    @Test
    void agregarElemento() {

        lista.agregar("A");

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
        assertEquals("A", lista.obtener(0));
    }

    @Test
    void agregarVariosElementos() {

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals(3, lista.size());

        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    @Test
    void eliminarPrimerElemento() {

        lista.agregar("A");
        lista.agregar("B");

        String eliminado =
                lista.eliminar(0);

        assertEquals("A", eliminado);
        assertEquals("B", lista.obtener(0));
        assertEquals(1, lista.size());
    }

    @Test
    void eliminarElementoIntermedio() {

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        String eliminado =
                lista.eliminar(1);

        assertEquals("B", eliminado);
        assertEquals("A", lista.obtener(0));
        assertEquals("C", lista.obtener(1));
        assertEquals(2, lista.size());
    }

    @Test
    void eliminarUltimoElemento() {

        lista.agregar("A");
        lista.agregar("B");

        String eliminado =
                lista.eliminar(1);

        assertEquals("B", eliminado);
        assertEquals(1, lista.size());
    }

    @Test
    void datoNuloDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> lista.agregar(null)
        );
    }

    @Test
    void indiceNegativoDebeLanzarExcepcion() {

        lista.agregar("A");

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> lista.obtener(-1)
        );
    }

    @Test
    void indiceFueraDeRangoDebeLanzarExcepcion() {

        lista.agregar("A");

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> lista.obtener(5)
        );
    }

    @Test
    void eliminarDeListaVaciaDebeLanzarExcepcion() {

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> lista.eliminar(0)
        );
    }

    @Test
    void clearDebeVaciarLaLista() {

        lista.agregar("A");
        lista.agregar("B");

        lista.clear();

        assertTrue(lista.isEmpty());
        assertEquals(0, lista.size());
    }
}