package com.smartstore.ui;

import com.smartstore.model.Factura;
import com.smartstore.model.Pedido;
import com.smartstore.service.PedidoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelFacturas extends JPanel {

    private final PedidoService pedidoService;
    private final List<Factura> facturas;

    public PanelFacturas(
            PedidoService pedidoService,
            List<Factura> facturas) {

        this.pedidoService = pedidoService;
        this.facturas = facturas;

        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel(
                "Gestión de Facturas",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(titulo, BorderLayout.NORTH);

        JPanel contenido = new JPanel(
                new FlowLayout()
        );

        JButton btnGenerar =
                new JButton("Generar factura");

        JButton btnConsultar =
                new JButton("Consultar factura");

        JButton btnListar =
                new JButton("Listar facturas");

        contenido.add(btnGenerar);
        contenido.add(btnConsultar);
        contenido.add(btnListar);

        add(contenido, BorderLayout.CENTER);

        btnGenerar.addActionListener(e ->
                generarFactura()
        );

        btnConsultar.addActionListener(e ->
                consultarFactura()
        );

        btnListar.addActionListener(e ->
                listarFacturas()
        );
    }

    private void generarFactura() {

        if (pedidoService.cantidadPedidos() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "No existen pedidos para facturar."
            );

            return;
        }

        String texto =
                JOptionPane.showInputDialog(
                        this,
                        "ID del pedido:"
                );

        if (texto == null) return;

        try {

            int id =
                    Integer.parseInt(texto);

            Pedido pedido =
                    pedidoService.buscarPedido(id);

            if (pedido == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Pedido no encontrado."
                );

                return;
            }

            Factura factura =
                    pedidoService.generarFactura(
                            pedido
                    );

            facturas.add(factura);

            JOptionPane.showMessageDialog(
                    this,
                    "Factura generada correctamente.\n\n"
                            + "Número: "
                            + factura.getNumero()
                            + "\nPedido: "
                            + pedido.getId()
                            + "\nSubtotal: $"
                            + pedido.calcularTotal()
                            + "\nIVA: $"
                            + factura.calcularIVA()
                            + "\nTotal: $"
                            + factura.calcularTotal(),
                    "Factura",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID inválido."
            );
        }
    }

    private void consultarFactura() {

        String texto =
                JOptionPane.showInputDialog(
                        this,
                        "Número de factura:"
                );

        if (texto == null) return;

        try {

            int numero =
                    Integer.parseInt(texto);

            for (Factura factura : facturas) {

                if (factura.getNumero() == numero) {

                    JOptionPane.showMessageDialog(
                            this,
                            factura.toString()
                                    + "\nIVA: $"
                                    + factura.calcularIVA()
                                    + "\nTotal: $"
                                    + factura.calcularTotal(),
                            "Factura",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    return;
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Factura no encontrada."
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Número inválido."
            );
        }
    }

    private void listarFacturas() {

        StringBuilder texto =
                new StringBuilder();

        for (Factura factura : facturas) {

            texto.append(factura)
                    .append("\n\n");
        }

        if (texto.length() == 0) {

            texto.append(
                    "No hay facturas registradas."
            );
        }

        JTextArea area =
                new JTextArea(
                        texto.toString()
                );

        area.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(area);

        scroll.setPreferredSize(
                new Dimension(700, 400)
        );

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Facturas",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}