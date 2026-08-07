package com.smartstore.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteNoEncontradoExceptionTest {

    @Test
    void debeConservarElMensaje() {

        String mensaje = "Cliente no encontrado.";

        ClienteNoEncontradoException exception =
                new ClienteNoEncontradoException(mensaje);

        assertEquals(mensaje, exception.getMessage());
    }
}