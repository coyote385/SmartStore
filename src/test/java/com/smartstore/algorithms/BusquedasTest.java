package com.smartstore.algorithms;

import org.junit.jupiter.api.Test;

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
}