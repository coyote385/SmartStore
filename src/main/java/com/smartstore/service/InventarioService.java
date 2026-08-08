package com.smartstore.service;

import com.smartstore.exceptions.CodigoDuplicadoException;
import com.smartstore.exceptions.ProductoNoEncontradoException;
import com.smartstore.model.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar el inventario de productos.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class InventarioService {

    private final List<Producto> productos;

    /**
     * Constructor del servicio.
     */
    public InventarioService() {
        productos = new ArrayList<>();
    }

    /**
     * Registra un producto en el inventario.
     *
     * @param producto Producto que se desea registrar.
     * @throws CodigoDuplicadoException
     *         si ya existe un producto con el mismo código.
     */
    public void registrarProducto(Producto producto)
            throws CodigoDuplicadoException {

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no puede ser nulo."
            );
        }

        if (buscarPorCodigo(producto.getCodigo()) != null) {
            throw new CodigoDuplicadoException(
                    "Ya existe un producto con ese código."
            );
        }

        productos.add(producto);
    }

    /**
     * Busca un producto por su código.
     *
     * @param codigo Código del producto.
     * @return Producto encontrado o null si no existe.
     */
    public Producto buscarPorCodigo(String codigo) {

        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        for (Producto producto : productos) {

            if (producto.getCodigo()
                    .equalsIgnoreCase(codigo)) {

                return producto;
            }
        }

        return null;
    }

    /**
     * Busca productos cuyo nombre contenga el texto indicado.
     *
     * @param nombre Texto que se desea buscar.
     * @return Lista de productos encontrados.
     */
    public List<Producto> buscarPorNombre(String nombre) {

        List<Producto> resultado =
                new ArrayList<>();

        if (nombre == null || nombre.isBlank()) {
            return resultado;
        }

        String criterio =
                nombre.toLowerCase();

        for (Producto producto : productos) {

            if (producto.getNombre()
                    .toLowerCase()
                    .contains(criterio)) {

                resultado.add(producto);
            }
        }

        return resultado;
    }

    /**
     * Elimina un producto del inventario.
     *
     * @param codigo Código del producto.
     * @throws ProductoNoEncontradoException
     *         si el producto no existe.
     */
    public boolean eliminarProducto(String codigo)
            throws ProductoNoEncontradoException {

        Producto producto =
                buscarPorCodigo(codigo);

        if (producto == null) {
            throw new ProductoNoEncontradoException(
                    "Producto no encontrado."
            );
        }

        return productos.remove(producto);
    }

    /**
     * Devuelve todos los productos registrados.
     *
     * @return Copia de la lista de productos.
     */
    public List<Producto> obtenerProductos() {

        return new ArrayList<>(productos);
    }

    /**
     * Obtiene la cantidad de productos registrados.
     *
     * @return Cantidad de productos.
     */
    public int cantidadProductos() {

        return productos.size();
    }
}