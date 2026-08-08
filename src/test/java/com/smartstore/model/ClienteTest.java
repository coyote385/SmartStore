package com.smartstore.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private Cliente crearCliente() {

        return new Cliente(
                1,
                "Jonathan",
                "Mendez",
                "123456789",
                "3001234567",
                "jonathan@email.com",
                "Ibagué",
                true
        );
    }

    @Test
    void crearClienteCorrectamente() {

        Cliente cliente = crearCliente();

        assertEquals(1, cliente.getId());
        assertEquals("Jonathan", cliente.getNombre());
        assertEquals("Mendez", cliente.getApellido());
        assertEquals("123456789", cliente.getCedula());
        assertEquals("3001234567", cliente.getTelefono());
        assertEquals("jonathan@email.com", cliente.getCorreo());
        assertEquals("Ibagué", cliente.getDireccion());
        assertTrue(cliente.isActivo());
    }

    @Test
    void cambiarId() {

        Cliente cliente = crearCliente();

        cliente.setId(2);

        assertEquals(2, cliente.getId());
    }

    @Test
    void idInvalidoDebeLanzarExcepcion() {

        Cliente cliente = crearCliente();

        assertThrows(
                IllegalArgumentException.class,
                () -> cliente.setId(0)
        );
    }

    @Test
    void cambiarNombre() {

        Cliente cliente = crearCliente();

        cliente.setNombre("Carlos");

        assertEquals(
                "Carlos",
                cliente.getNombre()
        );
    }

    @Test
    void nombreVacioDebeLanzarExcepcion() {

        Cliente cliente = crearCliente();

        assertThrows(
                IllegalArgumentException.class,
                () -> cliente.setNombre("")
        );
    }

    @Test
    void nombreNuloDebeLanzarExcepcion() {

        Cliente cliente = crearCliente();

        assertThrows(
                IllegalArgumentException.class,
                () -> cliente.setNombre(null)
        );
    }

    @Test
    void cambiarApellido() {

        Cliente cliente = crearCliente();

        cliente.setApellido("Perez");

        assertEquals(
                "Perez",
                cliente.getApellido()
        );
    }

    @Test
    void apellidoVacioDebeLanzarExcepcion() {

        Cliente cliente = crearCliente();

        assertThrows(
                IllegalArgumentException.class,
                () -> cliente.setApellido("")
        );
    }

    @Test
    void apellidoNuloDebeLanzarExcepcion() {

        Cliente cliente = crearCliente();

        assertThrows(
                IllegalArgumentException.class,
                () -> cliente.setApellido(null)
        );
    }

    @Test
    void cambiarCedula() {

        Cliente cliente = crearCliente();

        cliente.setCedula("987654321");

        assertEquals(
                "987654321",
                cliente.getCedula()
        );
    }

    @Test
    void cedulaVaciaDebeLanzarExcepcion() {

        Cliente cliente = crearCliente();

        assertThrows(
                IllegalArgumentException.class,
                () -> cliente.setCedula("")
        );
    }

    @Test
    void cedulaNulaDebeLanzarExcepcion() {

        Cliente cliente = crearCliente();

        assertThrows(
                IllegalArgumentException.class,
                () -> cliente.setCedula(null)
        );
    }

    @Test
    void cambiarTelefono() {

        Cliente cliente = crearCliente();

        cliente.setTelefono("3111111111");

        assertEquals(
                "3111111111",
                cliente.getTelefono()
        );
    }

    @Test
    void telefonoVacioDebeLanzarExcepcion() {

        Cliente cliente = crearCliente();

        assertThrows(
                IllegalArgumentException.class,
                () -> cliente.setTelefono("")
        );
    }

    @Test
    void telefonoNuloDebeLanzarExcepcion() {

        Cliente cliente = crearCliente();

        assertThrows(
                IllegalArgumentException.class,
                () -> cliente.setTelefono(null)
        );
    }

    @Test
    void cambiarCorreo() {

        Cliente cliente = crearCliente();

        cliente.setCorreo("nuevo@email.com");

        assertEquals(
                "nuevo@email.com",
                cliente.getCorreo()
        );
    }

    @Test
    void correoVacioDebeLanzarExcepcion() {

        Cliente cliente = crearCliente();

        assertThrows(
                IllegalArgumentException.class,
                () -> cliente.setCorreo("")
        );
    }

    @Test
    void correoNuloDebeLanzarExcepcion() {

        Cliente cliente = crearCliente();

        assertThrows(
                IllegalArgumentException.class,
                () -> cliente.setCorreo(null)
        );
    }

    @Test
    void cambiarDireccion() {

        Cliente cliente = crearCliente();

        cliente.setDireccion("Bogotá");

        assertEquals(
                "Bogotá",
                cliente.getDireccion()
        );
    }

    @Test
    void direccionVaciaDebeLanzarExcepcion() {

        Cliente cliente = crearCliente();

        assertThrows(
                IllegalArgumentException.class,
                () -> cliente.setDireccion("")
        );
    }

    @Test
    void direccionNulaDebeLanzarExcepcion() {

        Cliente cliente = crearCliente();

        assertThrows(
                IllegalArgumentException.class,
                () -> cliente.setDireccion(null)
        );
    }

    @Test
    void cambiarEstadoCliente() {

        Cliente cliente = crearCliente();

        cliente.setActivo(false);

        assertFalse(
                cliente.isActivo()
        );
    }

    @Test
    void verificarToString() {

        Cliente cliente = crearCliente();

        String resultado = cliente.toString();

        assertTrue(
                resultado.contains("Jonathan")
        );

        assertTrue(
                resultado.contains("123456789")
        );

        assertTrue(
                resultado.contains("Ibagué")
        );
    }
}