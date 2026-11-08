package com.smartstore.service;

import com.smartstore.exceptions.CodigoDuplicadoException;
import com.smartstore.exceptions.ProductoNoEncontradoException;
import com.smartstore.model.Categoria;
import com.smartstore.model.Producto;
import com.smartstore.structures.ArbolCategorias;
import com.smartstore.structures.PilaHistorial;
import com.smartstore.structures.TablaHashProductos;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar el inventario de productos.
 * Integra estructuras de datos optimizadas como Tabla Hash para búsquedas O(1),
 * Árbol BST para categorías y Pila para historial de operaciones.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class InventarioService {

    private final List<Producto> productos;
    private final TablaHashProductos tablaHash;
    private final ArbolCategorias arbolCategorias;
    private final PilaHistorial historial;

    /**
     * Constructor del servicio.
     */
    public InventarioService() {
        productos = new ArrayList<>();
        tablaHash = new TablaHashProductos();
        arbolCategorias = new ArbolCategorias();
        historial = new PilaHistorial();
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
        tablaHash.agregar(producto);

        if (producto.getCategoria() != null && arbolCategorias.buscar(producto.getCategoria().getId()) == null) {
            arbolCategorias.insertar(producto.getCategoria());
        }

        historial.push("Registrado producto: " + producto.getCodigo() + " - " + producto.getNombre());
    }

    /**
     * Busca un producto por su código utilizando la Tabla Hash en O(1).
     *
     * @param codigo Código del producto.
     * @return Producto encontrado o null si no existe.
     */
    public Producto buscarPorCodigo(String codigo) {

        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        return tablaHash.buscar(codigo);
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

        tablaHash.eliminar(codigo);
        historial.push("Eliminado producto: " + codigo);
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

    /**
     * Carga una lista de productos en el inventario sincronizando las estructuras.
     *
     * @param nuevosProductos Lista de productos a cargar.
     */
    public void cargarProductos(List<Producto> nuevosProductos) {
        limpiar();
        if (nuevosProductos != null) {
            for (Producto p : nuevosProductos) {
                if (p != null && buscarPorCodigo(p.getCodigo()) == null) {
                    productos.add(p);
                    tablaHash.agregar(p);
                    if (p.getCategoria() != null && arbolCategorias.buscar(p.getCategoria().getId()) == null) {
                        arbolCategorias.insertar(p.getCategoria());
                    }
                }
            }
        }
    }

    /**
     * Limpia todos los productos y reinicia las estructuras.
     */
    public void limpiar() {
        productos.clear();
        tablaHash.clear();
        historial.clear();
    }

    public TablaHashProductos getTablaHash() {
        return tablaHash;
    }

    public ArbolCategorias getArbolCategorias() {
        return arbolCategorias;
    }

    public PilaHistorial getHistorial() {
        return historial;
    }

    public List<Categoria> obtenerCategoriasOrdenadas() {
        return arbolCategorias.inOrden();
    }

    public String obtenerUltimaOperacion() {
        return historial.peek();
    }
}