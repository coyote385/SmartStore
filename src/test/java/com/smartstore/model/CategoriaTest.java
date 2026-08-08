package com.smartstore.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriaTest {

    private Categoria crearCategoria() {

        return new Categoria(
                1,
                "Tecnología",
                "Productos tecnológicos",
                true
        );
    }

    // ==========================
    // Creación y getters
    // ==========================

    @Test
    void crearCategoriaCorrectamente() {

        Categoria categoria = crearCategoria();

        assertEquals(
                1,
                categoria.getId()
        );

        assertEquals(
                "Tecnología",
                categoria.getNombre()
        );

        assertEquals(
                "Productos tecnológicos",
                categoria.getDescripcion()
        );

        assertTrue(
                categoria.isActiva()
        );
    }

    // ==========================
    // Setters
    // ==========================

    @Test
    void modificarId() {

        Categoria categoria = crearCategoria();

        categoria.setId(2);

        assertEquals(
                2,
                categoria.getId()
        );
    }

    @Test
    void modificarNombre() {

        Categoria categoria = crearCategoria();

        categoria.setNombre("Electrónica");

        assertEquals(
                "Electrónica",
                categoria.getNombre()
        );
    }

    @Test
    void modificarDescripcion() {

        Categoria categoria = crearCategoria();

        categoria.setDescripcion(
                "Equipos electrónicos"
        );

        assertEquals(
                "Equipos electrónicos",
                categoria.getDescripcion()
        );
    }

    @Test
    void desactivarCategoria() {

        Categoria categoria = crearCategoria();

        categoria.setActiva(false);

        assertFalse(
                categoria.isActiva()
        );
    }

    @Test
    void activarCategoria() {

        Categoria categoria = crearCategoria();

        categoria.setActiva(false);
        categoria.setActiva(true);

        assertTrue(
                categoria.isActiva()
        );
    }

    // ==========================
    // Validación ID
    // ==========================

    @Test
    void idCeroDebeLanzarExcepcion() {

        Categoria categoria = crearCategoria();

        assertThrows(
                IllegalArgumentException.class,
                () -> categoria.setId(0)
        );
    }

    @Test
    void idNegativoDebeLanzarExcepcion() {

        Categoria categoria = crearCategoria();

        assertThrows(
                IllegalArgumentException.class,
                () -> categoria.setId(-1)
        );
    }

    // ==========================
    // Validación nombre
    // ==========================

    @Test
    void nombreVacioDebeLanzarExcepcion() {

        Categoria categoria = crearCategoria();

        assertThrows(
                IllegalArgumentException.class,
                () -> categoria.setNombre("")
        );
    }

    @Test
    void nombreNuloDebeLanzarExcepcion() {

        Categoria categoria = crearCategoria();

        assertThrows(
                IllegalArgumentException.class,
                () -> categoria.setNombre(null)
        );
    }

    @Test
    void nombreConEspaciosDebeLanzarExcepcion() {

        Categoria categoria = crearCategoria();

        assertThrows(
                IllegalArgumentException.class,
                () -> categoria.setNombre("   ")
        );
    }

    // ==========================
    // Validación descripción
    // ==========================

    @Test
    void descripcionVaciaDebeLanzarExcepcion() {

        Categoria categoria = crearCategoria();

        assertThrows(
                IllegalArgumentException.class,
                () -> categoria.setDescripcion("")
        );
    }

    @Test
    void descripcionNulaDebeLanzarExcepcion() {

        Categoria categoria = crearCategoria();

        assertThrows(
                IllegalArgumentException.class,
                () -> categoria.setDescripcion(null)
        );
    }

    @Test
    void descripcionConEspaciosDebeLanzarExcepcion() {

        Categoria categoria = crearCategoria();

        assertThrows(
                IllegalArgumentException.class,
                () -> categoria.setDescripcion("   ")
        );
    }

    // ==========================
    // equals
    // ==========================

    @Test
    void equalsDebeSerTrueParaElMismoObjeto() {

        Categoria categoria = crearCategoria();

        assertEquals(
                categoria,
                categoria
        );
    }

    @Test
    void equalsDebeCompararPorId() {

        Categoria c1 = new Categoria(
                1,
                "Tecnología",
                "A",
                true
        );

        Categoria c2 = new Categoria(
                1,
                "Otra",
                "B",
                false
        );

        assertEquals(
                c1,
                c2
        );
    }

    @Test
    void categoriasConIdDiferenteNoDebenSerIguales() {

        Categoria c1 = crearCategoria();

        Categoria c2 = new Categoria(
                2,
                "Tecnología",
                "Productos tecnológicos",
                true
        );

        assertNotEquals(
                c1,
                c2
        );
    }

    @Test
    void categoriaNoDebeSerIgualANull() {

        Categoria categoria = crearCategoria();

        assertNotEquals(
                categoria,
                null
        );
    }

    @Test
    void categoriaNoDebeSerIgualAOtroTipo() {

        Categoria categoria = crearCategoria();

        assertNotEquals(
                categoria,
                "Tecnología"
        );
    }

    // ==========================
    // hashCode
    // ==========================

    @Test
    void hashCodeDebeCoincidirParaMismoId() {

        Categoria c1 = new Categoria(
                1,
                "Tecnología",
                "A",
                true
        );

        Categoria c2 = new Categoria(
                1,
                "Otra",
                "B",
                false
        );

        assertEquals(
                c1.hashCode(),
                c2.hashCode()
        );
    }

    @Test
    void hashCodeDebeSerDiferenteParaIdsDiferentes() {

        Categoria c1 = new Categoria(
                1,
                "Tecnología",
                "A",
                true
        );

        Categoria c2 = new Categoria(
                2,
                "Tecnología",
                "A",
                true
        );

        assertNotEquals(
                c1.hashCode(),
                c2.hashCode()
        );
    }

    // ==========================
    // toString
    // ==========================

    @Test
    void toStringDebeContenerInformacion() {

        Categoria categoria = crearCategoria();

        String resultado =
                categoria.toString();

        assertTrue(
                resultado.contains("1")
        );

        assertTrue(
                resultado.contains("Tecnología")
        );

        assertTrue(
                resultado.contains(
                        "Productos tecnológicos"
                )
        );

        assertTrue(
                resultado.contains("true")
        );
    }
}