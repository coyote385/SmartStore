package com.smartstore.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PedidoNoEncontradoExceptionTest {

    @Test
    void debeConservarElMensaje() {

        String mensaje = "Pedido no encontrado.";

        PedidoNoEncontradoException exception =
                new PedidoNoEncontradoException(mensaje);

        assertEquals(mensaje, exception.getMessage());
    }
}