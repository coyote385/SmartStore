package com.smartstore.structures;

import com.smartstore.model.Producto;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa una tabla hash para almacenar productos.
 *
 * Los productos se identifican mediante su código,
 * permitiendo realizar búsquedas rápidas por clave.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class TablaHashProductos {

    private Map<String, Producto> productos;

    /**
     * Constructor de la tabla hash.
     */
    public TablaHashProductos() {
        productos = new HashMap<>();
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

        if (productos.containsKey(producto.getCodigo())) {
            throw new IllegalArgumentException(
                    "Ya existe un producto con ese código."
            );
        }

        productos.put(
                producto.getCodigo(),
                producto
        );
    }

    /**
     * Busca un producto mediante su código.
     *
     * @param codigo Código del producto.
     * @return Producto encontrado o null si no existe.
     */
    public Producto buscar(String codigo) {

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código es obligatorio."
            );
        }

        return productos.get(codigo);
    }

    /**
     * Elimina un producto mediante su código.
     *
     * @param codigo Código del producto.
     * @return Producto eliminado o null si no existe.
     */
    public Producto eliminar(String codigo) {

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código es obligatorio."
            );
        }

        return productos.remove(codigo);
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

        return productos.containsKey(codigo);
    }

    /**
     * Obtiene la cantidad de productos almacenados.
     *
     * @return Cantidad de productos.
     */
    public int size() {

        return productos.size();
    }

    /**
     * Verifica si la tabla está vacía.
     *
     * @return true si está vacía.
     */
    public boolean isEmpty() {

        return productos.isEmpty();
    }

    /**
     * Elimina todos los productos.
     */
    public void clear() {

        productos.clear();
    }
}