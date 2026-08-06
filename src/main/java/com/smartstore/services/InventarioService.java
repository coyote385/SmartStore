package com.smartstore.service;

import com.smartstore.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class InventarioService {

    private final List<Producto> productos;

    public InventarioService() {
        productos = new ArrayList<>();
    }

    /**
     * Registra un producto.
     */
    public void registrarProducto(Producto producto) {

        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }

        for (Producto p : productos) {

            if (p.getCodigo().equalsIgnoreCase(producto.getCodigo())) {

                throw new IllegalArgumentException(
                        "Ya existe un producto con ese código.");

            }

        }

        productos.add(producto);
    }

    /**
     * Busca un producto por código.
     */
    public Producto buscarPorCodigo(String codigo) {

        for (Producto p : productos) {

            if (p.getCodigo().equalsIgnoreCase(codigo)) {

                return p;

            }

        }

        return null;
    }

    /**
     * Busca productos por nombre.
     */
    public List<Producto> buscarPorNombre(String nombre) {

        List<Producto> resultado = new ArrayList<>();

        for (Producto p : productos) {

            if (p.getNombre().toLowerCase()
                    .contains(nombre.toLowerCase())) {

                resultado.add(p);

            }

        }

        return resultado;
    }

    /**
     * Elimina un producto.
     */
    public boolean eliminarProducto(String codigo) {

        Producto producto = buscarPorCodigo(codigo);

        if (producto != null) {

            productos.remove(producto);

            return true;

        }

        return false;
    }

    /**
     * Devuelve todos los productos.
     */
    public List<Producto> obtenerProductos() {

        return new ArrayList<>(productos);

    }

    /**
     * Cantidad de productos.
     */
    public int cantidadProductos() {

        return productos.size();

    }

}