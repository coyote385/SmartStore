package com.smartstore.service;

import com.smartstore.model.Cliente;
import com.smartstore.model.DetallePedido;
import com.smartstore.model.Factura;
import com.smartstore.model.Pedido;
import com.smartstore.structures.ColaPedidos;
import com.smartstore.structures.PilaHistorial;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar los pedidos del sistema SmartStore.
 * Integra Cola FIFO para atención de pedidos y Pila para auditoría de acciones.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class PedidoService {

    private final List<Pedido> pedidos;
    private final ColaPedidos colaPendientes;
    private final PilaHistorial historial;
    private int consecutivoFactura;

    public PedidoService() {
        pedidos = new ArrayList<>();
        colaPendientes = new ColaPedidos();
        historial = new PilaHistorial();
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
        colaPendientes.enqueue(pedido);
        historial.push("Creado pedido #" + pedido.getId() + " para cliente " + pedido.getCliente().getNombre());
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
            historial.push("Eliminado pedido #" + id);
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
        return generarFactura(pedido, 0.19);
    }

    /**
     * Genera la factura del pedido con un porcentaje de IVA personalizado.
     */
    public Factura generarFactura(Pedido pedido, double iva) {

        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }

        Factura factura = new Factura(consecutivoFactura++, pedido, iva);
        historial.push("Generada factura #" + factura.getNumero() + " para pedido #" + pedido.getId());
        return factura;
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

    /**
     * Carga una lista de pedidos en el servicio.
     *
     * @param nuevosPedidos Lista de pedidos a cargar.
     */
    public void cargarPedidos(List<Pedido> nuevosPedidos) {
        limpiar();
        if (nuevosPedidos != null) {
            for (Pedido p : nuevosPedidos) {
                if (p != null && buscarPedido(p.getId()) == null) {
                    pedidos.add(p);
                    if ("Pendiente".equalsIgnoreCase(p.getEstado())) {
                        colaPendientes.enqueue(p);
                    }
                }
            }
        }
    }

    /**
     * Limpia todos los pedidos del servicio y reinicia la cola y el historial.
     */
    public void limpiar() {
        pedidos.clear();
        colaPendientes.clear();
        historial.clear();
    }

    /**
     * Despacha el siguiente pedido en la cola FIFO.
     *
     * @return El pedido despachado o null si no hay pedidos pendientes.
     */
    public Pedido despacharSiguientePedido() {
        if (colaPendientes.isEmpty()) {
            return null;
        }
        Pedido siguiente = colaPendientes.dequeue();
        siguiente.setEstado("Despachado");
        historial.push("Despachado pedido #" + siguiente.getId());
        return siguiente;
    }

    public Pedido consultarProximoPedido() {
        return colaPendientes.peek();
    }

    public int cantidadPedidosPendientes() {
        return colaPendientes.size();
    }

    public boolean tienePedidosPendientes() {
        return !colaPendientes.isEmpty();
    }

    public ColaPedidos getColaPendientes() {
        return colaPendientes;
    }

    public PilaHistorial getHistorial() {
        return historial;
    }

    public String obtenerUltimaOperacion() {
        return historial.peek();
    }
}