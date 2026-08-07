package com.smartstore.structures;

import java.util.*;

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

        if (!conexiones.get(origen).contains(destino)) {
            conexiones.get(origen).add(destino);
        }

        if (!conexiones.get(destino).contains(origen)) {
            conexiones.get(destino).add(origen);
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
     * Recorre el grafo utilizando BFS.
     *
     * @param inicio Punto inicial.
     * @return Lista de puntos visitados.
     */
    public List<String> bfs(String inicio) {

        validarPunto(inicio);

        List<String> recorrido =
                new ArrayList<>();

        if (!conexiones.containsKey(inicio)) {
            return recorrido;
        }

        Queue<String> cola =
                new LinkedList<>();

        Set<String> visitados =
                new HashSet<>();

        cola.offer(inicio);
        visitados.add(inicio);

        while (!cola.isEmpty()) {

            String actual = cola.poll();

            recorrido.add(actual);

            for (String vecino :
                    conexiones.get(actual)) {

                if (!visitados.contains(vecino)) {

                    visitados.add(vecino);
                    cola.offer(vecino);
                }
            }
        }

        return recorrido;
    }

    /**
     * Recorre el grafo utilizando DFS.
     *
     * @param inicio Punto inicial.
     * @return Lista de puntos visitados.
     */
    public List<String> dfs(String inicio) {

        validarPunto(inicio);

        List<String> recorrido =
                new ArrayList<>();

        if (!conexiones.containsKey(inicio)) {
            return recorrido;
        }

        Set<String> visitados =
                new HashSet<>();

        dfsRecursivo(
                inicio,
                visitados,
                recorrido
        );

        return recorrido;
    }

    private void dfsRecursivo(
            String actual,
            Set<String> visitados,
            List<String> recorrido) {

        visitados.add(actual);
        recorrido.add(actual);

        for (String vecino :
                conexiones.get(actual)) {

            if (!visitados.contains(vecino)) {

                dfsRecursivo(
                        vecino,
                        visitados,
                        recorrido
                );
            }
        }
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