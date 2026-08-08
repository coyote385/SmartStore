package com.smartstore.ui;

import com.smartstore.model.Cliente;
import com.smartstore.model.Factura;
import com.smartstore.model.Pedido;
import com.smartstore.model.Producto;
import com.smartstore.service.ClienteService;
import com.smartstore.service.InventarioService;
import com.smartstore.service.PedidoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelReportes extends JPanel {

    private final ClienteService clienteService;
    private final InventarioService inventarioService;
    private final PedidoService pedidoService;
    private final List<Factura> facturas;

    public PanelReportes(
            ClienteService clienteService,
            InventarioService inventarioService,
            PedidoService pedidoService,
            List<Factura> facturas) {

        this.clienteService = clienteService;
        this.inventarioService = inventarioService;
        this.pedidoService = pedidoService;
        this.facturas = facturas;

        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel(
                "Reportes",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(titulo, BorderLayout.NORTH);

        JPanel contenido = new JPanel(
                new GridLayout(3, 1, 10, 10)
        );

        JButton btnProductos =
                new JButton("Reporte de productos");

        JButton btnPedidos =
                new JButton("Reporte de pedidos");

        JButton btnClientes =
                new JButton("Reporte de clientes");

        contenido.add(btnProductos);
        contenido.add(btnPedidos);
        contenido.add(btnClientes);

        add(contenido, BorderLayout.CENTER);

        btnProductos.addActionListener(e ->
                reporteProductos()
        );

        btnPedidos.addActionListener(e ->
                reportePedidos()
        );

        btnClientes.addActionListener(e ->
                reporteClientes()
        );
    }

    private void reporteProductos() {

        StringBuilder texto =
                new StringBuilder();

        texto.append(
                "TOTAL DE PRODUCTOS: "
        ).append(
                inventarioService.cantidadProductos()
        ).append("\n\n");

        for (Producto producto :
                inventarioService.obtenerProductos()) {

            texto.append(
                    producto.getCodigo()
            ).append(" - ")
                    .append(
                            producto.getNombre()
                    )
                    .append(" | Stock: ")
                    .append(
                            producto.getStock()
                    )
                    .append("\n");
        }

        mostrarReporte(
                "Reporte de productos",
                texto.toString()
        );
    }

    private void reportePedidos() {

        StringBuilder texto =
                new StringBuilder();

        texto.append(
                "TOTAL DE PEDIDOS: "
        ).append(
                pedidoService.cantidadPedidos()
        ).append("\n\n");

        for (Pedido pedido :
                pedidoService.listarPedidos()) {

            texto.append(
                    "Pedido #"
            ).append(
                    pedido.getId()
            ).append(" | Cliente: ")
                    .append(
                            pedido.getCliente().getNombre()
                    )
                    .append(" | Estado: ")
                    .append(
                            pedido.getEstado()
                    )
                    .append(" | Total: $")
                    .append(
                            pedido.calcularTotal()
                    )
                    .append("\n");
        }

        texto.append("\nFACTURAS: ")
                .append(facturas.size());

        mostrarReporte(
                "Reporte de pedidos",
                texto.toString()
        );
    }

    private void reporteClientes() {

        StringBuilder texto =
                new StringBuilder();

        texto.append(
                "TOTAL DE CLIENTES: "
        ).append(
                clienteService.cantidadClientes()
        ).append("\n\n");

        texto.append(
                "CLIENTES ACTIVOS: "
        ).append(
                clienteService
                        .obtenerClientesActivos()
                        .size()
        ).append("\n");

        texto.append(
                "CLIENTES INACTIVOS: "
        ).append(
                clienteService
                        .obtenerClientesInactivos()
                        .size()
        ).append("\n\n");

        for (Cliente cliente :
                clienteService.listarClientes()) {

            texto.append(
                    cliente.getId()
            ).append(" - ")
                    .append(
                            cliente.getNombre()
                    )
                    .append(" ")
                    .append(
                            cliente.getApellido()
                    )
                    .append(" | ")
                    .append(
                            cliente.isActivo()
                                    ? "Activo"
                                    : "Inactivo"
                    )
                    .append("\n");
        }

        mostrarReporte(
                "Reporte de clientes",
                texto.toString()
        );
    }

    private void mostrarReporte(
            String titulo,
            String contenido) {

        JTextArea area =
                new JTextArea(contenido);

        area.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(area);

        scroll.setPreferredSize(
                new Dimension(750, 450)
        );

        JOptionPane.showMessageDialog(
                this,
                scroll,
                titulo,
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}