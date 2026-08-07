package com.smartstore.structures;

import com.smartstore.model.Cliente;
import com.smartstore.model.Pedido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColaPedidosTest {

    private ColaPedidos cola;

    private Cliente cliente;

    @BeforeEach
    void setUp() {

        cola = new ColaPedidos();

        cliente = new Cliente(
                1,
                "Jonathan",
                "Mendez",
                "123456",
                "3001234567",
                "jonathan@email.com",
                "Ibague",
                true
        );
    }

    private Pedido crearPedido(int id) {

        return new Pedido(
                id,
                cliente,
                "Pendiente"
        );
    }

    @Test
    void colaDebeIniciarVacia() {

        assertTrue(cola.isEmpty());
        assertEquals(0, cola.size());
    }

    @Test
    void agregarPedido() {

        Pedido pedido = crearPedido(1);

        cola.enqueue(pedido);

        assertFalse(cola.isEmpty());
        assertEquals(1, cola.size());
    }

    @Test
    void peekDebeConsultarSinEliminar() {

        Pedido pedido = crearPedido(1);

        cola.enqueue(pedido);

        assertEquals(
                pedido,
                cola.peek()
        );

        assertEquals(1, cola.size());
    }

    @Test
    void dequeueDebeRetirarPrimerPedido() {

        Pedido pedido1 = crearPedido(1);
        Pedido pedido2 = crearPedido(2);

        cola.enqueue(pedido1);
        cola.enqueue(pedido2);

        Pedido resultado = cola.dequeue();

        assertEquals(
                pedido1,
                resultado
        );

        assertEquals(1, cola.size());
    }

    @Test
    void debeCumplirComportamientoFIFO() {

        Pedido pedido1 = crearPedido(1);
        Pedido pedido2 = crearPedido(2);
        Pedido pedido3 = crearPedido(3);

        cola.enqueue(pedido1);
        cola.enqueue(pedido2);
        cola.enqueue(pedido3);

        assertEquals(
                pedido1,
                cola.dequeue()
        );

        assertEquals(
                pedido2,
                cola.dequeue()
        );

        assertEquals(
                pedido3,
                cola.dequeue()
        );

        assertTrue(cola.isEmpty());
    }

    @Test
    void dequeueDeColaVaciaDebeRetornarNull() {

        assertNull(cola.dequeue());
    }

    @Test
    void peekDeColaVaciaDebeRetornarNull() {

        assertNull(cola.peek());
    }

    @Test
    void pedidoNuloDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> cola.enqueue(null)
        );
    }

    @Test
    void clearDebeVaciarLaCola() {

        cola.enqueue(crearPedido(1));
        cola.enqueue(crearPedido(2));

        cola.clear();

        assertTrue(cola.isEmpty());
        assertEquals(0, cola.size());
    }
}