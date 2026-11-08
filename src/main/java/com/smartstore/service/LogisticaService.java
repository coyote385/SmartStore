package com.smartstore.service;

import com.smartstore.algorithms.RecorridosGrafo;
import com.smartstore.structures.GrafoLogistico;

import java.util.List;

/**
 * Servicio encargado de gestionar la red logística y cálculo de rutas
 * de despacho entre diferentes centros y sucursales de SmartStore.
 *
 * Utiliza estructuras de grafos y algoritmos BFS / DFS para optimizar rutas.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class LogisticaService {

    private final GrafoLogistico redDistribucion;

    /**
     * Constructor que inicializa la red logística con los centros principales por defecto.
     */
    public LogisticaService() {
        this.redDistribucion = new GrafoLogistico();
        inicializarRedPorDefecto();
    }

    /**
     * Constructor con grafo personalizado.
     *
     * @param grafo Red logística personalizada.
     */
    public LogisticaService(GrafoLogistico grafo) {
        if (grafo == null) {
            throw new IllegalArgumentException("El grafo no puede ser nulo.");
        }
        this.redDistribucion = grafo;
    }

    private void inicializarRedPorDefecto() {
        // Centros y conexiones logísticas base en Colombia
        conectarCentros("Bogota", "Ibague");
        conectarCentros("Bogota", "Medellin");
        conectarCentros("Bogota", "Bucaramanga");
        conectarCentros("Ibague", "Pereira");
        conectarCentros("Pereira", "Cali");
        conectarCentros("Medellin", "Pereira");
        conectarCentros("Medellin", "Barranquilla");
        conectarCentros("Bucaramanga", "Barranquilla");
    }

    /**
     * Agrega un nuevo centro de distribución a la red.
     *
     * @param nombreCiudad Nombre de la ciudad o centro.
     */
    public void agregarCentroDistribucion(String nombreCiudad) {
        redDistribucion.agregarPunto(nombreCiudad);
    }

    /**
     * Conecta dos centros de distribución con un tramo bidireccional.
     *
     * @param origen Ciudad de origen.
     * @param destino Ciudad de destino.
     */
    public void conectarCentros(String origen, String destino) {
        redDistribucion.agregarConexion(origen, destino);
    }

    /**
     * Calcula la ruta de entrega óptima (menor cantidad de saltos) entre dos ciudades.
     *
     * @param origen Ciudad de salida.
     * @param destino Ciudad de llegada.
     * @return Lista de ciudades que conforman la ruta o lista vacía si no hay conexión.
     */
    public List<String> calcularRutaEntrega(String origen, String destino) {
        return RecorridosGrafo.rutaMasCorta(redDistribucion, origen, destino);
    }

    /**
     * Verifica si existe una ruta entre dos ciudades.
     *
     * @param origen Ciudad de partida.
     * @param destino Ciudad de llegada.
     * @return true si están conectadas en la red.
     */
    public boolean existeRuta(String origen, String destino) {
        return RecorridosGrafo.existeRuta(redDistribucion, origen, destino);
    }

    /**
     * Obtiene los centros de distribución directamente conectados a una ciudad.
     *
     * @param ciudad Nombre de la ciudad.
     * @return Lista de ciudades vecinas conectadas.
     */
    public List<String> consultarCentrosConectados(String ciudad) {
        return redDistribucion.obtenerVecinos(ciudad);
    }

    /**
     * Obtiene la lista de todos los centros de distribución registrados.
     *
     * @return Lista de nombres de ciudades en la red.
     */
    public List<String> listarCentros() {
        return redDistribucion.obtenerPuntos();
    }

    /**
     * Realiza un recorrido en anchura (BFS) sobre la red desde un punto de partida.
     *
     * @param puntoInicio Ciudad inicial.
     * @return Orden de visita BFS.
     */
    public List<String> recorridoBFS(String puntoInicio) {
        return RecorridosGrafo.bfs(redDistribucion, puntoInicio);
    }

    /**
     * Realiza un recorrido en profundidad (DFS) sobre la red desde un punto de partida.
     *
     * @param puntoInicio Ciudad inicial.
     * @return Orden de visita DFS.
     */
    public List<String> recorridoDFS(String puntoInicio) {
        return RecorridosGrafo.dfs(redDistribucion, puntoInicio);
    }

    public GrafoLogistico getRedDistribucion() {
        return redDistribucion;
    }

    public int cantidadCentros() {
        return redDistribucion.cantidadPuntos();
    }
}
