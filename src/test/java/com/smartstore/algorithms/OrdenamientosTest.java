package com.smartstore.algorithms;

import org.junit.jupiter.api.Test;

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
}