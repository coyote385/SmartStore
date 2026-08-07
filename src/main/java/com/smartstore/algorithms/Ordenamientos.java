package com.smartstore.algorithms;

/**
 * Contiene algoritmos de ordenamiento utilizados en SmartStore.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class Ordenamientos {

    /**
     * Ordena un arreglo utilizando Bubble Sort.
     *
     * @param arreglo Arreglo que se desea ordenar.
     * @param ascendente true para ordenar de menor a mayor.
     */
    public static void bubbleSort(
            int[] arreglo,
            boolean ascendente) {

        validarArreglo(arreglo);

        for (int i = 0; i < arreglo.length - 1; i++) {

            boolean huboIntercambio = false;

            for (int j = 0;
                    j < arreglo.length - 1 - i;
                    j++) {

                if (debeIntercambiar(
                        arreglo[j],
                        arreglo[j + 1],
                        ascendente)) {

                    int temporal = arreglo[j];

                    arreglo[j] = arreglo[j + 1];

                    arreglo[j + 1] = temporal;

                    huboIntercambio = true;
                }
            }

            if (!huboIntercambio) {
                break;
            }
        }
    }

    /**
     * Ordena un arreglo utilizando Quick Sort.
     *
     * @param arreglo Arreglo que se desea ordenar.
     * @param ascendente true para ordenar de menor a mayor.
     */
    public static void quickSort(
            int[] arreglo,
            boolean ascendente) {

        validarArreglo(arreglo);

        quickSortRecursivo(
                arreglo,
                0,
                arreglo.length - 1,
                ascendente
        );
    }

    private static void quickSortRecursivo(
            int[] arreglo,
            int inicio,
            int fin,
            boolean ascendente) {

        if (inicio >= fin) {
            return;
        }

        int indicePivote = particionar(
                arreglo,
                inicio,
                fin,
                ascendente
        );

        quickSortRecursivo(
                arreglo,
                inicio,
                indicePivote - 1,
                ascendente
        );

        quickSortRecursivo(
                arreglo,
                indicePivote + 1,
                fin,
                ascendente
        );
    }

    private static int particionar(
            int[] arreglo,
            int inicio,
            int fin,
            boolean ascendente) {

        int pivote = arreglo[fin];

        int indiceMenor = inicio - 1;

        for (int j = inicio; j < fin; j++) {

            if (!debeIntercambiar(
                    arreglo[j],
                    pivote,
                    ascendente)) {

                indiceMenor++;

                int temporal =
                        arreglo[indiceMenor];

                arreglo[indiceMenor] =
                        arreglo[j];

                arreglo[j] = temporal;
            }
        }

        int temporal =
                arreglo[indiceMenor + 1];

        arreglo[indiceMenor + 1] =
                arreglo[fin];

        arreglo[fin] = temporal;

        return indiceMenor + 1;
    }

    private static boolean debeIntercambiar(
            int actual,
            int siguiente,
            boolean ascendente) {

        if (ascendente) {
            return actual > siguiente;
        }

        return actual < siguiente;
    }

    private static void validarArreglo(
            int[] arreglo) {

        if (arreglo == null) {
            throw new IllegalArgumentException(
                    "El arreglo no puede ser nulo."
            );
        }
    }
}