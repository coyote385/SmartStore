package com.smartstore.ui;

import com.smartstore.model.Cliente;
import com.smartstore.service.ClienteService;

import javax.swing.*;
import java.awt.*;

public class PanelClientes extends JPanel {

    private final ClienteService clienteService;

    public PanelClientes(ClienteService clienteService) {

        this.clienteService = clienteService;

        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel(
                "Gestión de Clientes",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(
                new FlowLayout()
        );

        JButton btnRegistrar =
                new JButton("Registrar cliente");

        JButton btnBuscar =
                new JButton("Buscar cliente");

        JButton btnActualizar =
                new JButton("Actualizar cliente");

        JButton btnEliminar =
                new JButton("Eliminar cliente");

        JButton btnListar =
                new JButton("Listar clientes");

        botones.add(btnRegistrar);
        botones.add(btnBuscar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        botones.add(btnListar);

        add(botones, BorderLayout.CENTER);

        btnRegistrar.addActionListener(e ->
                registrarCliente()
        );

        btnBuscar.addActionListener(e ->
                buscarCliente()
        );

        btnActualizar.addActionListener(e ->
                actualizarCliente()
        );

        btnEliminar.addActionListener(e ->
                eliminarCliente()
        );

        btnListar.addActionListener(e ->
                listarClientes()
        );
    }

    private void registrarCliente() {

        try {

            String idTexto = JOptionPane.showInputDialog(
                    this,
                    "ID del cliente:"
            );

            if (idTexto == null) {
                return;
            }

            int id = Integer.parseInt(idTexto);

            String nombre = JOptionPane.showInputDialog(
                    this,
                    "Nombre:"
            );

            String apellido = JOptionPane.showInputDialog(
                    this,
                    "Apellido:"
            );

            String cedula = JOptionPane.showInputDialog(
                    this,
                    "Cédula:"
            );

            String telefono = JOptionPane.showInputDialog(
                    this,
                    "Teléfono:"
            );

            String correo = JOptionPane.showInputDialog(
                    this,
                    "Correo:"
            );

            String direccion = JOptionPane.showInputDialog(
                    this,
                    "Dirección:"
            );

            if (nombre == null ||
                    apellido == null ||
                    cedula == null ||
                    telefono == null ||
                    correo == null ||
                    direccion == null) {

                return;
            }

            Cliente cliente = new Cliente(
                    id,
                    nombre,
                    apellido,
                    cedula,
                    telefono,
                    correo,
                    direccion,
                    true
            );

            clienteService.agregarCliente(cliente);

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente registrado correctamente."
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un número válido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void buscarCliente() {

        String texto = JOptionPane.showInputDialog(
                this,
                "Ingrese el ID del cliente:"
        );

        if (texto == null) {
            return;
        }

        try {

            int id = Integer.parseInt(texto);

            Cliente cliente =
                    clienteService.buscarPorId(id);

            if (cliente == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Cliente no encontrado."
                );

                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    cliente.toString(),
                    "Cliente encontrado",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID inválido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void actualizarCliente() {

        try {

            String texto = JOptionPane.showInputDialog(
                    this,
                    "ID del cliente:"
            );

            if (texto == null) {
                return;
            }

            int id = Integer.parseInt(texto);

            Cliente existente =
                    clienteService.buscarPorId(id);

            if (existente == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Cliente no encontrado."
                );

                return;
            }

            String nombre = JOptionPane.showInputDialog(
                    this,
                    "Nombre:",
                    existente.getNombre()
            );

            String apellido = JOptionPane.showInputDialog(
                    this,
                    "Apellido:",
                    existente.getApellido()
            );

            String cedula = JOptionPane.showInputDialog(
                    this,
                    "Cédula:",
                    existente.getCedula()
            );

            String telefono = JOptionPane.showInputDialog(
                    this,
                    "Teléfono:",
                    existente.getTelefono()
            );

            String correo = JOptionPane.showInputDialog(
                    this,
                    "Correo:",
                    existente.getCorreo()
            );

            String direccion = JOptionPane.showInputDialog(
                    this,
                    "Dirección:",
                    existente.getDireccion()
            );

            if (nombre == null ||
                    apellido == null ||
                    cedula == null ||
                    telefono == null ||
                    correo == null ||
                    direccion == null) {

                return;
            }

            Cliente actualizado = new Cliente(
                    id,
                    nombre,
                    apellido,
                    cedula,
                    telefono,
                    correo,
                    direccion,
                    existente.isActivo()
            );

            clienteService.actualizarCliente(actualizado);

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente actualizado correctamente."
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID inválido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarCliente() {

        String texto = JOptionPane.showInputDialog(
                this,
                "ID del cliente:"
        );

        if (texto == null) {
            return;
        }

        try {

            int id = Integer.parseInt(texto);

            boolean eliminado =
                    clienteService.eliminarCliente(id);

            if (eliminado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Cliente eliminado correctamente."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Cliente no encontrado."
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID inválido."
            );
        }
    }

    private void listarClientes() {

        StringBuilder texto =
                new StringBuilder();

        for (Cliente cliente :
                clienteService.listarClientes()) {

            texto.append(cliente)
                    .append("\n\n");
        }

        if (texto.length() == 0) {
            texto.append("No hay clientes registrados.");
        }

        JTextArea area =
                new JTextArea(texto.toString());

        area.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(area);

        scroll.setPreferredSize(
                new Dimension(700, 400)
        );

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Clientes",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}