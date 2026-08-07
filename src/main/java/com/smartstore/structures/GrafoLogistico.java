package com.smartstore.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa un grafo para modelar las conexiones
 * entre diferentes puntos logísticos.
 *
 * Cada punto se representa mediante un identificador
 * y las conexiones se almacenan mediante listas de adyacencia.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class GrafoLogistico {

    private Map<String, List<String>> conexiones;

    /**
     * Constructor del grafo.
     */
    public GrafoLogistico() {
        conexiones = new HashMap<>();
    }

    /**
     * Agrega un punto al grafo.
     *
     * @param punto Nombre del punto.
     */
    public void agregarPunto(String punto) {

        validarPunto(punto);

        conexiones.putIfAbsent(
                punto,
                new ArrayList<>()
        );
    }

    /**
     * Agrega una conexión entre dos puntos.
     *
     * El grafo se maneja como no dirigido.
     *
     * @param origen Punto de origen.
     * @param destino Punto de destino.
     */
    public void agregarConexion(
            String origen,
            String destino) {

        validarPunto(origen);
        validarPunto(destino);

        agregarPunto(origen);
        agregarPunto(destino);

        if (!conexiones.get(origen)
                .contains(destino)) {

            conexiones.get(origen)
                    .add(destino);
        }

        if (!conexiones.get(destino)
                .contains(origen)) {

            conexiones.get(destino)
                    .add(origen);
        }
    }

    /**
     * Obtiene los puntos conectados a un punto determinado.
     *
     * @param punto Punto que se desea consultar.
     * @return Lista de puntos vecinos.
     */
    public List<String> obtenerVecinos(String punto) {

        validarPunto(punto);

        if (!conexiones.containsKey(punto)) {
            return new ArrayList<>();
        }

        return new ArrayList<>(
                conexiones.get(punto)
        );
    }

    /**
     * Verifica si existe un punto.
     *
     * @param punto Punto que se desea consultar.
     * @return true si existe.
     */
    public boolean contienePunto(String punto) {

        if (punto == null || punto.isBlank()) {
            return false;
        }

        return conexiones.containsKey(punto);
    }

    /**
     * Obtiene la cantidad de puntos.
     *
     * @return Número de puntos.
     */
    public int cantidadPuntos() {
        return conexiones.size();
    }

    /**
     * Verifica si el grafo está vacío.
     *
     * @return true si está vacío.
     */
    public boolean isEmpty() {
        return conexiones.isEmpty();
    }

    /**
     * Elimina todos los puntos y conexiones.
     */
    public void clear() {
        conexiones.clear();
    }

    private void validarPunto(String punto) {

        if (punto == null || punto.isBlank()) {

            throw new IllegalArgumentException(
                    "El punto no puede ser vacío."
            );
        }
    }
}