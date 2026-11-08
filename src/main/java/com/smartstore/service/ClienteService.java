package com.smartstore.service;

import com.smartstore.exceptions.ClienteNoEncontradoException;
import com.smartstore.model.Cliente;
import com.smartstore.structures.PilaHistorial;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar los clientes del sistema SmartStore.
 * Integra historial de operaciones mediante Pila LIFO.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class ClienteService {

    private final List<Cliente> clientes;
    private final PilaHistorial historial;

    /**
     * Constructor del servicio.
     */
    public ClienteService() {
        clientes = new ArrayList<>();
        historial = new PilaHistorial();
    }

    /**
     * Agrega un cliente al sistema.
     *
     * @param cliente Cliente que se desea agregar.
     */
    public void agregarCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        if (buscarPorId(cliente.getId()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un cliente con ese ID."
            );
        }

        if (buscarPorCedula(cliente.getCedula()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un cliente con esa cédula."
            );
        }

        clientes.add(cliente);
        historial.push("Agregado cliente ID " + cliente.getId() + " - " + cliente.getNombre() + " " + cliente.getApellido());
    }

    /**
     * Busca un cliente por su ID.
     *
     * @param id Identificador del cliente.
     * @return Cliente encontrado o null si no existe.
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
     * Busca un cliente por su cédula.
     *
     * @param cedula Cédula del cliente.
     * @return Cliente encontrado o null si no existe.
     */
    public Cliente buscarPorCedula(String cedula) {

        if (cedula == null || cedula.isBlank()) {
            return null;
        }

        for (Cliente cliente : clientes) {

            if (cliente.getCedula().equalsIgnoreCase(cedula)) {
                return cliente;
            }
        }

        return null;
    }

    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param clienteActualizado Datos nuevos del cliente.
     * @throws ClienteNoEncontradoException
     *         si el cliente no existe.
     */
    public void actualizarCliente(
            Cliente clienteActualizado)
            throws ClienteNoEncontradoException {

        if (clienteActualizado == null) {
            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        Cliente existente =
                buscarPorId(clienteActualizado.getId());

        if (existente == null) {
            throw new ClienteNoEncontradoException(
                    "Cliente no encontrado."
            );
        }

        existente.setNombre(
                clienteActualizado.getNombre()
        );

        existente.setApellido(
                clienteActualizado.getApellido()
        );

        existente.setCedula(
                clienteActualizado.getCedula()
        );

        existente.setTelefono(
                clienteActualizado.getTelefono()
        );

        existente.setCorreo(
                clienteActualizado.getCorreo()
        );

        existente.setDireccion(
                clienteActualizado.getDireccion()
        );

        existente.setActivo(
                clienteActualizado.isActivo()
        );

        historial.push("Actualizado cliente ID " + existente.getId());
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param id Identificador del cliente.
     * @return true si el cliente fue eliminado.
     */
    public boolean eliminarCliente(int id) {

        Cliente cliente = buscarPorId(id);

        if (cliente != null) {
            historial.push("Eliminado cliente ID " + id);
            return clientes.remove(cliente);
        }

        return false;
    }

    /**
     * Devuelve todos los clientes registrados.
     *
     * @return Copia de la lista de clientes.
     */
    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes);
    }

    /**
     * Obtiene únicamente los clientes activos.
     *
     * @return Lista de clientes activos.
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
     *
     * @return Lista de clientes inactivos.
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
     * Obtiene la cantidad de clientes registrados.
     *
     * @return Cantidad de clientes.
     */
    public int cantidadClientes() {
        return clientes.size();
    }

    /**
     * Carga una lista de clientes en el servicio.
     *
     * @param nuevosClientes Lista de clientes a cargar.
     */
    public void cargarClientes(List<Cliente> nuevosClientes) {
        clientes.clear();
        if (nuevosClientes != null) {
            for (Cliente c : nuevosClientes) {
                if (c != null && buscarPorId(c.getId()) == null && buscarPorCedula(c.getCedula()) == null) {
                    clientes.add(c);
                }
            }
        }
    }

    /**
     * Limpia todos los clientes del servicio y reinicia el historial.
     */
    public void limpiar() {
        clientes.clear();
        historial.clear();
    }

    public PilaHistorial getHistorial() {
        return historial;
    }

    public String obtenerUltimaOperacion() {
        return historial.peek();
    }
}