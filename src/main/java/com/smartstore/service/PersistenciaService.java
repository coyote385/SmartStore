package com.smartstore.service;

import com.smartstore.model.Cliente;
import com.smartstore.model.Factura;
import com.smartstore.model.Pedido;
import com.smartstore.model.Producto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar la persistencia de datos del sistema
 * SmartStore mediante serialización y deserialización de objetos en archivos .txt.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class PersistenciaService {

    public static final String DEFAULT_DATA_DIR = "src/data";
    public static final String ARCHIVO_CLIENTES = "clientes.txt";
    public static final String ARCHIVO_PRODUCTOS = "productos.txt";
    public static final String ARCHIVO_PEDIDOS = "pedidos.txt";
    public static final String ARCHIVO_FACTURAS = "facturas.txt";

    private final String directorioBase;

    /**
     * Constructor por defecto. Utiliza la ruta "src/data" o "data" según exista.
     */
    public PersistenciaService() {
        File dirSrc = new File(DEFAULT_DATA_DIR);
        if (dirSrc.exists()) {
            this.directorioBase = DEFAULT_DATA_DIR;
        } else {
            File dirFallback = new File("data");
            if (!dirFallback.exists()) {
                dirFallback.mkdirs();
            }
            this.directorioBase = dirFallback.getPath();
        }
    }

    /**
     * Constructor con directorio personalizado (útil para pruebas unitarias).
     *
     * @param directorioBase Ruta de la carpeta donde se almacenarán los archivos.
     */
    public PersistenciaService(String directorioBase) {
        if (directorioBase == null || directorioBase.isBlank()) {
            throw new IllegalArgumentException("El directorio base no puede ser nulo ni vacío.");
        }
        File dir = new File(directorioBase);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        this.directorioBase = directorioBase;
    }

    /**
     * Obtiene el directorio base configurado.
     *
     * @return Ruta del directorio.
     */
    public String getDirectorioBase() {
        return directorioBase;
    }

    /**
     * Obtiene la referencia al archivo dentro del directorio base.
     *
     * @param nombreArchivo Nombre del archivo.
     * @return Archivo resultante.
     */
    public File obtenerArchivo(String nombreArchivo) {
        return new File(directorioBase, nombreArchivo);
    }

    /**
     * Serializa y guarda un objeto serializable en el archivo indicado.
     *
     * @param nombreArchivo Nombre del archivo dentro del directorio base.
     * @param objeto Objeto serializable a persistir.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public void guardar(String nombreArchivo, Serializable objeto) throws IOException {
        File archivo = obtenerArchivo(nombreArchivo);
        File padre = archivo.getParentFile();
        if (padre != null && !padre.exists()) {
            padre.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(objeto);
        }
    }

    /**
     * Deserializa y carga un objeto desde el archivo indicado.
     * Si el archivo no existe o está vacío, devuelve null.
     *
     * @param nombreArchivo Nombre del archivo dentro del directorio base.
     * @param <T> Tipo del objeto esperado.
     * @return Objeto deserializado o null si el archivo no existe o está vacío.
     * @throws IOException Si ocurre un error de lectura.
     * @throws ClassNotFoundException Si no se encuentra la clase del objeto.
     */
    @SuppressWarnings("unchecked")
    public <T> T cargar(String nombreArchivo) throws IOException, ClassNotFoundException {
        File archivo = obtenerArchivo(nombreArchivo);
        if (!archivo.exists() || archivo.length() == 0) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (T) ois.readObject();
        }
    }

    // =========================================================
    // MÉTODOS DE CONVENIENCIA PARA CLIENTES
    // =========================================================

    public void guardarClientes(List<Cliente> clientes) throws IOException {
        guardar(ARCHIVO_CLIENTES, new ArrayList<>(clientes != null ? clientes : List.of()));
    }

    public List<Cliente> cargarClientes() throws IOException, ClassNotFoundException {
        List<Cliente> datos = cargar(ARCHIVO_CLIENTES);
        return datos != null ? datos : new ArrayList<>();
    }

    // =========================================================
    // MÉTODOS DE CONVENIENCIA PARA PRODUCTOS
    // =========================================================

    public void guardarProductos(List<Producto> productos) throws IOException {
        guardar(ARCHIVO_PRODUCTOS, new ArrayList<>(productos != null ? productos : List.of()));
    }

    public List<Producto> cargarProductos() throws IOException, ClassNotFoundException {
        List<Producto> datos = cargar(ARCHIVO_PRODUCTOS);
        return datos != null ? datos : new ArrayList<>();
    }

    // =========================================================
    // MÉTODOS DE CONVENIENCIA PARA PEDIDOS
    // =========================================================

    public void guardarPedidos(List<Pedido> pedidos) throws IOException {
        guardar(ARCHIVO_PEDIDOS, new ArrayList<>(pedidos != null ? pedidos : List.of()));
    }

    public List<Pedido> cargarPedidos() throws IOException, ClassNotFoundException {
        List<Pedido> datos = cargar(ARCHIVO_PEDIDOS);
        return datos != null ? datos : new ArrayList<>();
    }

    // =========================================================
    // MÉTODOS DE CONVENIENCIA PARA FACTURAS
    // =========================================================

    public void guardarFacturas(List<Factura> facturas) throws IOException {
        guardar(ARCHIVO_FACTURAS, new ArrayList<>(facturas != null ? facturas : List.of()));
    }

    public List<Factura> cargarFacturas() throws IOException, ClassNotFoundException {
        List<Factura> datos = cargar(ARCHIVO_FACTURAS);
        return datos != null ? datos : new ArrayList<>();
    }
}
