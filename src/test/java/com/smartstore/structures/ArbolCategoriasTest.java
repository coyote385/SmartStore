package com.smartstore.structures;

import com.smartstore.model.Categoria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArbolCategoriasTest {

    private ArbolCategorias arbol;

    @BeforeEach
    void setUp() {

        arbol = new ArbolCategorias();
    }

    private Categoria crearCategoria(int id) {

        return new Categoria(
                id,
                "Categoria " + id,
                "Descripcion " + id,
                true
        );
    }

    @Test
    void arbolDebeIniciarVacio() {

        assertTrue(arbol.isEmpty());
    }

    @Test
    void insertarCategoriaCorrectamente() {

        Categoria categoria =
                crearCategoria(5);

        arbol.insertar(categoria);

        assertFalse(arbol.isEmpty());
        assertEquals(
                categoria,
                arbol.buscar(5)
        );
    }

    @Test
    void buscarCategoriaExistente() {

        Categoria categoria1 =
                crearCategoria(5);

        Categoria categoria2 =
                crearCategoria(3);

        Categoria categoria3 =
                crearCategoria(8);

        arbol.insertar(categoria1);
        arbol.insertar(categoria2);
        arbol.insertar(categoria3);

        assertEquals(
                categoria2,
                arbol.buscar(3)
        );

        assertEquals(
                categoria3,
                arbol.buscar(8)
        );
    }

    @Test
    void buscarCategoriaInexistenteDebeRetornarNull() {

        arbol.insertar(
                crearCategoria(5)
        );

        assertNull(
                arbol.buscar(10)
        );
    }

    @Test
    void noDebePermitirIdDuplicado() {

        arbol.insertar(
                crearCategoria(5)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> arbol.insertar(
                        crearCategoria(5)
                )
        );
    }

    @Test
    void categoriaNulaDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> arbol.insertar(null)
        );
    }

    @Test
    void recorridoInOrdenDebeOrdenarPorId() {

        arbol.insertar(
                crearCategoria(5)
        );

        arbol.insertar(
                crearCategoria(3)
        );

        arbol.insertar(
                crearCategoria(8)
        );

        arbol.insertar(
                crearCategoria(1)
        );

        arbol.insertar(
                crearCategoria(4)
        );

        List<Categoria> resultado =
                arbol.inOrden();

        assertEquals(5, resultado.size());

        assertEquals(
                1,
                resultado.get(0).getId()
        );

        assertEquals(
                3,
                resultado.get(1).getId()
        );

        assertEquals(
                4,
                resultado.get(2).getId()
        );

        assertEquals(
                5,
                resultado.get(3).getId()
        );

        assertEquals(
                8,
                resultado.get(4).getId()
        );
    }

    @Test
    void eliminarHoja() {

        arbol.insertar(
                crearCategoria(5)
        );

        arbol.insertar(
                crearCategoria(3)
        );

        arbol.insertar(
                crearCategoria(8)
        );

        assertTrue(
                arbol.eliminar(3)
        );

        assertNull(
                arbol.buscar(3)
        );
    }

    @Test
    void eliminarNodoConUnHijo() {

        arbol.insertar(
                crearCategoria(5)
        );

        arbol.insertar(
                crearCategoria(3)
        );

        arbol.insertar(
                crearCategoria(2)
        );

        assertTrue(
                arbol.eliminar(3)
        );

        assertNull(
                arbol.buscar(3)
        );

        assertNotNull(
                arbol.buscar(2)
        );
    }

    @Test
    void eliminarNodoConDosHijos() {

        arbol.insertar(
                crearCategoria(5)
        );

        arbol.insertar(
                crearCategoria(3)
        );

        arbol.insertar(
                crearCategoria(8)
        );

        arbol.insertar(
                crearCategoria(2)
        );

        arbol.insertar(
                crearCategoria(4)
        );

        assertTrue(
                arbol.eliminar(3)
        );

        assertNull(
                arbol.buscar(3)
        );

        assertNotNull(
                arbol.buscar(2)
        );

        assertNotNull(
                arbol.buscar(4)
        );
    }

    @Test
    void eliminarCategoriaInexistente() {

        arbol.insertar(
                crearCategoria(5)
        );

        assertFalse(
                arbol.eliminar(10)
        );
    }

    @Test
    void clearDebeVaciarElArbol() {

        arbol.insertar(
                crearCategoria(5)
        );

        arbol.insertar(
                crearCategoria(3)
        );

        arbol.clear();

        assertTrue(
                arbol.isEmpty()
        );

        assertNull(
                arbol.buscar(5)
        );
    }
}