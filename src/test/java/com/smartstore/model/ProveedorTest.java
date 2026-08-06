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
                true);

        assertEquals("HP Colombia", proveedor.getNombre());
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
                true);

        proveedor.setTelefono("3009999999");

        assertEquals("3009999999", proveedor.getTelefono());
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
                true);

        proveedor.setCorreo("nuevo@hp.com");

        assertEquals("nuevo@hp.com", proveedor.getCorreo());
    }

    @Test
    void correoInvalidoDebeLanzarExcepcion() {

        assertThrows(IllegalArgumentException.class, () -> {

            Proveedor proveedor = new Proveedor(
                    1,
                    "HP",
                    "123",
                    "111",
                    "correo",
                    "Bogotá",
                    true);

        });
    }

    @Test
    void nombreVacioDebeLanzarExcepcion() {

        assertThrows(IllegalArgumentException.class, () -> {

            Proveedor proveedor = new Proveedor(
                    1,
                    "",
                    "123",
                    "111",
                    "a@a.com",
                    "Bogotá",
                    true);

        });
    }

    @Test
    void verificarEquals() {

        Proveedor p1 = new Proveedor(
                1,"A","1","1","a@a.com","Bogotá",true);

        Proveedor p2 = new Proveedor(
                1,"B","2","2","b@b.com","Medellín",false);

        assertEquals(p1,p2);
    }

    @Test
    void verificarHashCode() {

        Proveedor p1 = new Proveedor(
                1,"A","1","1","a@a.com","Bogotá",true);

        Proveedor p2 = new Proveedor(
                1,"B","2","2","b@b.com","Medellín",false);

        assertEquals(p1.hashCode(),p2.hashCode());
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
                true);

        assertTrue(proveedor.toString().contains("HP"));
    }

}