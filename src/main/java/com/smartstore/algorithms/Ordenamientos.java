package com.smartstore.algorithms;

import com.smartstore.model.Cliente;
import com.smartstore.model.Producto;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    // =========================================================
    // ORDENAMIENTOS DE OBJETOS DE DOMINIO (PRODUCTO Y CLIENTE)
    // =========================================================

    /**
     * Ordena una lista de productos por precio utilizando Bubble Sort.
     *
     * @param lista Lista de productos a ordenar.
     * @param ascendente true para menor a mayor, false para mayor a menor.
     */
    public static void bubbleSortProductosPorPrecio(List<Producto> lista, boolean ascendente) {
        if (lista == null) {
            throw new IllegalArgumentException("La lista no puede ser nula.");
        }
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            boolean intercambio = false;
            for (int j = 0; j < n - 1 - i; j++) {
                double p1 = lista.get(j).getPrecio();
                double p2 = lista.get(j + 1).getPrecio();
                boolean condicion = ascendente ? p1 > p2 : p1 < p2;
                if (condicion) {
                    Collections.swap(lista, j, j + 1);
                    intercambio = true;
                }
            }
            if (!intercambio) {
                break;
            }
        }
    }

    /**
     * Ordena una lista de productos por precio utilizando Quick Sort.
     *
     * @param lista Lista de productos a ordenar.
     * @param ascendente true para menor a mayor, false para mayor a menor.
     */
    public static void quickSortProductosPorPrecio(List<Producto> lista, boolean ascendente) {
        if (lista == null) {
            throw new IllegalArgumentException("La lista no puede ser nula.");
        }
        quickSortProductosRecursivo(lista, 0, lista.size() - 1, (p1, p2) -> Double.compare(p1.getPrecio(), p2.getPrecio()), ascendente);
    }

    /**
     * Ordena una lista de productos por nombre alfabéticamente con Quick Sort.
     *
     * @param lista Lista de productos a ordenar.
     * @param ascendente true para A-Z, false para Z-A.
     */
    public static void quickSortProductosPorNombre(List<Producto> lista, boolean ascendente) {
        if (lista == null) {
            throw new IllegalArgumentException("La lista no puede ser nula.");
        }
        quickSortProductosRecursivo(lista, 0, lista.size() - 1, (p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()), ascendente);
    }

    /**
     * Ordena una lista de productos por código alfabéticamente con Quick Sort.
     *
     * @param lista Lista de productos a ordenar.
     * @param ascendente true para A-Z, false para Z-A.
     */
    public static void quickSortProductosPorCodigo(List<Producto> lista, boolean ascendente) {
        if (lista == null) {
            throw new IllegalArgumentException("La lista no puede ser nula.");
        }
        quickSortProductosRecursivo(lista, 0, lista.size() - 1, (p1, p2) -> p1.getCodigo().compareToIgnoreCase(p2.getCodigo()), ascendente);
    }

    private static void quickSortProductosRecursivo(List<Producto> lista, int inicio, int fin, Comparator<Producto> comparator, boolean ascendente) {
        if (inicio >= fin) {
            return;
        }
        int indicePivote = particionarProductos(lista, inicio, fin, comparator, ascendente);
        quickSortProductosRecursivo(lista, inicio, indicePivote - 1, comparator, ascendente);
        quickSortProductosRecursivo(lista, indicePivote + 1, fin, comparator, ascendente);
    }

    private static int particionarProductos(List<Producto> lista, int inicio, int fin, Comparator<Producto> comparator, boolean ascendente) {
        Producto pivote = lista.get(fin);
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            int cmp = comparator.compare(lista.get(j), pivote);
            boolean debeMover = ascendente ? cmp <= 0 : cmp >= 0;
            if (debeMover) {
                i++;
                Collections.swap(lista, i, j);
            }
        }
        Collections.swap(lista, i + 1, fin);
        return i + 1;
    }

    /**
     * Ordena una lista de clientes por nombre con Quick Sort.
     *
     * @param lista Lista de clientes a ordenar.
     * @param ascendente true para A-Z, false para Z-A.
     */
    public static void quickSortClientesPorNombre(List<Cliente> lista, boolean ascendente) {
        if (lista == null) {
            throw new IllegalArgumentException("La lista no puede ser nula.");
        }
        quickSortClientesRecursivo(lista, 0, lista.size() - 1, (c1, c2) -> c1.getNombre().compareToIgnoreCase(c2.getNombre()), ascendente);
    }

    /**
     * Ordena una lista de clientes por cédula con Quick Sort.
     *
     * @param lista Lista de clientes a ordenar.
     * @param ascendente true para menor a mayor, false para mayor a menor.
     */
    public static void quickSortClientesPorCedula(List<Cliente> lista, boolean ascendente) {
        if (lista == null) {
            throw new IllegalArgumentException("La lista no puede ser nula.");
        }
        quickSortClientesRecursivo(lista, 0, lista.size() - 1, (c1, c2) -> c1.getCedula().compareToIgnoreCase(c2.getCedula()), ascendente);
    }

    private static void quickSortClientesRecursivo(List<Cliente> lista, int inicio, int fin, Comparator<Cliente> comparator, boolean ascendente) {
        if (inicio >= fin) {
            return;
        }
        int indicePivote = particionarClientes(lista, inicio, fin, comparator, ascendente);
        quickSortClientesRecursivo(lista, inicio, indicePivote - 1, comparator, ascendente);
        quickSortClientesRecursivo(lista, indicePivote + 1, fin, comparator, ascendente);
    }

    private static int particionarClientes(List<Cliente> lista, int inicio, int fin, Comparator<Cliente> comparator, boolean ascendente) {
        Cliente pivote = lista.get(fin);
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            int cmp = comparator.compare(lista.get(j), pivote);
            boolean debeMover = ascendente ? cmp <= 0 : cmp >= 0;
            if (debeMover) {
                i++;
                Collections.swap(lista, i, j);
            }
        }
        Collections.swap(lista, i + 1, fin);
        return i + 1;
    }
}