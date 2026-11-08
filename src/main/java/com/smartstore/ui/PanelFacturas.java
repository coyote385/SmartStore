package com.smartstore.ui;

import com.smartstore.model.DetallePedido;
import com.smartstore.model.Factura;
import com.smartstore.model.Pedido;
import com.smartstore.service.PedidoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelFacturas extends JPanel {

    private final PedidoService pedidoService;
    private final List<Factura> facturas;

    private JComboBox<PedidoItem> cmbPedidos;
    private JTextField txtIva;
    private JTable tablaFacturas;
    private DefaultTableModel modeloTabla;

    public PanelFacturas(
            PedidoService pedidoService,
            List<Factura> facturas) {

        this.pedidoService = pedidoService;
        this.facturas = facturas;

        inicializarInterfaz();
        refrescarComboPedidos();
        cargarDatosTabla();
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

        JLabel lblTitulo = new JLabel("Facturación y Comprobantes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(35, 45, 55));
        panelNorte.add(lblTitulo, BorderLayout.WEST);

        JButton btnRefrescar = new JButton("Actualizar");
        estilizarBotonSecundario(btnRefrescar);
        panelNorte.add(btnRefrescar, BorderLayout.EAST);
        add(panelNorte, BorderLayout.NORTH);

        // =====================================================
        // PANEL IZQUIERDO: FORMULARIO GENERACIÓN DE FACTURA
        // =====================================================
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setPreferredSize(new Dimension(340, 0));
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        "Generar Factura",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14),
                        new Color(35, 45, 55)
                )
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        cmbPedidos = new JComboBox<>();
        txtIva = new JTextField("19");

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.35;
        panelForm.add(new JLabel("Pedido:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.65;
        panelForm.add(cmbPedidos, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.35;
        panelForm.add(new JLabel("IVA (%):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.65;
        panelForm.add(txtIva, gbc);

        JButton btnGenerar = new JButton("Emitir Factura");
        estilizarBotonPrimario(btnGenerar);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        panelForm.add(btnGenerar, gbc);

        add(panelForm, BorderLayout.WEST);

        // =====================================================
        // PANEL CENTRAL: TABLA DE FACTURAS
        // =====================================================
        JPanel panelTabla = new JPanel(new BorderLayout(10, 10));
        panelTabla.setOpaque(false);

        String[] columnas = {"Factura #", "Pedido #", "Cliente", "Fecha", "Subtotal ($)", "IVA (%)", "Total ($)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaFacturas = new JTable(modeloTabla);
        tablaFacturas.setRowHeight(26);
        tablaFacturas.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaFacturas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaFacturas.getTableHeader().setBackground(new Color(235, 240, 245));
        tablaFacturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollTabla = new JScrollPane(tablaFacturas);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230)));
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        JButton btnVerDetalle = new JButton("Ver Factura Completa");
        estilizarBotonSecundario(btnVerDetalle);
        JPanel panelBotonInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonInferior.setOpaque(false);
        panelBotonInferior.add(btnVerDetalle);
        panelTabla.add(panelBotonInferior, BorderLayout.SOUTH);

        add(panelTabla, BorderLayout.CENTER);

        // =====================================================
        // EVENTOS
        // =====================================================
        btnGenerar.addActionListener(e -> emitirFactura());
        btnVerDetalle.addActionListener(e -> verDetalleFactura());
        btnRefrescar.addActionListener(e -> {
            refrescarComboPedidos();
            cargarDatosTabla();
        });
    }

    public void refrescarComboPedidos() {
        cmbPedidos.removeAllItems();
        for (Pedido p : pedidoService.listarPedidos()) {
            cmbPedidos.addItem(new PedidoItem(p));
        }
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        for (Factura f : facturas) {
            modeloTabla.addRow(new Object[]{
                    f.getNumero(),
                    f.getPedido().getId(),
                    f.getPedido().getCliente().getNombre() + " " + f.getPedido().getCliente().getApellido(),
                    f.getFecha().toString(),
                    String.format("%.2f", f.getPedido().calcularTotal()),
                    String.format("%.0f%%", f.getIva() * 100),
                    String.format("%.2f", f.calcularTotal())
            });
        }
    }

    private void emitirFactura() {
        PedidoItem item = (PedidoItem) cmbPedidos.getSelectedItem();
        if (item == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido para facturar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double porcentajeIva = Double.parseDouble(txtIva.getText().trim()) / 100.0;
            if (porcentajeIva < 0) {
                JOptionPane.showMessageDialog(this, "El porcentaje de IVA no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Factura nuevaFactura = pedidoService.generarFactura(item.pedido, porcentajeIva);
            facturas.add(nuevaFactura);

            cargarDatosTabla();
            JOptionPane.showMessageDialog(this, "Factura #" + nuevaFactura.getNumero() + " generada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El IVA debe ser un número válido (ej. 19).", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verDetalleFactura() {
        int fila = tablaFacturas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una factura de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numFactura = (Integer) modeloTabla.getValueAt(fila, 0);
        Factura encontrada = null;
        for (Factura f : facturas) {
            if (f.getNumero() == numFactura) {
                encontrada = f;
                break;
            }
        }

        if (encontrada == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("           SMARTSTORE - FACTURA          \n");
        sb.append("=========================================\n");
        sb.append("Factura N°: ").append(encontrada.getNumero()).append("\n");
        sb.append("Fecha: ").append(encontrada.getFecha()).append("\n");
        sb.append("Pedido N°: ").append(encontrada.getPedido().getId()).append("\n");
        sb.append("Cliente: ").append(encontrada.getPedido().getCliente().getNombre()).append(" ")
          .append(encontrada.getPedido().getCliente().getApellido()).append("\n");
        sb.append("Cédula: ").append(encontrada.getPedido().getCliente().getCedula()).append("\n");
        sb.append("Dirección: ").append(encontrada.getPedido().getCliente().getDireccion()).append("\n");
        sb.append("-----------------------------------------\n");
        sb.append("DETALLE DE PRODUCTOS:\n");
        for (DetallePedido d : encontrada.getPedido().getDetalles()) {
            sb.append(String.format("- %-18s x%-3d  $%10.2f\n",
                    d.getProducto().getNombre(),
                    d.getCantidad(),
                    d.calcularSubtotal()));
        }
        sb.append("-----------------------------------------\n");
        sb.append(String.format("Subtotal:               $%10.2f\n", encontrada.getPedido().calcularTotal()));
        sb.append(String.format("IVA (%.0f%%):             $%10.2f\n", encontrada.getIva() * 100, encontrada.calcularIVA()));
        sb.append(String.format("TOTAL A PAGAR:          $%10.2f\n", encontrada.calcularTotal()));
        sb.append("=========================================\n");

        JTextArea areaTexto = new JTextArea(sb.toString());
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaTexto.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(areaTexto), "Factura #" + encontrada.getNumero(), JOptionPane.INFORMATION_MESSAGE);
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

    private static class PedidoItem {
        final Pedido pedido;
        PedidoItem(Pedido p) { this.pedido = p; }
        @Override
        public String toString() {
            return "Pedido #" + pedido.getId() + " - " + pedido.getCliente().getNombre() + " ($" + String.format("%.2f", pedido.calcularTotal()) + ")";
        }
    }
}
