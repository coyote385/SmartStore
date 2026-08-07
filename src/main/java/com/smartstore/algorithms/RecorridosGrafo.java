package com.smartstore.algorithms;

import com.smartstore.structures.GrafoLogistico;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Contiene algoritmos de recorrido sobre el grafo logístico.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class RecorridosGrafo {

    /**
     * Realiza un recorrido BFS desde un punto determinado.
     *
     * @param grafo Grafo que se desea recorrer.
     * @param inicio Punto inicial.
     * @return Lista con el orden del recorrido.
     */
    public static List<String> bfs(
            GrafoLogistico grafo,
            String inicio) {

        validarGrafo(grafo);

        validarInicio(inicio);

        List<String> recorrido =
                new ArrayList<>();

        if (!grafo.contienePunto(inicio)) {
            return recorrido;
        }

        Queue<String> cola =
                new ArrayDeque<>();

        Set<String> visitados =
                new HashSet<>();

        cola.offer(inicio);
        visitados.add(inicio);

        while (!cola.isEmpty()) {

            String actual = cola.poll();

            recorrido.add(actual);

            for (String vecino :
                    grafo.obtenerVecinos(actual)) {

                if (!visitados.contains(vecino)) {

                    visitados.add(vecino);
                    cola.offer(vecino);
                }
            }
        }

        return recorrido;
    }

    /**
     * Realiza un recorrido DFS desde un punto determinado.
     *
     * @param grafo Grafo que se desea recorrer.
     * @param inicio Punto inicial.
     * @return Lista con el orden del recorrido.
     */
    public static List<String> dfs(
            GrafoLogistico grafo,
            String inicio) {

        validarGrafo(grafo);

        validarInicio(inicio);

        List<String> recorrido =
                new ArrayList<>();

        if (!grafo.contienePunto(inicio)) {
            return recorrido;
        }

        Set<String> visitados =
                new HashSet<>();

        dfsRecursivo(
                grafo,
                inicio,
                visitados,
                recorrido
        );

        return recorrido;
    }

    private static void dfsRecursivo(
            GrafoLogistico grafo,
            String actual,
            Set<String> visitados,
            List<String> recorrido) {

        visitados.add(actual);
        recorrido.add(actual);

        for (String vecino :
                grafo.obtenerVecinos(actual)) {

            if (!visitados.contains(vecino)) {

                dfsRecursivo(
                        grafo,
                        vecino,
                        visitados,
                        recorrido
                );
            }
        }
    }

    /**
     * Verifica si existe una conexión entre dos puntos.
     *
     * @param grafo Grafo que se desea consultar.
     * @param origen Punto de origen.
     * @param destino Punto de destino.
     * @return true si existe una ruta.
     */
    public static boolean existeRuta(
            GrafoLogistico grafo,
            String origen,
            String destino) {

        validarGrafo(grafo);
        validarInicio(origen);
        validarInicio(destino);

        return bfs(grafo, origen)
                .contains(destino);
    }

    private static void validarGrafo(
            GrafoLogistico grafo) {

        if (grafo == null) {

            throw new IllegalArgumentException(
                    "El grafo no puede ser nulo."
            );
        }
    }

    private static void validarInicio(
            String inicio) {

        if (inicio == null || inicio.isBlank()) {

            throw new IllegalArgumentException(
                    "El punto inicial es obligatorio."
            );
        }
    }
}