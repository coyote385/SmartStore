package com.smartstore.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CategoriaTest {

    @Test
    void crearCategoriaCorrectamente() {

        Categoria categoria = new Categoria(
                1,
                "Tecnología",
                "Productos tecnológicos",
                true);

        assertEquals(1, categoria.getId());
        assertEquals("Tecnología", categoria.getNombre());
        assertEquals("Productos tecnológicos", categoria.getDescripcion());
        assertTrue(categoria.isActiva());
    }

    @Test
    void modificarNombre() {

        Categoria categoria = new Categoria(
                1,
                "Tecnología",
                "Productos tecnológicos",
                true);

        categoria.setNombre("Electrónica");

        assertEquals("Electrónica", categoria.getNombre());
    }

    @Test
    void modificarDescripcion() {

        Categoria categoria = new Categoria(
                1,
                "Tecnología",
                "Productos tecnológicos",
                true);

        categoria.setDescripcion("Equipos electrónicos");

        assertEquals("Equipos electrónicos", categoria.getDescripcion());
    }

    @Test
    void desactivarCategoria() {

        Categoria categoria = new Categoria(
                1,
                "Tecnología",
                "Productos tecnológicos",
                true);

        categoria.setActiva(false);

        assertFalse(categoria.isActiva());
    }

    @Test
    void nombreVacioDebeLanzarExcepcion() {

        Categoria categoria = new Categoria(
                1,
                "Tecnología",
                "Productos tecnológicos",
                true);

        assertThrows(IllegalArgumentException.class,
                () -> categoria.setNombre(""));
    }

    @Test
    void descripcionVaciaDebeLanzarExcepcion() {

        Categoria categoria = new Categoria(
                1,
                "Tecnología",
                "Productos tecnológicos",
                true);

        assertThrows(IllegalArgumentException.class,
                () -> categoria.setDescripcion(""));
    }

    @Test
    void verificarEquals() {

        Categoria c1 = new Categoria(
                1,
                "Tecnología",
                "A",
                true);

        Categoria c2 = new Categoria(
                1,
                "Otra",
                "B",
                false);

        assertEquals(c1, c2);
    }

    @Test
    void verificarHashCode() {

        Categoria c1 = new Categoria(
                1,
                "Tecnología",
                "A",
                true);

        Categoria c2 = new Categoria(
                1,
                "Otra",
                "B",
                false);

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void verificarToString() {

        Categoria categoria = new Categoria(
                1,
                "Tecnología",
                "Productos tecnológicos",
                true);

        assertTrue(categoria.toString().contains("Tecnología"));
    }

}