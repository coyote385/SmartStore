package com.smartstore.algorithms;

/**
 * Contiene algoritmos de búsqueda utilizados en SmartStore.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class Busquedas {

    /**
     * Realiza una búsqueda lineal sobre un arreglo.
     *
     * @param arreglo Arreglo donde se realizará la búsqueda.
     * @param objetivo Elemento que se desea encontrar.
     * @return Posición del elemento o -1 si no existe.
     */
    public static int busquedaLineal(
            int[] arreglo,
            int objetivo) {

        if (arreglo == null) {
            throw new IllegalArgumentException(
                    "El arreglo no puede ser nulo."
            );
        }

        for (int i = 0; i < arreglo.length; i++) {

            if (arreglo[i] == objetivo) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Realiza una búsqueda binaria sobre un arreglo ordenado.
     *
     * El arreglo debe estar ordenado de menor a mayor.
     *
     * @param arreglo Arreglo ordenado.
     * @param objetivo Elemento que se desea encontrar.
     * @return Posición del elemento o -1 si no existe.
     */
    public static int busquedaBinaria(
            int[] arreglo,
            int objetivo) {

        if (arreglo == null) {
            throw new IllegalArgumentException(
                    "El arreglo no puede ser nulo."
            );
        }

        int inicio = 0;
        int fin = arreglo.length - 1;

        while (inicio <= fin) {

            int medio =
                    inicio + (fin - inicio) / 2;

            if (arreglo[medio] == objetivo) {
                return medio;
            }

            if (arreglo[medio] < objetivo) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }

        return -1;
    }
}