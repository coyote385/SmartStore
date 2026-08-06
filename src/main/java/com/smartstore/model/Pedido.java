package com.smartstore.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un pedido realizado por un cliente.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class Pedido implements Serializable {

    private int id;
    private Cliente cliente;
    private LocalDate fecha;
    private String estado;
    private List<DetallePedido> detalles;

    /**
     * Constructor del pedido.
     *
     * @param id Identificador.
     * @param cliente Cliente que realiza el pedido.
     * @param estado Estado del pedido.
     */
    public Pedido(int id,
                  Cliente cliente,
                  String estado) {

        this.id = id;
        this.cliente = cliente;
        this.estado = estado;
        this.fecha = LocalDate.now();
        this.detalles = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {

        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("El estado es obligatorio.");
        }

        this.estado = estado;

    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    /**
     * Agrega un detalle al pedido.
     */
    public void agregarDetalle(DetallePedido detalle) {

        if (detalle == null) {
            throw new IllegalArgumentException("El detalle no puede ser nulo.");
        }

        detalles.add(detalle);

    }

    /**
     * Elimina un detalle.
     */
    public boolean eliminarDetalle(DetallePedido detalle) {

        return detalles.remove(detalle);

    }

    /**
     * Calcula el valor total del pedido.
     */
    public double calcularTotal() {

        double total = 0;

        for (DetallePedido detalle : detalles) {

            total += detalle.calcularSubtotal();

        }

        return total;

    }

    /**
     * Cantidad de productos diferentes.
     */
    public int cantidadProductos() {

        return detalles.size();

    }

    @Override
    public String toString() {

        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente.getNombre() +
                ", fecha=" + fecha +
                ", estado='" + estado + '\'' +
                ", total=" + calcularTotal() +
                '}';

    }

}