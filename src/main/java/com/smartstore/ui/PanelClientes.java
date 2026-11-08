package com.smartstore.ui;

import com.smartstore.algorithms.Ordenamientos;
import com.smartstore.model.Cliente;
import com.smartstore.service.ClienteService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelClientes extends JPanel {

    private final ClienteService clienteService;

    // Componentes del formulario
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCedula;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtDireccion;
    private JCheckBox chkActivo;

    // Componentes de búsqueda
    private JTextField txtBuscar;

    // Tabla
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public PanelClientes(ClienteService clienteService) {
        this.clienteService = clienteService;
        inicializarInterfaz();
        cargarDatosTabla(clienteService.listarClientes());
    }

    private void inicializarInterfaz() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(15, 20, 15, 20));
        setBackground(new Color(245, 247, 250));

        // =====================================================
        // ENCABEZADO
        // =====================================================
        JPanel panelNorte = new JPanel(new BorderLayout(10, 10));
        panelNorte.setOpaque(false);

        JLabel lblTitulo = new JLabel("Gestión de Clientes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(35, 45, 55));
        panelNorte.add(lblTitulo, BorderLayout.WEST);

        // Barra de búsqueda rápida
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panelBusqueda.setOpaque(false);

        txtBuscar = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnRefrescar = new JButton("Mostrar Todos");
        JButton btnOrdenarNombre = new JButton("Ordenar A-Z");

        estilizarBotonSecundario(btnBuscar);
        estilizarBotonSecundario(btnRefrescar);
        estilizarBotonSecundario(btnOrdenarNombre);

        panelBusqueda.add(new JLabel("Cédula / Nombre:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnRefrescar);
        panelBusqueda.add(btnOrdenarNombre);

        panelNorte.add(panelBusqueda, BorderLayout.EAST);
        add(panelNorte, BorderLayout.NORTH);

        // =====================================================
        // PANEL IZQUIERDO: FORMULARIO DE CLIENTE
        // =====================================================
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setPreferredSize(new Dimension(340, 0));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        "Datos del Cliente",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14),
                        new Color(35, 45, 55)
                )
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtCedula = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();
        txtDireccion = new JTextField();
        chkActivo = new JCheckBox("Cliente Activo", true);
        chkActivo.setOpaque(false);

        agregarCampo(panelFormulario, gbc, 0, "ID:", txtId);
        agregarCampo(panelFormulario, gbc, 1, "Cédula:", txtCedula);
        agregarCampo(panelFormulario, gbc, 2, "Nombre:", txtNombre);
        agregarCampo(panelFormulario, gbc, 3, "Apellido:", txtApellido);
        agregarCampo(panelFormulario, gbc, 4, "Teléfono:", txtTelefono);
        agregarCampo(panelFormulario, gbc, 5, "Correo:", txtCorreo);
        agregarCampo(panelFormulario, gbc, 6, "Dirección:", txtDireccion);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panelFormulario.add(chkActivo, gbc);

        // Botones de acción del formulario
        JPanel panelAcciones = new JPanel(new GridLayout(2, 2, 8, 8));
        panelAcciones.setOpaque(false);

        JButton btnGuardar = new JButton("Registrar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        estilizarBotonPrimario(btnGuardar);
        estilizarBotonSecundario(btnActualizar);
        estilizarBotonPeligro(btnEliminar);
        estilizarBotonSecundario(btnLimpiar);

        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnActualizar);
        panelAcciones.add(btnEliminar);
        panelAcciones.add(btnLimpiar);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        panelFormulario.add(panelAcciones, gbc);

        add(panelFormulario, BorderLayout.WEST);

        // =====================================================
        // PANEL CENTRAL: TABLA DE CLIENTES
        // =====================================================
        String[] columnas = {"ID", "Cédula", "Nombre", "Apellido", "Teléfono", "Correo", "Dirección", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setRowHeight(26);
        tablaClientes.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaClientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaClientes.getTableHeader().setBackground(new Color(235, 240, 245));
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaClientes.getSelectedRow() != -1) {
                cargarFormularioDesdeFila(tablaClientes.getSelectedRow());
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaClientes);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230)));
        add(scrollTabla, BorderLayout.CENTER);

        // =====================================================
        // EVENTOS
        // =====================================================
        btnGuardar.addActionListener(e -> registrarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        btnBuscar.addActionListener(e -> buscarClientes());
        btnRefrescar.addActionListener(e -> {
            txtBuscar.setText("");
            cargarDatosTabla(clienteService.listarClientes());
        });

        btnOrdenarNombre.addActionListener(e -> {
            List<Cliente> clientes = clienteService.listarClientes();
            Ordenamientos.quickSortClientesPorNombre(clientes, true);
            cargarDatosTabla(clientes);
        });
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(campo, gbc);
    }

    private void cargarDatosTabla(List<Cliente> clientes) {
        modeloTabla.setRowCount(0);
        for (Cliente c : clientes) {
            modeloTabla.addRow(new Object[]{
                    c.getId(),
                    c.getCedula(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getTelefono(),
                    c.getCorreo(),
                    c.getDireccion(),
                    c.isActivo() ? "Activo" : "Inactivo"
            });
        }
    }

    private void cargarFormularioDesdeFila(int fila) {
        txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtCedula.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtNombre.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtApellido.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtTelefono.setText(modeloTabla.getValueAt(fila, 4).toString());
        txtCorreo.setText(modeloTabla.getValueAt(fila, 5).toString());
        txtDireccion.setText(modeloTabla.getValueAt(fila, 6).toString());
        chkActivo.setSelected("Activo".equalsIgnoreCase(modeloTabla.getValueAt(fila, 7).toString()));
        txtId.setEditable(false);
    }

    private void registrarCliente() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String cedula = txtCedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();
            String direccion = txtDireccion.getText().trim();
            boolean activo = chkActivo.isSelected();

            Cliente cliente = new Cliente(id, nombre, apellido, cedula, telefono, correo, direccion, activo);
            clienteService.agregarCliente(cliente);

            cargarDatosTabla(clienteService.listarClientes());
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarCliente() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String cedula = txtCedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();
            String direccion = txtDireccion.getText().trim();
            boolean activo = chkActivo.isSelected();

            Cliente cliente = new Cliente(id, nombre, apellido, cedula, telefono, correo, direccion, activo);
            clienteService.actualizarCliente(cliente);

            cargarDatosTabla(clienteService.listarClientes());
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente válido de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar al cliente con ID " + id + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = clienteService.eliminarCliente(id);
                if (eliminado) {
                    cargarDatosTabla(clienteService.listarClientes());
                    limpiarFormulario();
                    JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el cliente con ID " + id, "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarClientes() {
        String criterio = txtBuscar.getText().trim().toLowerCase();
        if (criterio.isBlank()) {
            cargarDatosTabla(clienteService.listarClientes());
            return;
        }

        List<Cliente> filtrados = new ArrayList<>();
        for (Cliente c : clienteService.listarClientes()) {
            if (c.getCedula().toLowerCase().contains(criterio) ||
                c.getNombre().toLowerCase().contains(criterio) ||
                c.getApellido().toLowerCase().contains(criterio)) {
                filtrados.add(c);
            }
        }
        cargarDatosTabla(filtrados);
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtId.setEditable(true);
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        chkActivo.setSelected(true);
        tablaClientes.clearSelection();
    }

    private void estilizarBotonPrimario(JButton btn) {
        btn.setBackground(new Color(0, 150, 214));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void estilizarBotonSecundario(JButton btn) {
        btn.setBackground(new Color(230, 235, 240));
        btn.setForeground(new Color(40, 50, 60));
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void estilizarBotonPeligro(JButton btn) {
        btn.setBackground(new Color(210, 60, 60));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
