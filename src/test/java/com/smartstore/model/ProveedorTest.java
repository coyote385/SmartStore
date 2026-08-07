package com.smartstore.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ProveedorTest {

    @Test
    void crearProveedorCorrectamente() {

        Proveedor proveedor = new Proveedor(
                1,
                "HP Colombia",
                "900123456",
                "3201112233",
                "ventas@hp.com",
                "Bogotá",
                true
        );

        assertEquals("HP Colombia", proveedor.getNombre());
        assertEquals("900123456", proveedor.getNit());
        assertEquals("3201112233", proveedor.getTelefono());
        assertEquals("ventas@hp.com", proveedor.getCorreo());
        assertEquals("Bogotá", proveedor.getDireccion());
        assertTrue(proveedor.isActivo());
    }

    @Test
    void modificarTelefono() {

        Proveedor proveedor = new Proveedor(
                1,
                "HP",
                "123",
                "111",
                "a@a.com",
                "Bogotá",
                true
        );

        proveedor.setTelefono("3009999999");

        assertEquals(
                "3009999999",
                proveedor.getTelefono()
        );
    }

    @Test
    void modificarCorreo() {

        Proveedor proveedor = new Proveedor(
                1,
                "HP",
                "123",
                "111",
                "a@a.com",
                "Bogotá",
                true
        );

        proveedor.setCorreo("nuevo@hp.com");

        assertEquals(
                "nuevo@hp.com",
                proveedor.getCorreo()
        );
    }

    @Test
    void correoInvalidoDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Proveedor(
                        1,
                        "HP",
                        "123",
                        "111",
                        "correo",
                        "Bogotá",
                        true
                )
        );
    }

    @Test
    void correoNuloDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Proveedor(
                        1,
                        "HP",
                        "123",
                        "111",
                        null,
                        "Bogotá",
                        true
                )
        );
    }

    @Test
    void nombreVacioDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Proveedor(
                        1,
                        "",
                        "123",
                        "111",
                        "a@a.com",
                        "Bogotá",
                        true
                )
        );
    }

    @Test
    void nitVacioDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Proveedor(
                        1,
                        "HP",
                        "",
                        "111",
                        "a@a.com",
                        "Bogotá",
                        true
                )
        );
    }

    @Test
    void telefonoVacioDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Proveedor(
                        1,
                        "HP",
                        "123",
                        "",
                        "a@a.com",
                        "Bogotá",
                        true
                )
        );
    }

    @Test
    void direccionVaciaDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Proveedor(
                        1,
                        "HP",
                        "123",
                        "111",
                        "a@a.com",
                        "",
                        true
                )
        );
    }

    @Test
    void idInvalidoDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Proveedor(
                        0,
                        "HP",
                        "123",
                        "111",
                        "a@a.com",
                        "Bogotá",
                        true
                )
        );
    }

    @Test
    void verificarEquals() {

        Proveedor p1 = new Proveedor(
                1,
                "A",
                "1",
                "1",
                "a@a.com",
                "Bogotá",
                true
        );

        Proveedor p2 = new Proveedor(
                1,
                "B",
                "2",
                "2",
                "b@b.com",
                "Medellín",
                false
        );

        assertEquals(p1, p2);
    }

    @Test
    void proveedoresConIdDiferenteNoSonIguales() {

        Proveedor p1 = new Proveedor(
                1,
                "A",
                "1",
                "1",
                "a@a.com",
                "Bogotá",
                true
        );

        Proveedor p2 = new Proveedor(
                2,
                "A",
                "1",
                "1",
                "a@a.com",
                "Bogotá",
                true
        );

        assertNotEquals(p1, p2);
    }

    @Test
    void verificarHashCode() {

        Proveedor p1 = new Proveedor(
                1,
                "A",
                "1",
                "1",
                "a@a.com",
                "Bogotá",
                true
        );

        Proveedor p2 = new Proveedor(
                1,
                "B",
                "2",
                "2",
                "b@b.com",
                "Medellín",
                false
        );

        assertEquals(
                p1.hashCode(),
                p2.hashCode()
        );
    }

    @Test
    void verificarToString() {

        Proveedor proveedor = new Proveedor(
                1,
                "HP",
                "123",
                "111",
                "a@a.com",
                "Bogotá",
                true
        );

        String resultado = proveedor.toString();

        assertTrue(resultado.contains("HP"));
        assertTrue(resultado.contains("123"));
        assertTrue(resultado.contains("111"));
        assertTrue(resultado.contains("a@a.com"));
        assertTrue(resultado.contains("Bogotá"));
    }
}