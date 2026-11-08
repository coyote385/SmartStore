package com.smartstore.algorithms;

import com.smartstore.model.Cliente;
import com.smartstore.model.Producto;

import java.util.List;

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

    /**
     * Realiza búsqueda binaria sobre una lista de productos ordenada por código.
     *
     * @param productos Lista de productos previamente ordenada por código.
     * @param codigo Código del producto a buscar.
     * @return Producto encontrado o null si no existe.
     */
    public static Producto buscarProductoPorCodigoBinaria(List<Producto> productos, String codigo) {
        if (productos == null) {
            throw new IllegalArgumentException("La lista de productos no puede ser nula.");
        }
        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        int inicio = 0;
        int fin = productos.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Producto p = productos.get(medio);
            int comparacion = p.getCodigo().compareToIgnoreCase(codigo);

            if (comparacion == 0) {
                return p;
            } else if (comparacion < 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }

        return null;
    }

    /**
     * Realiza búsqueda binaria sobre una lista de clientes ordenada por cédula.
     *
     * @param clientes Lista de clientes previamente ordenada por cédula.
     * @param cedula Cédula del cliente a buscar.
     * @return Cliente encontrado o null si no existe.
     */
    public static Cliente buscarClientePorCedulaBinaria(List<Cliente> clientes, String cedula) {
        if (clientes == null) {
            throw new IllegalArgumentException("La lista de clientes no puede ser nula.");
        }
        if (cedula == null || cedula.isBlank()) {
            return null;
        }

        int inicio = 0;
        int fin = clientes.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            Cliente c = clientes.get(medio);
            int comparacion = c.getCedula().compareToIgnoreCase(cedula);

            if (comparacion == 0) {
                return c;
            } else if (comparacion < 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }

        return null;
    }
}