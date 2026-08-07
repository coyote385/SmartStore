package com.smartstore.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodoTest {

    @Test
    void crearNodoCorrectamente() {

        Nodo<String> nodo =
                new Nodo<>("Producto");

        assertEquals(
                "Producto",
                nodo.getDato()
        );

        assertNull(
                nodo.getSiguiente()
        );
    }

    @Test
    void modificarDato() {

        Nodo<String> nodo =
                new Nodo<>("Producto");

        nodo.setDato("Pedido");

        assertEquals(
                "Pedido",
                nodo.getDato()
        );
    }

    @Test
    void enlazarNodo() {

        Nodo<String> primero =
                new Nodo<>("A");

        Nodo<String> segundo =
                new Nodo<>("B");

        primero.setSiguiente(segundo);

        assertEquals(
                segundo,
                primero.getSiguiente()
        );
    }
}