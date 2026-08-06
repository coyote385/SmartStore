package com.smartstore.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    void crearClienteCorrectamente() {

        Cliente cliente = new Cliente(
                1,
                "Jonathan",
                "Mendez",
                "123456789",
                "3001234567",
                "jonathan@email.com",
                "Ibagué",
                true);

        assertEquals("Jonathan", cliente.getNombre());
        assertEquals("Mendez", cliente.getApellido());
    }

    @Test
    void cambiarNombre() {

        Cliente cliente = new Cliente(
                1,
                "Juan",
                "Perez",
                "111",
                "300",
                "correo@mail.com",
                "Ibagué",
                true);

        cliente.setNombre("Carlos");

        assertEquals("Carlos", cliente.getNombre());
    }

    @Test
    void nombreVacioDebeLanzarExcepcion() {

        Cliente cliente = new Cliente(
                1,
                "Juan",
                "Perez",
                "111",
                "300",
                "correo@mail.com",
                "Ibagué",
                true);

        assertThrows(IllegalArgumentException.class,
                () -> cliente.setNombre(""));
    }

    @Test
    void cambiarEstadoCliente() {

        Cliente cliente = new Cliente(
                1,
                "Juan",
                "Perez",
                "111",
                "300",
                "correo@mail.com",
                "Ibagué",
                true);

        cliente.setActivo(false);

        assertFalse(cliente.isActivo());
    }

}