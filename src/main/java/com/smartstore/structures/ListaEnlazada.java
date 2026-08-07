package com.smartstore.structures;

/**
 * Representa una lista enlazada simple.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class ListaEnlazada<T> {

    private Nodo<T> cabeza;
    private int tamaño;

    /**
     * Constructor de la lista.
     */
    public ListaEnlazada() {
        cabeza = null;
        tamaño = 0;
    }

    /**
     * Agrega un elemento al final de la lista.
     *
     * @param dato Elemento que se desea agregar.
     */
    public void agregar(T dato) {

        if (dato == null) {
            throw new IllegalArgumentException(
                    "El dato no puede ser nulo."
            );
        }

        Nodo<T> nuevo =
                new Nodo<>(dato);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {

            Nodo<T> actual = cabeza;

            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }

            actual.setSiguiente(nuevo);
        }

        tamaño++;
    }

    /**
     * Obtiene un elemento mediante su posición.
     *
     * @param indice Posición del elemento.
     * @return Elemento encontrado.
     */
    public T obtener(int indice) {

        validarIndice(indice);

        Nodo<T> actual = cabeza;

        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }

        return actual.getDato();
    }

    /**
     * Elimina un elemento mediante su posición.
     *
     * @param indice Posición del elemento.
     * @return Elemento eliminado.
     */
    public T eliminar(int indice) {

        validarIndice(indice);

        T dato;

        if (indice == 0) {

            dato = cabeza.getDato();
            cabeza = cabeza.getSiguiente();

        } else {

            Nodo<T> anterior = cabeza;

            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.getSiguiente();
            }

            Nodo<T> eliminado =
                    anterior.getSiguiente();

            dato = eliminado.getDato();

            anterior.setSiguiente(
                    eliminado.getSiguiente()
            );
        }

        tamaño--;

        return dato;
    }

    /**
     * Obtiene la cantidad de elementos.
     *
     * @return Tamaño de la lista.
     */
    public int size() {
        return tamaño;
    }

    /**
     * Verifica si la lista está vacía.
     *
     * @return true si está vacía.
     */
    public boolean isEmpty() {
        return cabeza == null;
    }

    /**
     * Elimina todos los elementos.
     */
    public void clear() {

        cabeza = null;
        tamaño = 0;
    }

    private void validarIndice(int indice) {

        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException(
                    "Índice fuera de rango."
            );
        }
    }
}