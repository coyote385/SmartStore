package com.smartstore.service;

import com.smartstore.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventarioServiceTest {

    private InventarioService inventario;

    private Categoria categoria;

    private Proveedor proveedor;

    @BeforeEach
    void setUp() {

        inventario = new InventarioService();

        categoria = new Categoria(
                1,
                "Tecnología",
                "Equipos",
                true);

        proveedor = new Proveedor(
                1,
                "HP",
                "123",
                "300",
                "hp@hp.com",
                "Bogotá",
                true);

    }

    @Test
    void registrarProducto() {

        Producto p = new Producto(
                "P001",
                "Mouse",
                "USB",
                50000,
                10,
                2,
                categoria,
                proveedor);

        inventario.registrarProducto(p);

        assertEquals(1,
                inventario.cantidadProductos());

    }

    @Test
    void noPermitirCodigoDuplicado() {

        Producto p1 = new Producto(
                "P001","Mouse","USB",1,1,1,categoria,proveedor);

        Producto p2 = new Producto(
                "P001","Teclado","USB",1,1,1,categoria,proveedor);

        inventario.registrarProducto(p1);

        assertThrows(
                IllegalArgumentException.class,
                () -> inventario.registrarProducto(p2));

    }

    @Test
    void buscarProductoPorCodigo() {

        Producto p = new Producto(
                "P001","Mouse","USB",1,1,1,categoria,proveedor);

        inventario.registrarProducto(p);

        assertNotNull(
                inventario.buscarPorCodigo("P001"));

    }

    @Test
    void eliminarProducto() {

        Producto p = new Producto(
                "P001","Mouse","USB",1,1,1,categoria,proveedor);

        inventario.registrarProducto(p);

        assertTrue(
                inventario.eliminarProducto("P001"));

    }

    @Test
    void buscarPorNombre() {

        inventario.registrarProducto(

                new Producto(
                        "P001",
                        "Mouse Gamer",
                        "USB",
                        1,
                        1,
                        1,
                        categoria,
                        proveedor));

        List<Producto> lista =
                inventario.buscarPorNombre("mouse");

        assertEquals(1,
                lista.size());

    }

}