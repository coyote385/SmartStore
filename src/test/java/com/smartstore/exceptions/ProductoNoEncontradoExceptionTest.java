package com.smartstore.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductoNoEncontradoExceptionTest {

    @Test
    void debeConservarElMensaje() {

        String mensaje = "Producto no encontrado.";

        ProductoNoEncontradoException exception =
                new ProductoNoEncontradoException(mensaje);

        assertEquals(mensaje, exception.getMessage());
    }
}