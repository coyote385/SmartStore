package com.smartstore.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodigoDuplicadoExceptionTest {

    @Test
    void debeConservarElMensaje() {

        String mensaje = "El código P001 ya existe.";

        CodigoDuplicadoException exception =
                new CodigoDuplicadoException(mensaje);

        assertEquals(mensaje, exception.getMessage());
    }
}