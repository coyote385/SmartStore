package com.smartstore.service;

import com.smartstore.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    private final List<Cliente> clientes;

    public ClienteService() {
        clientes = new ArrayList<>();
    }

    /**
     * Agrega un cliente al sistema.
     */
    public void agregarCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }

        if (buscarPorId(cliente.getId()) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con ese ID.");
        }

        if (buscarPorCedula(cliente.getCedula()) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con esa cédula.");
        }

        clientes.add(cliente);
    }

    /**
     * Busca un cliente por ID.
     */
    public Cliente buscarPorId(int id) {

        for (Cliente cliente : clientes) {

            if (cliente.getId() == id) {
                return cliente;
            }

        }

        return null;
    }

    /**
     * Busca un cliente por cédula.
     */
    public Cliente buscarPorCedula(String cedula) {

        for (Cliente cliente : clientes) {

            if (cliente.getCedula().equalsIgnoreCase(cedula)) {
                return cliente;
            }

        }

        return null;
    }

    /**
     * Actualiza un cliente existente.
     */
    public void actualizarCliente(Cliente clienteActualizado) {

        Cliente existente = buscarPorId(clienteActualizado.getId());

        if (existente == null) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }

        existente.setNombre(clienteActualizado.getNombre());
        existente.setApellido(clienteActualizado.getApellido());
        existente.setCedula(clienteActualizado.getCedula());
        existente.setTelefono(clienteActualizado.getTelefono());
        existente.setCorreo(clienteActualizado.getCorreo());
        existente.setDireccion(clienteActualizado.getDireccion());
        existente.setActivo(clienteActualizado.isActivo());
    }

    /**
     * Elimina un cliente por ID.
     */
    public boolean eliminarCliente(int id) {

        Cliente cliente = buscarPorId(id);

        if (cliente != null) {
            return clientes.remove(cliente);
        }

        return false;
    }

    /**
     * Devuelve todos los clientes.
     */
    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    /**
     * Obtiene únicamente los clientes activos.
     */
    public List<Cliente> obtenerClientesActivos() {

        List<Cliente> activos = new ArrayList<>();

        for (Cliente cliente : clientes) {

            if (cliente.isActivo()) {
                activos.add(cliente);
            }

        }

        return activos;
    }

    /**
     * Obtiene únicamente los clientes inactivos.
     */
    public List<Cliente> obtenerClientesInactivos() {

        List<Cliente> inactivos = new ArrayList<>();

        for (Cliente cliente : clientes) {

            if (!cliente.isActivo()) {
                inactivos.add(cliente);
            }

        }

        return inactivos;
    }

    /**
     * Cantidad de clientes registrados.
     */
    public int cantidadClientes() {
        return clientes.size();
    }

}