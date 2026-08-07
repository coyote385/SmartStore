package com.smartstore.structures;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Representa una pila para almacenar el historial
 * de operaciones realizadas en el sistema.
 *
 * La última operación agregada es la primera
 * que puede ser consultada o retirada (LIFO).
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class PilaHistorial {

    private Deque<String> pila;

    /**
     * Constructor de la pila de historial.
     */
    public PilaHistorial() {
        pila = new ArrayDeque<>();
    }

    /**
     * Agrega una operación al historial.
     *
     * @param operacion Operación realizada.
     */
    public void push(String operacion) {

        if (operacion == null || operacion.isBlank()) {
            throw new IllegalArgumentException(
                    "La operación no puede ser vacía."
            );
        }

        pila.push(operacion);
    }

    /**
     * Retira y devuelve la última operación registrada.
     *
     * @return Última operación.
     */
    public String pop() {

        if (pila.isEmpty()) {
            return null;
        }

        return pila.pop();
    }

    /**
     * Consulta la última operación sin retirarla.
     *
     * @return Última operación.
     */
    public String peek() {

        if (pila.isEmpty()) {
            return null;
        }

        return pila.peek();
    }

    /**
     * Verifica si la pila está vacía.
     *
     * @return true si está vacía.
     */
    public boolean isEmpty() {
        return pila.isEmpty();
    }

    /**
     * Obtiene la cantidad de operaciones almacenadas.
     *
     * @return Número de operaciones.
     */
    public int size() {
        return pila.size();
    }

    /**
     * Elimina todo el historial.
     */
    public void clear() {
        pila.clear();
    }
}