package com.smartstore.structures;

import com.smartstore.model.Producto;

/**
 * Representa una tabla hash para almacenar productos.
 *
 * Utiliza encadenamiento separado para manejar colisiones.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class TablaHashProductos {

    private static final int CAPACIDAD = 10;

    private NodoHash[] tabla;
    private int cantidad;

    /**
     * Nodo interno utilizado para manejar las colisiones
     * mediante encadenamiento.
     */
    private static class NodoHash {

        private String codigo;
        private Producto producto;
        private NodoHash siguiente;

        public NodoHash(
                String codigo,
                Producto producto) {

            this.codigo = codigo;
            this.producto = producto;
        }
    }

    /**
     * Constructor de la tabla hash.
     */
    public TablaHashProductos() {

        tabla = new NodoHash[CAPACIDAD];
        cantidad = 0;
    }

    /**
     * Calcula la posición de una clave dentro de la tabla.
     *
     * @param codigo Código del producto.
     * @return Índice correspondiente.
     */
    private int calcularIndice(String codigo) {

        return Math.abs(codigo.hashCode())
                % CAPACIDAD;
    }

    /**
     * Agrega un producto a la tabla.
     *
     * @param producto Producto que se desea agregar.
     */
    public void agregar(Producto producto) {

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no puede ser nulo."
            );
        }

        String codigo = producto.getCodigo();

        int indice = calcularIndice(codigo);

        NodoHash actual = tabla[indice];

        while (actual != null) {

            if (actual.codigo.equals(codigo)) {

                throw new IllegalArgumentException(
                        "Ya existe un producto con ese código."
                );
            }

            actual = actual.siguiente;
        }

        NodoHash nuevo =
                new NodoHash(codigo, producto);

        nuevo.siguiente = tabla[indice];

        tabla[indice] = nuevo;

        cantidad++;
    }

    /**
     * Busca un producto mediante su código.
     *
     * @param codigo Código del producto.
     * @return Producto encontrado o null si no existe.
     */
    public Producto buscar(String codigo) {

        validarCodigo(codigo);

        int indice = calcularIndice(codigo);

        NodoHash actual = tabla[indice];

        while (actual != null) {

            if (actual.codigo.equals(codigo)) {
                return actual.producto;
            }

            actual = actual.siguiente;
        }

        return null;
    }

    /**
     * Elimina un producto mediante su código.
     *
     * @param codigo Código del producto.
     * @return Producto eliminado o null si no existe.
     */
    public Producto eliminar(String codigo) {

        validarCodigo(codigo);

        int indice = calcularIndice(codigo);

        NodoHash actual = tabla[indice];
        NodoHash anterior = null;

        while (actual != null) {

            if (actual.codigo.equals(codigo)) {

                if (anterior == null) {
                    tabla[indice] =
                            actual.siguiente;
                } else {
                    anterior.siguiente =
                            actual.siguiente;
                }

                cantidad--;

                return actual.producto;
            }

            anterior = actual;
            actual = actual.siguiente;
        }

        return null;
    }

    /**
     * Verifica si existe un producto con determinado código.
     *
     * @param codigo Código del producto.
     * @return true si existe.
     */
    public boolean contiene(String codigo) {

        if (codigo == null || codigo.isBlank()) {
            return false;
        }

        return buscar(codigo) != null;
    }

    /**
     * Obtiene la cantidad de productos almacenados.
     *
     * @return Cantidad de productos.
     */
    public int size() {
        return cantidad;
    }

    /**
     * Verifica si la tabla está vacía.
     *
     * @return true si está vacía.
     */
    public boolean isEmpty() {
        return cantidad == 0;
    }

    /**
     * Elimina todos los productos.
     */
    public void clear() {

        tabla = new NodoHash[CAPACIDAD];
        cantidad = 0;
    }

    private void validarCodigo(String codigo) {

        if (codigo == null || codigo.isBlank()) {

            throw new IllegalArgumentException(
                    "El código es obligatorio."
            );
        }
    }
}