package com.smartstore.structures;

import com.smartstore.model.Pedido;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Representa una cola para gestionar los pedidos
 * pendientes de procesamiento.
 *
 * La cola utiliza el comportamiento FIFO:
 * el primer pedido que entra es el primero
 * que sale.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class ColaPedidos {

    private Queue<Pedido> cola;

    /**
     * Constructor de la cola de pedidos.
     */
    public ColaPedidos() {
        cola = new LinkedList<>();
    }

    /**
     * Agrega un pedido al final de la cola.
     *
     * @param pedido Pedido que se desea agregar.
     */
    public void enqueue(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException(
                    "El pedido no puede ser nulo."
            );
        }

        cola.offer(pedido);
    }

    /**
     * Retira y devuelve el primer pedido de la cola.
     *
     * @return Primer pedido de la cola.
     */
    public Pedido dequeue() {

        return cola.poll();
    }

    /**
     * Consulta el primer pedido sin retirarlo.
     *
     * @return Primer pedido de la cola.
     */
    public Pedido peek() {

        return cola.peek();
    }

    /**
     * Verifica si la cola está vacía.
     *
     * @return true si no contiene pedidos.
     */
    public boolean isEmpty() {

        return cola.isEmpty();
    }

    /**
     * Obtiene la cantidad de pedidos en la cola.
     *
     * @return Número de pedidos.
     */
    public int size() {

        return cola.size();
    }

    /**
     * Elimina todos los pedidos de la cola.
     */
    public void clear() {

        cola.clear();
    }
}