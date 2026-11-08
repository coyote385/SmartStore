package com.smartstore.ui;

import com.smartstore.model.Cliente;
import com.smartstore.model.Factura;
import com.smartstore.model.Pedido;
import com.smartstore.model.Producto;
import com.smartstore.service.ClienteService;
import com.smartstore.service.InventarioService;
import com.smartstore.service.PedidoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PanelReportes extends JPanel {

    private final ClienteService clienteService;
    private final InventarioService inventarioService;
    private final PedidoService pedidoService;
    private final List<Factura> facturas;

    private JTextArea txtVisorReporte;
    private JLabel lblSubtituloReporte;

    public PanelReportes(
            ClienteService clienteService,
            InventarioService inventarioService,
            PedidoService pedidoService,
            List<Factura> facturas) {

        this.clienteService = clienteService;
        this.inventarioService = inventarioService;
        this.pedidoService = pedidoService;
        this.facturas = facturas;

        inicializarInterfaz();
        generarResumenEjecutivo();
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

        JLabel lblTitulo = new JLabel("Módulo de Reportes y Auditoría");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(35, 45, 55));

        lblSubtituloReporte = new JLabel("Seleccione una opción para emitir el reporte detallado.");
        lblSubtituloReporte.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSubtituloReporte.setForeground(new Color(100, 110, 120));

        JPanel panelTitulos = new JPanel(new GridLayout(2, 1, 0, 4));
        panelTitulos.setOpaque(false);
        panelTitulos.add(lblTitulo);
        panelTitulos.add(lblSubtituloReporte);
        panelNorte.add(panelTitulos, BorderLayout.WEST);

        // Barra de botones de reportes
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panelBotones.setOpaque(false);

        JButton btnResumen = new JButton("Resumen General");
        JButton btnProductos = new JButton("Inventario");
        JButton btnPedidos = new JButton("Pedidos");
        JButton btnClientes = new JButton("Clientes");
        JButton btnHistorial = new JButton("Auditoría (Pila)");

        estilizarBoton(btnResumen, new Color(0, 150, 214));
        estilizarBoton(btnProductos, new Color(50, 65, 80));
        estilizarBoton(btnPedidos, new Color(50, 65, 80));
        estilizarBoton(btnClientes, new Color(50, 65, 80));
        estilizarBoton(btnHistorial, new Color(40, 140, 75));

        panelBotones.add(btnResumen);
        panelBotones.add(btnProductos);
        panelBotones.add(btnPedidos);
        panelBotones.add(btnClientes);
        panelBotones.add(btnHistorial);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        add(panelNorte, BorderLayout.NORTH);

        // =====================================================
        // CENTRO: VISOR DE REPORTE INTEGRADO
        // =====================================================
        txtVisorReporte = new JTextArea();
        txtVisorReporte.setEditable(false);
        txtVisorReporte.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtVisorReporte.setBackground(Color.WHITE);
        txtVisorReporte.setBorder(new EmptyBorder(15, 15, 15, 15));

        JScrollPane scrollVisor = new JScrollPane(txtVisorReporte);
        scrollVisor.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230)));
        add(scrollVisor, BorderLayout.CENTER);

        // =====================================================
        // EVENTOS
        // =====================================================
        btnResumen.addActionListener(e -> generarResumenEjecutivo());
        btnProductos.addActionListener(e -> generarReporteProductos());
        btnPedidos.addActionListener(e -> generarReportePedidos());
        btnClientes.addActionListener(e -> generarReporteClientes());
        btnHistorial.addActionListener(e -> generarReporteAuditoria());
    }

    private void generarResumenEjecutivo() {
        lblSubtituloReporte.setText("Visualizando: Resumen Ejecutivo del Negocio");
        StringBuilder sb = new StringBuilder();
        sb.append("========================================================================================\n");
        sb.append("                              SMARTSTORE - RESUMEN EJECUTIVO                            \n");
        sb.append("========================================================================================\n\n");

        double totalFacturado = 0;
        for (Factura f : facturas) {
            totalFacturado += f.calcularTotal();
        }

        sb.append(String.format(" * Clientes Registrados:          %5d clientes\n", clienteService.cantidadClientes()));
        sb.append(String.format("   - Clientes Activos:            %5d\n", clienteService.obtenerClientesActivos().size()));
        sb.append(String.format("   - Clientes Inactivos:          %5d\n", clienteService.obtenerClientesInactivos().size()));
        sb.append(String.format(" * Productos en Inventario:       %5d referencias\n", inventarioService.cantidadProductos()));
        sb.append(String.format(" * Pedidos Totales:               %5d pedidos\n", pedidoService.cantidadPedidos()));
        sb.append(String.format(" * Pedidos Pendientes (Cola FIFO):%5d pedidos por despachar\n", pedidoService.cantidadPedidosPendientes()));
        sb.append(String.format(" * Facturas Emitidas:             %5d comprobantes\n", facturas.size()));
        sb.append(String.format(" * Total Facturado Acumulado:     $%,12.2f COP\n", totalFacturado));
        sb.append("\n========================================================================================\n");
        sb.append("ESTADO DE LAS ESTRUCTURAS DE DATOS EN MEMORIA:\n");
        sb.append(String.format(" - Tabla Hash de Productos:       %d nodos almacenados (Búsqueda O(1))\n", inventarioService.getTablaHash().size()));
        sb.append(String.format(" - Árbol BST de Categorías:       %d categorías indexadas\n", inventarioService.obtenerCategoriasOrdenadas().size()));
        sb.append(String.format(" - Cola FIFO de Pedidos:          %d pendientes\n", pedidoService.cantidadPedidosPendientes()));
        sb.append(String.format(" - Pila de Historial (Auditoría): %d acciones registradas\n",
                inventarioService.getHistorial().size() + clienteService.getHistorial().size() + pedidoService.getHistorial().size()));
        sb.append("========================================================================================\n");

        txtVisorReporte.setText(sb.toString());
        txtVisorReporte.setCaretPosition(0);
    }

    private void generarReporteProductos() {
        lblSubtituloReporte.setText("Visualizando: Reporte de Inventario de Productos");
        StringBuilder sb = new StringBuilder();
        sb.append("===================================================================================================\n");
        sb.append("                                 INVENTARIO DE PRODUCTOS SMARTSTORE                                \n");
        sb.append("===================================================================================================\n");
        sb.append(String.format("%-8s | %-24s | %-16s | %12s | %6s | %7s | %-15s\n",
                "CÓDIGO", "NOMBRE", "CATEGORÍA", "PRECIO", "STOCK", "MÍNIMO", "PROVEEDOR"));
        sb.append("---------------------------------------------------------------------------------------------------\n");

        for (Producto p : inventarioService.obtenerProductos()) {
            sb.append(String.format("%-8s | %-24s | %-16s | $%11.2f | %6d | %7d | %-15s\n",
                    p.getCodigo(),
                    truncar(p.getNombre(), 24),
                    truncar(p.getCategoria() != null ? p.getCategoria().getNombre() : "General", 16),
                    p.getPrecio(),
                    p.getStock(),
                    p.getStockMinimo(),
                    truncar(p.getProveedor() != null ? p.getProveedor().getNombre() : "Principal", 15)));
        }
        sb.append("===================================================================================================\n");
        sb.append(String.format("TOTAL REFERENCIAS: %d\n", inventarioService.cantidadProductos()));

        txtVisorReporte.setText(sb.toString());
        txtVisorReporte.setCaretPosition(0);
    }

    private void generarReportePedidos() {
        lblSubtituloReporte.setText("Visualizando: Reporte de Pedidos y Ventas");
        StringBuilder sb = new StringBuilder();
        sb.append("===================================================================================================\n");
        sb.append("                                   HISTORIAL GENERAL DE PEDIDOS                                    \n");
        sb.append("===================================================================================================\n");
        sb.append(String.format("%-6s | %-26s | %-12s | %6s | %14s | %-12s\n",
                "ID", "CLIENTE", "FECHA", "ÍTEMS", "TOTAL ($)", "ESTADO"));
        sb.append("---------------------------------------------------------------------------------------------------\n");

        for (Pedido p : pedidoService.listarPedidos()) {
            sb.append(String.format("%-6d | %-26s | %-12s | %6d | $%13.2f | %-12s\n",
                    p.getId(),
                    truncar(p.getCliente().getNombre() + " " + p.getCliente().getApellido(), 26),
                    p.getFecha().toString(),
                    p.cantidadProductos(),
                    p.calcularTotal(),
                    p.getEstado()));
        }
        sb.append("===================================================================================================\n");
        sb.append(String.format("TOTAL PEDIDOS: %d  |  EN COLA PENDIENTE: %d\n",
                pedidoService.cantidadPedidos(), pedidoService.cantidadPedidosPendientes()));

        txtVisorReporte.setText(sb.toString());
        txtVisorReporte.setCaretPosition(0);
    }

    private void generarReporteClientes() {
        lblSubtituloReporte.setText("Visualizando: Cartera de Clientes");
        StringBuilder sb = new StringBuilder();
        sb.append("===================================================================================================\n");
        sb.append("                                   CARTERA DE CLIENTES REGISTRADOS                                 \n");
        sb.append("===================================================================================================\n");
        sb.append(String.format("%-5s | %-12s | %-25s | %-12s | %-24s | %-8s\n",
                "ID", "CÉDULA", "NOMBRE COMPLETO", "TELÉFONO", "CORREO", "ESTADO"));
        sb.append("---------------------------------------------------------------------------------------------------\n");

        for (Cliente c : clienteService.listarClientes()) {
            sb.append(String.format("%-5d | %-12s | %-25s | %-12s | %-24s | %-8s\n",
                    c.getId(),
                    c.getCedula(),
                    truncar(c.getNombre() + " " + c.getApellido(), 25),
                    c.getTelefono(),
                    truncar(c.getCorreo(), 24),
                    c.isActivo() ? "Activo" : "Inactivo"));
        }
        sb.append("===================================================================================================\n");
        sb.append(String.format("TOTAL CLIENTES: %d  |  ACTIVOS: %d  |  INACTIVOS: %d\n",
                clienteService.cantidadClientes(),
                clienteService.obtenerClientesActivos().size(),
                clienteService.obtenerClientesInactivos().size()));

        txtVisorReporte.setText(sb.toString());
        txtVisorReporte.setCaretPosition(0);
    }

    private void generarReporteAuditoria() {
        lblSubtituloReporte.setText("Visualizando: Registro de Auditoría de Operaciones Recientes (Pila LIFO)");
        StringBuilder sb = new StringBuilder();
        sb.append("===================================================================================================\n");
        sb.append("                         HISTORIAL DE OPERACIONES DEL SISTEMA (PILA LIFO)                          \n");
        sb.append("===================================================================================================\n");
        sb.append("Las operaciones más recientes aparecen primero en la pila:\n\n");

        sb.append("--- AUDITORÍA DE INVENTARIO ---\n");
        sb.append("Última acción: ").append(inventarioService.obtenerUltimaOperacion() != null ? inventarioService.obtenerUltimaOperacion() : "Sin actividad reciente").append("\n\n");

        sb.append("--- AUDITORÍA DE PEDIDOS ---\n");
        sb.append("Última acción: ").append(pedidoService.obtenerUltimaOperacion() != null ? pedidoService.obtenerUltimaOperacion() : "Sin actividad reciente").append("\n\n");

        sb.append("--- AUDITORÍA DE CLIENTES ---\n");
        sb.append("Última acción: ").append(clienteService.obtenerUltimaOperacion() != null ? clienteService.obtenerUltimaOperacion() : "Sin actividad reciente").append("\n");
        sb.append("===================================================================================================\n");

        txtVisorReporte.setText(sb.toString());
        txtVisorReporte.setCaretPosition(0);
    }

    private String truncar(String texto, int longitud) {
        if (texto == null) return "";
        return texto.length() > longitud ? texto.substring(0, longitud - 2) + ".." : texto;
    }

    private void estilizarBoton(JButton btn, Color fondo) {
        btn.setBackground(fondo);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
