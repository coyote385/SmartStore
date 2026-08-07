package com.smartstore.structures;

import com.smartstore.model.Categoria;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un árbol binario de búsqueda para almacenar categorías.
 *
 * Las categorías se organizan utilizando su identificador
 * como clave de comparación.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class ArbolCategorias {

    /**
     * Nodo interno del árbol.
     */
    private static class Nodo {

        private Categoria categoria;
        private Nodo izquierdo;
        private Nodo derecho;

        public Nodo(Categoria categoria) {
            this.categoria = categoria;
        }
    }

    private Nodo raiz;

    /**
     * Agrega una categoría al árbol.
     *
     * @param categoria Categoría que se desea agregar.
     */
    public void insertar(Categoria categoria) {

        if (categoria == null) {
            throw new IllegalArgumentException(
                    "La categoría no puede ser nula."
            );
        }

        raiz = insertarRecursivo(raiz, categoria);
    }

    private Nodo insertarRecursivo(
            Nodo nodo,
            Categoria categoria) {

        if (nodo == null) {
            return new Nodo(categoria);
        }

        if (categoria.getId() < nodo.categoria.getId()) {

            nodo.izquierdo =
                    insertarRecursivo(
                            nodo.izquierdo,
                            categoria
                    );

        } else if (categoria.getId() > nodo.categoria.getId()) {

            nodo.derecho =
                    insertarRecursivo(
                            nodo.derecho,
                            categoria
                    );

        } else {

            throw new IllegalArgumentException(
                    "Ya existe una categoría con ese ID."
            );
        }

        return nodo;
    }

    /**
     * Busca una categoría por su ID.
     *
     * @param id Identificador de la categoría.
     * @return Categoría encontrada o null.
     */
    public Categoria buscar(int id) {

        Nodo resultado = buscarRecursivo(raiz, id);

        if (resultado == null) {
            return null;
        }

        return resultado.categoria;
    }

    private Nodo buscarRecursivo(
            Nodo nodo,
            int id) {

        if (nodo == null) {
            return null;
        }

        if (id == nodo.categoria.getId()) {
            return nodo;
        }

        if (id < nodo.categoria.getId()) {
            return buscarRecursivo(
                    nodo.izquierdo,
                    id
            );
        }

        return buscarRecursivo(
                nodo.derecho,
                id
        );
    }

    /**
     * Obtiene las categorías mediante recorrido inorden.
     *
     * El resultado queda ordenado por ID.
     *
     * @return Lista de categorías ordenadas.
     */
    public List<Categoria> inOrden() {

        List<Categoria> resultado =
                new ArrayList<>();

        inOrdenRecursivo(
                raiz,
                resultado
        );

        return resultado;
    }

    private void inOrdenRecursivo(
            Nodo nodo,
            List<Categoria> resultado) {

        if (nodo == null) {
            return;
        }

        inOrdenRecursivo(
                nodo.izquierdo,
                resultado
        );

        resultado.add(nodo.categoria);

        inOrdenRecursivo(
                nodo.derecho,
                resultado
        );
    }

    /**
     * Elimina una categoría por su ID.
     *
     * @param id Identificador de la categoría.
     * @return true si fue eliminada.
     */
    public boolean eliminar(int id) {

        if (buscar(id) == null) {
            return false;
        }

        raiz = eliminarRecursivo(
                raiz,
                id
        );

        return true;
    }

    private Nodo eliminarRecursivo(
            Nodo nodo,
            int id) {

        if (nodo == null) {
            return null;
        }

        if (id < nodo.categoria.getId()) {

            nodo.izquierdo =
                    eliminarRecursivo(
                            nodo.izquierdo,
                            id
                    );

        } else if (id > nodo.categoria.getId()) {

            nodo.derecho =
                    eliminarRecursivo(
                            nodo.derecho,
                            id
                    );

        } else {

            if (nodo.izquierdo == null) {
                return nodo.derecho;
            }

            if (nodo.derecho == null) {
                return nodo.izquierdo;
            }

            Nodo sucesor =
                    encontrarMinimo(
                            nodo.derecho
                    );

            nodo.categoria =
                    sucesor.categoria;

            nodo.derecho =
                    eliminarRecursivo(
                            nodo.derecho,
                            sucesor.categoria.getId()
                    );
        }

        return nodo;
    }

    private Nodo encontrarMinimo(Nodo nodo) {

        Nodo actual = nodo;

        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }

        return actual;
    }

    /**
     * Verifica si el árbol está vacío.
     *
     * @return true si no contiene categorías.
     */
    public boolean isEmpty() {

        return raiz == null;
    }

    /**
     * Elimina todas las categorías del árbol.
     */
    public void clear() {

        raiz = null;
    }
}