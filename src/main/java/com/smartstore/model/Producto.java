package com.smartstore.model;

import java.util.Objects;

/**
 * Representa un producto disponible en el inventario de SmartStore.
 *
 * Cada producto pertenece a una categoría y es suministrado por un proveedor.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class Producto {

    private String codigo;
    private String nombre;
    private String descripcion;

    private double precio;

    private int stock;
    private int stockMinimo;

    private Categoria categoria;
    private Proveedor proveedor;

    /**
     * Constructor completo.
     */
    public Producto(
            String codigo,
            String nombre,
            String descripcion,
            double precio,
            int stock,
            int stockMinimo,
            Categoria categoria,
            Proveedor proveedor) {

        setCodigo(codigo);
        setNombre(nombre);
        setDescripcion(descripcion);
        setPrecio(precio);
        setStock(stock);
        setStockMinimo(stockMinimo);

        if (categoria == null) {
            throw new IllegalArgumentException(
                    "La categoría es obligatoria."
            );
        }

        if (proveedor == null) {
            throw new IllegalArgumentException(
                    "El proveedor es obligatorio."
            );
        }

        this.categoria = categoria;
        this.proveedor = proveedor;
    }

    // =========================
    // Getters y Setters
    // =========================

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código es obligatorio."
            );
        }

        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio."
            );
        }

        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La descripción es obligatoria."
            );
        }

        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {

        if (precio <= 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor que cero."
            );
        }

        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {

        if (stock < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede ser negativo."
            );
        }

        this.stock = stock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {

        if (stockMinimo < 0) {
            throw new IllegalArgumentException(
                    "El stock mínimo no puede ser negativo."
            );
        }

        this.stockMinimo = stockMinimo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {

        if (categoria == null) {
            throw new IllegalArgumentException(
                    "La categoría es obligatoria."
            );
        }

        this.categoria = categoria;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {

        if (proveedor == null) {
            throw new IllegalArgumentException(
                    "El proveedor es obligatorio."
            );
        }

        this.proveedor = proveedor;
    }

    // =========================
    // Métodos de negocio
    // =========================

    /**
     * Incrementa el stock.
     */
    public void aumentarStock(int cantidad) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero."
            );
        }

        stock += cantidad;
    }

    /**
     * Disminuye el stock.
     */
    public void disminuirStock(int cantidad) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero."
            );
        }

        if (cantidad > stock) {
            throw new IllegalArgumentException(
                    "Stock insuficiente."
            );
        }

        stock -= cantidad;
    }

    /**
     * Indica si existe inventario disponible.
     */
    public boolean hayStock() {
        return stock > 0;
    }

    /**
     * Indica si el inventario llegó al mínimo permitido.
     */
    public boolean stockBajo() {
        return stock <= stockMinimo;
    }

    // =========================
    // Object
    // =========================

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Producto)) {
            return false;
        }

        Producto other = (Producto) obj;

        return Objects.equals(
                codigo,
                other.codigo
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {

        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", categoria=" + categoria.getNombre() +
                ", proveedor=" + proveedor.getNombre() +
                '}';
    }
}