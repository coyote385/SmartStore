package com.smartstore.structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PilaHistorialTest {

    private PilaHistorial historial;

    @BeforeEach
    void setUp() {
        historial = new PilaHistorial();
    }

    @Test
    void pilaDebeIniciarVacia() {

        assertTrue(historial.isEmpty());
        assertEquals(0, historial.size());
    }

    @Test
    void agregarOperacion() {

        historial.push("Registrar producto");

        assertFalse(historial.isEmpty());
        assertEquals(1, historial.size());
    }

    @Test
    void peekDebeConsultarSinEliminar() {

        historial.push("Registrar producto");

        assertEquals(
                "Registrar producto",
                historial.peek()
        );

        assertEquals(1, historial.size());
    }

    @Test
    void popDebeRetirarUltimaOperacion() {

        historial.push("Registrar producto");
        historial.push("Actualizar stock");

        String resultado = historial.pop();

        assertEquals(
                "Actualizar stock",
                resultado
        );

        assertEquals(1, historial.size());
    }

    @Test
    void debeCumplirComportamientoLIFO() {

        historial.push("Operación 1");
        historial.push("Operación 2");
        historial.push("Operación 3");

        assertEquals("Operación 3", historial.pop());
        assertEquals("Operación 2", historial.pop());
        assertEquals("Operación 1", historial.pop());

        assertTrue(historial.isEmpty());
    }

    @Test
    void popDePilaVaciaDebeRetornarNull() {

        assertNull(historial.pop());
    }

    @Test
    void peekDePilaVaciaDebeRetornarNull() {

        assertNull(historial.peek());
    }

    @Test
    void operacionVaciaDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> historial.push("")
        );
    }

    @Test
    void operacionNulaDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> historial.push(null)
        );
    }

    @Test
    void clearDebeVaciarLaPila() {

        historial.push("Operación 1");
        historial.push("Operación 2");

        historial.clear();

        assertTrue(historial.isEmpty());
        assertEquals(0, historial.size());
    }
}