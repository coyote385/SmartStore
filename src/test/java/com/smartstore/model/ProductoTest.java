package com.smartstore.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductoTest {

    private Categoria categoria;
    private Proveedor proveedor;

    @BeforeEach
    void setUp() {

        categoria = new Categoria(
                1,
                "Tecnología",
                "Equipos tecnológicos",
                true);

        proveedor = new Proveedor(
                1,
                "HP",
                "900123",
                "3201234567",
                "ventas@hp.com",
                "Bogotá",
                true);
    }

    @Test
    void crearProductoCorrectamente() {

        Producto producto = new Producto(
                "P001",
                "Mouse",
                "Mouse inalámbrico",
                85000,
                20,
                5,
                categoria,
                proveedor);

        assertEquals("Mouse", producto.getNombre());
    }

    @Test
    void aumentarStock() {

        Producto producto = new Producto(
                "P001","Mouse","USB",50000,10,5,categoria,proveedor);

        producto.aumentarStock(15);

        assertEquals(25, producto.getStock());
    }

    @Test
    void disminuirStock() {

        Producto producto = new Producto(
                "P001","Mouse","USB",50000,20,5,categoria,proveedor);

        producto.disminuirStock(5);

        assertEquals(15, producto.getStock());
    }

    @Test
    void stockBajoDebeSerTrue() {

        Producto producto = new Producto(
                "P001","Mouse","USB",50000,5,5,categoria,proveedor);

        assertTrue(producto.stockBajo());
    }

    @Test
    void hayStockDebeSerTrue() {

        Producto producto = new Producto(
                "P001","Mouse","USB",50000,8,5,categoria,proveedor);

        assertTrue(producto.hayStock());
    }

    @Test
    void disminuirMasDelStockDebeLanzarExcepcion() {

        Producto producto = new Producto(
                "P001","Mouse","USB",50000,2,1,categoria,proveedor);

        assertThrows(IllegalArgumentException.class,
                () -> producto.disminuirStock(10));
    }

    @Test
    void verificarEquals() {

        Producto p1 = new Producto(
                "P001","A","A",1,1,1,categoria,proveedor);

        Producto p2 = new Producto(
                "P001","B","B",2,2,2,categoria,proveedor);

        assertEquals(p1,p2);
    }

    @Test
    void verificarHashCode() {

        Producto p1 = new Producto(
                "P001","A","A",1,1,1,categoria,proveedor);

        Producto p2 = new Producto(
                "P001","B","B",2,2,2,categoria,proveedor);

        assertEquals(p1.hashCode(),p2.hashCode());
    }

    @Test
    void verificarToString() {

        Producto producto = new Producto(
                "P001","Mouse","USB",50000,10,5,categoria,proveedor);

        assertTrue(producto.toString().contains("Mouse"));
    }

}