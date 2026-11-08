package com.smartstore.ui;

import com.smartstore.model.Cliente;
import com.smartstore.model.DetallePedido;
import com.smartstore.model.Pedido;
import com.smartstore.model.Producto;
import com.smartstore.service.ClienteService;
import com.smartstore.service.InventarioService;
import com.smartstore.service.PedidoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelPedidos extends JPanel {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final InventarioService inventarioService;

    // Carrito del pedido actual
    private final List<DetallePedido> detallesEnConstruccion;

    // Componentes del creador de pedido
    private JTextField txtIdPedido;
    private JComboBox<ClienteItem> cmbClientes;
    private JComboBox<ProductoItem> cmbProductos;
    private JSpinner spnCantidad;
    private JLabel lblTotalPedidoActual;

    // Tablas
    private JTable tablaDetallesActuales;
    private DefaultTableModel modeloDetalles;

    private JTable tablaPedidosRegistrados;
    private DefaultTableModel modeloPedidos;

    public PanelPedidos(
            PedidoService pedidoService,
            ClienteService clienteService,
            InventarioService inventarioService) {

        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.inventarioService = inventarioService;
        this.detallesEnConstruccion = new ArrayList<>();

        inicializarInterfaz();
        refrescarCombos();
        cargarDatosTablaPedidos(pedidoService.listarPedidos());
    }

    private void inicializarInterfaz() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(15, 20, 15, 20));
        setBackground(new Color(245, 247, 250));

        // =====================================================
        // ENCABEZADO
        // =====================================================
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);

        JLabel lblTitulo = new JLabel("Gestión y Despacho de Pedidos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(35, 45, 55));
        panelNorte.add(lblTitulo, BorderLayout.WEST);

        // Botón rápido para despachar con la Cola FIFO
        JPanel panelBotonera = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotonera.setOpaque(false);

        JButton btnDespacharSiguiente = new JButton("Despachar Siguiente (Cola FIFO)");
        estilizarBotonPrimario(btnDespacharSiguiente);
        btnDespacharSiguiente.setBackground(new Color(40, 140, 75));

        JButton btnRefrescarListas = new JButton("Actualizar Listas");
        estilizarBotonSecundario(btnRefrescarListas);

        panelBotonera.add(btnDespacharSiguiente);
        panelBotonera.add(btnRefrescarListas);
        panelNorte.add(panelBotonera, BorderLayout.EAST);

        add(panelNorte, BorderLayout.NORTH);

        // =====================================================
        // PANEL IZQUIERDO: FORMULARIO Y DETALLES EN CONSTRUCCIÓN
        // =====================================================
        JPanel panelConstructor = new JPanel(new BorderLayout(10, 10));
        panelConstructor.setPreferredSize(new Dimension(440, 0));
        panelConstructor.setBackground(Color.WHITE);
        panelConstructor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        "Crear Nuevo Pedido",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14),
                        new Color(35, 45, 55)
                )
        ));

        // Campos de cabecera
        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 8, 8));
        panelCampos.setOpaque(false);

        txtIdPedido = new JTextField();
        cmbClientes = new JComboBox<>();
        cmbProductos = new JComboBox<>();
        spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));

        panelCampos.add(new JLabel("ID Pedido:"));
        panelCampos.add(txtIdPedido);
        panelCampos.add(new JLabel("Cliente:"));
        panelCampos.add(cmbClientes);
        panelCampos.add(new JLabel("Producto:"));
        panelCampos.add(cmbProductos);
        panelCampos.add(new JLabel("Cantidad:"));
        panelCampos.add(spnCantidad);

        panelConstructor.add(panelCampos, BorderLayout.NORTH);

        // Mini-tabla de detalles del pedido
        String[] colDetalles = {"Producto", "Cant.", "Precio Unit.", "Subtotal"};
        modeloDetalles = new DefaultTableModel(colDetalles, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaDetallesActuales = new JTable(modeloDetalles);
        tablaDetallesActuales.setRowHeight(22);
        JScrollPane scrollDetalles = new JScrollPane(tablaDetallesActuales);
        scrollDetalles.getViewport().setBackground(Color.WHITE);
        scrollDetalles.setBorder(BorderFactory.createTitledBorder("Productos en el Pedido Actual"));

        panelConstructor.add(scrollDetalles, BorderLayout.CENTER);

        // Panel inferior del constructor
        JPanel panelPieConstructor = new JPanel(new BorderLayout(8, 8));
        panelPieConstructor.setOpaque(false);

        JPanel panelBotonesDetalle = new JPanel(new GridLayout(1, 2, 8, 0));
        panelBotonesDetalle.setOpaque(false);
        JButton btnAgregarProducto = new JButton("+ Añadir Producto");
        JButton btnQuitarDetalle = new JButton("Quitar Seleccionado");
        estilizarBotonSecundario(btnAgregarProducto);
        estilizarBotonSecundario(btnQuitarDetalle);
        panelBotonesDetalle.add(btnAgregarProducto);
        panelBotonesDetalle.add(btnQuitarDetalle);

        lblTotalPedidoActual = new JLabel("Total: $0.00", SwingConstants.RIGHT);
        lblTotalPedidoActual.setFont(new Font("Arial", Font.BOLD, 15));
        lblTotalPedidoActual.setForeground(new Color(0, 120, 180));

        JButton btnConfirmarPedido = new JButton("Guardar Pedido");
        estilizarBotonPrimario(btnConfirmarPedido);

        JPanel panelTotalYConfirmar = new JPanel(new BorderLayout(8, 8));
        panelTotalYConfirmar.setOpaque(false);
        panelTotalYConfirmar.add(lblTotalPedidoActual, BorderLayout.NORTH);
        panelTotalYConfirmar.add(btnConfirmarPedido, BorderLayout.SOUTH);

        panelPieConstructor.add(panelBotonesDetalle, BorderLayout.NORTH);
        panelPieConstructor.add(panelTotalYConfirmar, BorderLayout.SOUTH);

        panelConstructor.add(panelPieConstructor, BorderLayout.SOUTH);
        add(panelConstructor, BorderLayout.WEST);

        // =====================================================
        // PANEL DERECHO: TABLA DE PEDIDOS REGISTRADOS
        // =====================================================
        JPanel panelTablaPedidos = new JPanel(new BorderLayout(10, 10));
        panelTablaPedidos.setOpaque(false);

        String[] colPedidos = {"ID", "Cliente", "Fecha", "Cant. Ítems", "Total ($)", "Estado"};
        modeloPedidos = new DefaultTableModel(colPedidos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPedidosRegistrados = new JTable(modeloPedidos);
        tablaPedidosRegistrados.setRowHeight(26);
        tablaPedidosRegistrados.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaPedidosRegistrados.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaPedidosRegistrados.getTableHeader().setBackground(new Color(235, 240, 245));
        tablaPedidosRegistrados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPedidos = new JScrollPane(tablaPedidosRegistrados);
        scrollPedidos.getViewport().setBackground(Color.WHITE);
        scrollPedidos.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230)));
        panelTablaPedidos.add(scrollPedidos, BorderLayout.CENTER);

        // Botones de acción sobre pedidos registrados
        JPanel panelAccionesPedidos = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelAccionesPedidos.setOpaque(false);

        JButton btnVerDetallePedido = new JButton("Ver Detalle");
        JButton btnCancelarPedido = new JButton("Cancelar Pedido");
        estilizarBotonSecundario(btnVerDetallePedido);
        estilizarBotonPeligro(btnCancelarPedido);

        panelAccionesPedidos.add(btnVerDetallePedido);
        panelAccionesPedidos.add(btnCancelarPedido);
        panelTablaPedidos.add(panelAccionesPedidos, BorderLayout.SOUTH);

        add(panelTablaPedidos, BorderLayout.CENTER);

        // =====================================================
        // EVENTOS
        // =====================================================
        btnAgregarProducto.addActionListener(e -> agregarDetalleAlPedidoActual());
        btnQuitarDetalle.addActionListener(e -> quitarDetalleSeleccionado());
        btnConfirmarPedido.addActionListener(e -> confirmarNuevoPedido());
        btnDespacharSiguiente.addActionListener(e -> despacharSiguienteDeLaCola());
        btnCancelarPedido.addActionListener(e -> cancelarPedidoSeleccionado());
        btnVerDetallePedido.addActionListener(e -> verDetallePedidoSeleccionado());

        btnRefrescarListas.addActionListener(e -> {
            refrescarCombos();
            cargarDatosTablaPedidos(pedidoService.listarPedidos());
        });
    }

    public void refrescarCombos() {
        cmbClientes.removeAllItems();
        for (Cliente c : clienteService.listarClientes()) {
            cmbClientes.addItem(new ClienteItem(c));
        }

        cmbProductos.removeAllItems();
        for (Producto p : inventarioService.obtenerProductos()) {
            cmbProductos.addItem(new ProductoItem(p));
        }
    }

    private void agregarDetalleAlPedidoActual() {
        ProductoItem itemProd = (ProductoItem) cmbProductos.getSelectedItem();
        if (itemProd == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un producto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cantidad = (Integer) spnCantidad.getValue();
        Producto p = itemProd.producto;

        if (p.getStock() < cantidad) {
            JOptionPane.showMessageDialog(this, "Stock insuficiente. Disponible: " + p.getStock(), "Stock Insuficiente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        detallesEnConstruccion.add(new DetallePedido(p, cantidad, p.getPrecio()));
        actualizarTablaDetallesActuales();
    }

    private void quitarDetalleSeleccionado() {
        int fila = tablaDetallesActuales.getSelectedRow();
        if (fila != -1) {
            detallesEnConstruccion.remove(fila);
            actualizarTablaDetallesActuales();
        }
    }

    private void actualizarTablaDetallesActuales() {
        modeloDetalles.setRowCount(0);
        double total = 0;
        for (DetallePedido d : detallesEnConstruccion) {
            modeloDetalles.addRow(new Object[]{
                    d.getProducto().getNombre(),
                    d.getCantidad(),
                    String.format("%.2f", d.getPrecioUnitario()),
                    String.format("%.2f", d.calcularSubtotal())
            });
            total += d.calcularSubtotal();
        }
        lblTotalPedidoActual.setText(String.format("Total: $%.2f", total));
    }

    private void confirmarNuevoPedido() {
        try {
            int id = Integer.parseInt(txtIdPedido.getText().trim());
            ClienteItem clienteItem = (ClienteItem) cmbClientes.getSelectedItem();

            if (clienteItem == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (detallesEnConstruccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe agregar al menos un producto al pedido.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Pedido pedido = new Pedido(id, clienteItem.cliente, "Pendiente");
            for (DetallePedido d : detallesEnConstruccion) {
                // Descontar stock
                Producto prod = d.getProducto();
                prod.setStock(prod.getStock() - d.getCantidad());
                pedido.agregarDetalle(d);
            }

            pedidoService.crearPedido(pedido);
            cargarDatosTablaPedidos(pedidoService.listarPedidos());

            detallesEnConstruccion.clear();
            actualizarTablaDetallesActuales();
            txtIdPedido.setText("");
            refrescarCombos();

            JOptionPane.showMessageDialog(this, "Pedido #" + id + " creado exitosamente y encolado para despacho.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID del pedido debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void despacharSiguienteDeLaCola() {
        if (!pedidoService.tienePedidosPendientes()) {
            JOptionPane.showMessageDialog(this, "No hay pedidos pendientes en la cola de despacho.", "Cola Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Pedido despachado = pedidoService.despacharSiguientePedido();
        cargarDatosTablaPedidos(pedidoService.listarPedidos());

        JOptionPane.showMessageDialog(this, "Pedido #" + despachado.getId() + " atendido y despachado con éxito (FIFO).\nCliente: " + despachado.getCliente().getNombre(), "Despacho Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cancelarPedidoSeleccionado() {
        int fila = tablaPedidosRegistrados.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) modeloPedidos.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de cancelar el pedido #" + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            pedidoService.eliminarPedido(id);
            cargarDatosTablaPedidos(pedidoService.listarPedidos());
            JOptionPane.showMessageDialog(this, "Pedido cancelado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void verDetallePedidoSeleccionado() {
        int fila = tablaPedidosRegistrados.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) modeloPedidos.getValueAt(fila, 0);
        Pedido pedido = pedidoService.buscarPedido(id);
        if (pedido == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("DETALLE DEL PEDIDO #").append(pedido.getId()).append("\n");
        sb.append("Cliente: ").append(pedido.getCliente().getNombre()).append(" ").append(pedido.getCliente().getApellido()).append("\n");
        sb.append("Fecha: ").append(pedido.getFecha()).append("\n");
        sb.append("Estado: ").append(pedido.getEstado()).append("\n\n");
        sb.append("ÍTEMS:\n");

        for (DetallePedido d : pedido.getDetalles()) {
            sb.append("- ").append(d.getProducto().getNombre())
              .append(" | Cant: ").append(d.getCantidad())
              .append(" | Precio: $").append(String.format("%.2f", d.getPrecioUnitario()))
              .append(" | Subtotal: $").append(String.format("%.2f", d.calcularSubtotal()))
              .append("\n");
        }
        sb.append("\nTOTAL: $").append(String.format("%.2f", pedido.calcularTotal()));

        JOptionPane.showMessageDialog(this, sb.toString(), "Información del Pedido #" + pedido.getId(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void cargarDatosTablaPedidos(List<Pedido> pedidos) {
        modeloPedidos.setRowCount(0);
        for (Pedido p : pedidos) {
            modeloPedidos.addRow(new Object[]{
                    p.getId(),
                    p.getCliente().getNombre() + " " + p.getCliente().getApellido(),
                    p.getFecha().toString(),
                    p.cantidadProductos(),
                    String.format("%.2f", p.calcularTotal()),
                    p.getEstado()
            });
        }
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

    // Clases auxiliares para ComboBox
    private static class ClienteItem {
        final Cliente cliente;
        ClienteItem(Cliente c) { this.cliente = c; }
        @Override
        public String toString() {
            return cliente.getId() + " - " + cliente.getNombre() + " " + cliente.getApellido() + " (" + cliente.getCedula() + ")";
        }
    }

    private static class ProductoItem {
        final Producto producto;
        ProductoItem(Producto p) { this.producto = p; }
        @Override
        public String toString() {
            return producto.getCodigo() + " - " + producto.getNombre() + " ($" + String.format("%.0f", producto.getPrecio()) + " | Stock: " + producto.getStock() + ")";
        }
    }
}
