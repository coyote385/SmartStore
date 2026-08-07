package com.smartstore.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockInsuficienteExceptionTest {

    @Test
    void debeConservarElMensaje() {

        String mensaje = "Stock insuficiente.";

        StockInsuficienteException exception =
                new StockInsuficienteException(mensaje);

        assertEquals(mensaje, exception.getMessage());
    }
}