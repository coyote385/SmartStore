package com.smartstore.model;

import java.io.Serializable;

/**
 * Representa un producto incluido dentro de un pedido.
 * Cada detalle almacena el producto, la cantidad solicitada
 * y el precio unitario al momento de la venta.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class DetallePedido implements Serializable {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    /**
     * Constructor de DetallePedido.
     *
     * @param producto Producto vendido.
     * @param cantidad Cantidad solicitada.
     * @param precioUnitario Precio del producto al momento de la venta.
     */
    public DetallePedido(Producto producto,
                         int cantidad,
                         double precioUnitario) {

        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {

        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }

        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {

        if (precioUnitario <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }

        this.precioUnitario = precioUnitario;
    }

    /**
     * Calcula el subtotal del detalle.
     *
     * @return subtotal del producto.
     */
    public double calcularSubtotal() {

        return cantidad * precioUnitario;

    }

    @Override
    public String toString() {

        return "DetallePedido{" +
                "producto=" + producto.getNombre() +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + calcularSubtotal() +
                '}';

    }

}