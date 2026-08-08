package com.smartstore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    private Categoria categoria;
    private Proveedor proveedor;

    @BeforeEach
    void setUp() {

        categoria = new Categoria(
                1,
                "Tecnología",
                "Equipos tecnológicos",
                true
        );

        proveedor = new Proveedor(
                1,
                "HP",
                "900123",
                "3201234567",
                "ventas@hp.com",
                "Bogotá",
                true
        );
    }

    private Producto crearProducto() {

        return new Producto(
                "P001",
                "Mouse",
                "Mouse inalámbrico",
                85000,
                20,
                5,
                categoria,
                proveedor
        );
    }

    // =========================
    // Constructor
    // =========================

    @Test
    void crearProductoCorrectamente() {

        Producto producto = crearProducto();

        assertEquals(
                "P001",
                producto.getCodigo()
        );

        assertEquals(
                "Mouse",
                producto.getNombre()
        );

        assertEquals(
                "Mouse inalámbrico",
                producto.getDescripcion()
        );

        assertEquals(
                85000,
                producto.getPrecio()
        );

        assertEquals(
                20,
                producto.getStock()
        );

        assertEquals(
                5,
                producto.getStockMinimo()
        );

        assertEquals(
                categoria,
                producto.getCategoria()
        );

        assertEquals(
                proveedor,
                producto.getProveedor()
        );
    }

    // =========================
    // Validaciones constructor
    // =========================

    @Test
    void codigoNuloDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Producto(
                        null,
                        "Mouse",
                        "USB",
                        50000,
                        10,
                        5,
                        categoria,
                        proveedor
                )
        );
    }

    @Test
    void codigoVacioDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Producto(
                        "",
                        "Mouse",
                        "USB",
                        50000,
                        10,
                        5,
                        categoria,
                        proveedor
                )
        );
    }

    @Test
    void nombreNuloDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Producto(
                        "P001",
                        null,
                        "USB",
                        50000,
                        10,
                        5,
                        categoria,
                        proveedor
                )
        );
    }

    @Test
    void nombreVacioDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Producto(
                        "P001",
                        "",
                        "USB",
                        50000,
                        10,
                        5,
                        categoria,
                        proveedor
                )
        );
    }

    @Test
    void descripcionNulaDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Producto(
                        "P001",
                        "Mouse",
                        null,
                        50000,
                        10,
                        5,
                        categoria,
                        proveedor
                )
        );
    }

    @Test
    void descripcionVaciaDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Producto(
                        "P001",
                        "Mouse",
                        "",
                        50000,
                        10,
                        5,
                        categoria,
                        proveedor
                )
        );
    }

    @Test
    void precioCeroDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Producto(
                        "P001",
                        "Mouse",
                        "USB",
                        0,
                        10,
                        5,
                        categoria,
                        proveedor
                )
        );
    }

    @Test
    void precioNegativoDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Producto(
                        "P001",
                        "Mouse",
                        "USB",
                        -1,
                        10,
                        5,
                        categoria,
                        proveedor
                )
        );
    }

    @Test
    void stockNegativoDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Producto(
                        "P001",
                        "Mouse",
                        "USB",
                        50000,
                        -1,
                        5,
                        categoria,
                        proveedor
                )
        );
    }

    @Test
    void stockMinimoNegativoDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Producto(
                        "P001",
                        "Mouse",
                        "USB",
                        50000,
                        10,
                        -1,
                        categoria,
                        proveedor
                )
        );
    }

    @Test
    void categoriaNulaDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Producto(
                        "P001",
                        "Mouse",
                        "USB",
                        50000,
                        10,
                        5,
                        null,
                        proveedor
                )
        );
    }

    @Test
    void proveedorNuloDebeLanzarExcepcion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Producto(
                        "P001",
                        "Mouse",
                        "USB",
                        50000,
                        10,
                        5,
                        categoria,
                        null
                )
        );
    }

    // =========================
    // Setters
    // =========================

    @Test
    void modificarCodigo() {

        Producto producto = crearProducto();

        producto.setCodigo("P002");

        assertEquals(
                "P002",
                producto.getCodigo()
        );
    }

    @Test
    void modificarNombre() {

        Producto producto = crearProducto();

        producto.setNombre("Teclado");

        assertEquals(
                "Teclado",
                producto.getNombre()
        );
    }

    @Test
    void modificarDescripcion() {

        Producto producto = crearProducto();

        producto.setDescripcion("Teclado inalámbrico");

        assertEquals(
                "Teclado inalámbrico",
                producto.getDescripcion()
        );
    }

    @Test
    void modificarPrecio() {

        Producto producto = crearProducto();

        producto.setPrecio(100000);

        assertEquals(
                100000,
                producto.getPrecio()
        );
    }

    @Test
    void modificarStock() {

        Producto producto = crearProducto();

        producto.setStock(50);

        assertEquals(
                50,
                producto.getStock()
        );
    }

    @Test
    void modificarStockMinimo() {

        Producto producto = crearProducto();

        producto.setStockMinimo(10);

        assertEquals(
                10,
                producto.getStockMinimo()
        );
    }

    @Test
    void modificarCategoria() {

        Producto producto = crearProducto();

        Categoria nuevaCategoria = new Categoria(
                2,
                "Oficina",
                "Productos de oficina",
                true
        );

        producto.setCategoria(nuevaCategoria);

        assertEquals(
                nuevaCategoria,
                producto.getCategoria()
        );
    }

    @Test
    void modificarProveedor() {

        Producto producto = crearProducto();

        Proveedor nuevoProveedor = new Proveedor(
                2,
                "Dell",
                "900999999",
                "3009999999",
                "dell@dell.com",
                "Bogotá",
                true
        );

        producto.setProveedor(nuevoProveedor);

        assertEquals(
                nuevoProveedor,
                producto.getProveedor()
        );
    }

    // =========================
    // Validaciones setters
    // =========================

    @Test
    void setCodigoNuloDebeLanzarExcepcion() {

        Producto producto = crearProducto();

        assertThrows(
                IllegalArgumentException.class,
                () -> producto.setCodigo(null)
        );
    }

    @Test
    void setNombreVacioDebeLanzarExcepcion() {

        Producto producto = crearProducto();

        assertThrows(
                IllegalArgumentException.class,
                () -> producto.setNombre("")
        );
    }

    @Test
    void setDescripcionVaciaDebeLanzarExcepcion() {

        Producto producto = crearProducto();

        assertThrows(
                IllegalArgumentException.class,
                () -> producto.setDescripcion("")
        );
    }

    @Test
    void setPrecioCeroDebeLanzarExcepcion() {

        Producto producto = crearProducto();

        assertThrows(
                IllegalArgumentException.class,
                () -> producto.setPrecio(0)
        );
    }

    @Test
    void setStockNegativoDebeLanzarExcepcion() {

        Producto producto = crearProducto();

        assertThrows(
                IllegalArgumentException.class,
                () -> producto.setStock(-1)
        );
    }

    @Test
    void setStockMinimoNegativoDebeLanzarExcepcion() {

        Producto producto = crearProducto();

        assertThrows(
                IllegalArgumentException.class,
                () -> producto.setStockMinimo(-1)
        );
    }

    @Test
    void setCategoriaNulaDebeLanzarExcepcion() {

        Producto producto = crearProducto();

        assertThrows(
                IllegalArgumentException.class,
                () -> producto.setCategoria(null)
        );
    }

    @Test
    void setProveedorNuloDebeLanzarExcepcion() {

        Producto producto = crearProducto();

        assertThrows(
                IllegalArgumentException.class,
                () -> producto.setProveedor(null)
        );
    }

    // =========================
    // Stock
    // =========================

    @Test
    void aumentarStock() {

        Producto producto = crearProducto();

        producto.aumentarStock(15);

        assertEquals(
                35,
                producto.getStock()
        );
    }

    @Test
    void aumentarStockConCeroDebeLanzarExcepcion() {

        Producto producto = crearProducto();

        assertThrows(
                IllegalArgumentException.class,
                () -> producto.aumentarStock(0)
        );
    }

    @Test
    void aumentarStockConCantidadNegativaDebeLanzarExcepcion() {

        Producto producto = crearProducto();

        assertThrows(
                IllegalArgumentException.class,
                () -> producto.aumentarStock(-5)
        );
    }

    @Test
    void disminuirStock() {

        Producto producto = crearProducto();

        producto.disminuirStock(5);

        assertEquals(
                15,
                producto.getStock()
        );
    }

    @Test
    void disminuirStockHastaCero() {

        Producto producto = crearProducto();

        producto.disminuirStock(20);

        assertEquals(
                0,
                producto.getStock()
        );

        assertFalse(producto.hayStock());
    }

    @Test
    void disminuirStockConCeroDebeLanzarExcepcion() {

        Producto producto = crearProducto();

        assertThrows(
                IllegalArgumentException.class,
                () -> producto.disminuirStock(0)
        );
    }

    @Test
    void disminuirStockConCantidadNegativaDebeLanzarExcepcion() {

        Producto producto = crearProducto();

        assertThrows(
                IllegalArgumentException.class,
                () -> producto.disminuirStock(-5)
        );
    }

    @Test
    void disminuirMasDelStockDebeLanzarExcepcion() {

        Producto producto = crearProducto();

        assertThrows(
                IllegalArgumentException.class,
                () -> producto.disminuirStock(21)
        );
    }

    // =========================
    // Estado del stock
    // =========================

    @Test
    void hayStockDebeSerTrue() {

        Producto producto = crearProducto();

        assertTrue(
                producto.hayStock()
        );
    }

    @Test
    void hayStockDebeSerFalseCuandoStockEsCero() {

        Producto producto = crearProducto();

        producto.setStock(0);

        assertFalse(
                producto.hayStock()
        );
    }

    @Test
    void stockBajoDebeSerTrue() {

        Producto producto = crearProducto();

        producto.setStock(5);

        assertTrue(
                producto.stockBajo()
        );
    }

    @Test
    void stockBajoDebeSerFalseCuandoSuperaMinimo() {

        Producto producto = crearProducto();

        producto.setStock(10);

        assertFalse(
                producto.stockBajo()
        );
    }

    // =========================
    // equals
    // =========================

    @Test
    void equalsDebeSerTrueParaElMismoObjeto() {

        Producto producto = crearProducto();

        assertEquals(
                producto,
                producto
        );
    }

    @Test
    void equalsDebeCompararPorCodigo() {

        Producto p1 = new Producto(
                "P001",
                "Mouse",
                "USB",
                50000,
                10,
                5,
                categoria,
                proveedor
        );

        Producto p2 = new Producto(
                "P001",
                "Teclado",
                "USB",
                100000,
                20,
                5,
                categoria,
                proveedor
        );

        assertEquals(
                p1,
                p2
        );
    }

    @Test
    void productosConCodigoDiferenteNoDebenSerIguales() {

        Producto p1 = crearProducto();

        Producto p2 = new Producto(
                "P002",
                "Mouse",
                "USB",
                50000,
                10,
                5,
                categoria,
                proveedor
        );

        assertNotEquals(
                p1,
                p2
        );
    }

    @Test
    void productoNoDebeSerIgualANull() {

        Producto producto = crearProducto();

        assertNotEquals(
                producto,
                null
        );
    }

    @Test
    void productoNoDebeSerIgualAOtroTipo() {

        Producto producto = crearProducto();

        assertNotEquals(
                producto,
                "P001"
        );
    }

    // =========================
    // hashCode
    // =========================

    @Test
    void hashCodeDebeCoincidirParaMismoCodigo() {

        Producto p1 = new Producto(
                "P001",
                "Mouse",
                "USB",
                50000,
                10,
                5,
                categoria,
                proveedor
        );

        Producto p2 = new Producto(
                "P001",
                "Teclado",
                "USB",
                100000,
                20,
                5,
                categoria,
                proveedor
        );

        assertEquals(
                p1.hashCode(),
                p2.hashCode()
        );
    }

    // =========================
    // toString
    // =========================

    @Test
    void toStringDebeContenerInformacionDelProducto() {

        Producto producto = crearProducto();

        String resultado = producto.toString();

        assertTrue(
                resultado.contains("P001")
        );

        assertTrue(
                resultado.contains("Mouse")
        );

        assertTrue(
                resultado.contains("85000")
        );

        assertTrue(
                resultado.contains("Tecnología")
        );

        assertTrue(
                resultado.contains("HP")
        );
    }
}