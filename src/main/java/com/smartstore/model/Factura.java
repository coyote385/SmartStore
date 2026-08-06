package com.smartstore.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Representa la factura generada a partir de un pedido.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class Factura implements Serializable {

    private int numero;
    private Pedido pedido;
    private LocalDate fecha;
    private double iva;

    /**
     * Constructor de la factura.
     *
     * @param numero Número de la factura.
     * @param pedido Pedido asociado.
     * @param iva Porcentaje de IVA (ej. 0.19).
     */
    public Factura(int numero, Pedido pedido, double iva) {

        if (pedido == null) {
            throw new IllegalArgumentException("El pedido es obligatorio.");
        }

        if (iva < 0) {
            throw new IllegalArgumentException("El IVA no puede ser negativo.");
        }

        this.numero = numero;
        this.pedido = pedido;
        this.iva = iva;
        this.fecha = LocalDate.now();

    }

    public int getNumero() {
        return numero;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {

        if (iva < 0) {
            throw new IllegalArgumentException("El IVA no puede ser negativo.");
        }

        this.iva = iva;

    }

    /**
     * Calcula el valor del IVA.
     *
     * @return Valor del IVA.
     */
    public double calcularIVA() {

        return pedido.calcularTotal() * iva;

    }

    /**
     * Calcula el total de la factura.
     *
     * @return Total con IVA.
     */
    public double calcularTotal() {

        return pedido.calcularTotal() + calcularIVA();

    }

    @Override
    public String toString() {

        return "Factura{" +
                "numero=" + numero +
                ", fecha=" + fecha +
                ", total=" + calcularTotal() +
                '}';

    }

}