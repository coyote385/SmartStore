package com.smartstore.ui;

import com.smartstore.model.Factura;
import com.smartstore.service.ClienteService;
import com.smartstore.service.InventarioService;
import com.smartstore.service.PedidoService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private JPanel panelContenido;

    private final ClienteService clienteService;
    private final InventarioService inventarioService;
    private final PedidoService pedidoService;
    private final List<Factura> facturas;

    public MainFrame() {

        clienteService = new ClienteService();
        inventarioService = new InventarioService();
        pedidoService = new PedidoService();
        facturas = new ArrayList<>();

        setTitle("SmartStore");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarInterfaz();
    }

    private void inicializarInterfaz() {

        setLayout(new BorderLayout());

        JPanel encabezado = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("SmartStore");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));

        encabezado.add(titulo, BorderLayout.WEST);

        add(encabezado, BorderLayout.NORTH);

        JPanel menu = new JPanel(
                new GridLayout(7, 1, 5, 5)
        );

        JButton btnInicio = new JButton("Inicio");
        JButton btnClientes = new JButton("Clientes");
        JButton btnProductos = new JButton("Productos");
        JButton btnPedidos = new JButton("Pedidos");
        JButton btnFacturas = new JButton("Facturas");
        JButton btnReportes = new JButton("Reportes");
        JButton btnSalir = new JButton("Salir");

        menu.add(btnInicio);
        menu.add(btnClientes);
        menu.add(btnProductos);
        menu.add(btnPedidos);
        menu.add(btnFacturas);
        menu.add(btnReportes);
        menu.add(btnSalir);

        add(menu, BorderLayout.WEST);

        panelContenido = new JPanel(new BorderLayout());

        add(
                panelContenido,
                BorderLayout.CENTER
        );

        mostrarPanel(new PanelInicio());

        btnInicio.addActionListener(e ->
                mostrarPanel(new PanelInicio())
        );

        btnClientes.addActionListener(e ->
                mostrarPanel(
                        new PanelClientes(clienteService)
                )
        );

        btnProductos.addActionListener(e ->
                mostrarPanel(
                        new PanelProductos(inventarioService)
                )
        );

        btnPedidos.addActionListener(e ->
                mostrarPanel(
                        new PanelPedidos(
                                pedidoService,
                                clienteService,
                                inventarioService
                        )
                )
        );

        btnFacturas.addActionListener(e ->
                mostrarPanel(
                        new PanelFacturas(
                                pedidoService,
                                facturas
                        )
                )
        );

        btnReportes.addActionListener(e ->
                mostrarPanel(
                        new PanelReportes(
                                clienteService,
                                inventarioService,
                                pedidoService,
                                facturas
                        )
                )
        );

        btnSalir.addActionListener(e -> {

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea salir de SmartStore?",
                    "Salir",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private void mostrarPanel(JPanel panel) {

        panelContenido.removeAll();

        panelContenido.add(
                panel,
                BorderLayout.CENTER
        );

        panelContenido.revalidate();
        panelContenido.repaint();
    }
}