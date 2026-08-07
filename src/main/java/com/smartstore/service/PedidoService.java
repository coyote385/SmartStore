package com.smartstore.service;

import com.smartstore.model.Cliente;
import com.smartstore.model.DetallePedido;
import com.smartstore.model.Factura;
import com.smartstore.model.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private final List<Pedido> pedidos;
    private int consecutivoFactura;

    public PedidoService() {
        pedidos = new ArrayList<>();
        consecutivoFactura = 1;
    }

    /**
     * Registra un nuevo pedido.
     */
    public void crearPedido(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }

        if (buscarPedido(pedido.getId()) != null) {
            throw new IllegalArgumentException("Ya existe un pedido con ese ID.");
        }

        pedidos.add(pedido);
    }

    /**
     * Busca un pedido por su ID.
     */
    public Pedido buscarPedido(int id) {

        for (Pedido pedido : pedidos) {

            if (pedido.getId() == id) {
                return pedido;
            }

        }

        return null;
    }

    /**
     * Elimina un pedido.
     */
    public boolean eliminarPedido(int id) {

        Pedido pedido = buscarPedido(id);

        if (pedido != null) {
            return pedidos.remove(pedido);
        }

        return false;
    }

    /**
     * Devuelve todos los pedidos registrados.
     */
    public List<Pedido> listarPedidos() {
        return new ArrayList<>(pedidos);
    }

    /**
     * Agrega un producto (detalle) al pedido.
     */
    public void agregarProducto(Pedido pedido, DetallePedido detalle) {

        if (pedido == null || detalle == null) {
            throw new IllegalArgumentException("Pedido y detalle son obligatorios.");
        }

        pedido.agregarDetalle(detalle);
    }

    /**
     * Elimina un detalle del pedido.
     */
    public boolean quitarProducto(Pedido pedido, DetallePedido detalle) {

        if (pedido == null || detalle == null) {
            throw new IllegalArgumentException("Pedido y detalle son obligatorios.");
        }

        return pedido.eliminarDetalle(detalle);
    }

    /**
     * Calcula el total del pedido.
     */
    public double calcularTotal(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }

        return pedido.calcularTotal();
    }

    /**
     * Genera la factura del pedido.
     */
    public Factura generarFactura(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }

        return new Factura(consecutivoFactura++, pedido, 0.19);
    }

    /**
     * Cancela un pedido.
     */
    public void cancelarPedido(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }

        pedido.setEstado("Cancelado");
    }

    /**
     * Busca todos los pedidos de un cliente.
     */
    public List<Pedido> buscarPedidosPorCliente(Cliente cliente) {

        List<Pedido> resultado = new ArrayList<>();

        for (Pedido pedido : pedidos) {

            if (pedido.getCliente().getId() == cliente.getId()) {
                resultado.add(pedido);
            }

        }

        return resultado;
    }

    /**
     * Cantidad total de pedidos.
     */
    public int cantidadPedidos() {
        return pedidos.size();
    }

}