package com.smartstore.ui;

import com.smartstore.service.ClienteService;
import com.smartstore.service.InventarioService;
import com.smartstore.service.PedidoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelInicio extends JPanel {

    private final ClienteService clienteService;
    private final InventarioService inventarioService;
    private final PedidoService pedidoService;

    public PanelInicio(
            ClienteService clienteService,
            InventarioService inventarioService,
            PedidoService pedidoService) {

        this.clienteService = clienteService;
        this.inventarioService = inventarioService;
        this.pedidoService = pedidoService;

        inicializarInterfaz();
    }

    private void inicializarInterfaz() {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        // =====================================================
        // CONTENIDO PRINCIPAL
        // =====================================================

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(
                contenido,
                BoxLayout.Y_AXIS
        ));

        contenido.setBackground(
                new Color(245, 247, 250)
        );

        contenido.setBorder(
                new EmptyBorder(
                        15,
                        20,
                        10,
                        20
                )
        );

        // =====================================================
        // BANNER
        // =====================================================

        JPanel panelBanner = new JPanel(
                new BorderLayout()
        );

        panelBanner.setBackground(
                new Color(245, 247, 250)
        );

        JLabel banner = new JLabel();

        java.net.URL recurso =
                getClass().getResource(
                        "/images/smartstore-banner.png"
                );

        if (recurso != null) {

            ImageIcon icono =
                    new ImageIcon(recurso);

            Image imagen =
                    icono.getImage();

            Image imagenEscalada =
                    imagen.getScaledInstance(
                            780,
                            210,
                            Image.SCALE_SMOOTH
                    );

            banner.setIcon(
                    new ImageIcon(imagenEscalada)
            );

        } else {

            banner.setText(
                    "SMARTSTORE"
            );

            banner.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            30
                    )
            );

        }

        banner.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        panelBanner.add(
                banner,
                BorderLayout.CENTER
        );

        panelBanner.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        220
                )
        );

        contenido.add(panelBanner);

        // =====================================================
        // ESPACIO
        // =====================================================

        contenido.add(
                Box.createVerticalStrut(8)
        );

        // =====================================================
        // TÍTULO
        // =====================================================

        JLabel titulo = new JLabel(
                "Bienvenido a SmartStore",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        27
                )
        );

        titulo.setForeground(
                new Color(35, 45, 55)
        );

        titulo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        contenido.add(titulo);

        // =====================================================
        // DESCRIPCIÓN
        // =====================================================

        JLabel descripcion = new JLabel(
                "Sistema de gestión inteligente para productos, clientes y pedidos",
                SwingConstants.CENTER
        );

        descripcion.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        descripcion.setForeground(
                new Color(85, 95, 105)
        );

        descripcion.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        contenido.add(descripcion);

        // =====================================================
        // ESPACIO
        // =====================================================

        contenido.add(
                Box.createVerticalStrut(18)
        );

        // =====================================================
        // TARJETAS
        // =====================================================

        JPanel tarjetas = new JPanel(
                new GridLayout(
                        1,
                        3,
                        15,
                        0
                )
        );

        tarjetas.setBackground(
                new Color(245, 247, 250)
        );

        tarjetas.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        145
                )
        );

        tarjetas.add(
                crearTarjeta(
                        "CLIENTES",
                        String.valueOf(
                                clienteService.cantidadClientes()
                        ),
                        "Clientes registrados"
                )
        );

        tarjetas.add(
                crearTarjeta(
                        "PRODUCTOS",
                        String.valueOf(
                                inventarioService.cantidadProductos()
                        ),
                        "Productos en inventario"
                )
        );

        tarjetas.add(
                crearTarjeta(
                        "PEDIDOS",
                        String.valueOf(
                                pedidoService.cantidadPedidos()
                        ),
                        "Pedidos registrados"
                )
        );

        contenido.add(tarjetas);

        // =====================================================
        // ESPACIO
        // =====================================================

        contenido.add(
                Box.createVerticalStrut(12)
        );

        // =====================================================
        // ACCESOS RÁPIDOS
        // =====================================================

        JPanel accesos = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        5
                )
        );

        accesos.setBackground(
                new Color(245, 247, 250)
        );

        JButton btnClientes =
                new JButton("Gestionar clientes");

        JButton btnProductos =
                new JButton("Gestionar productos");

        JButton btnPedidos =
                new JButton("Gestionar pedidos");

        accesos.add(btnClientes);
        accesos.add(btnProductos);
        accesos.add(btnPedidos);

        contenido.add(accesos);

        // =====================================================
        // AGREGAR CONTENIDO
        // =====================================================

        add(
                contenido,
                BorderLayout.CENTER
        );

        // =====================================================
        // EVENTOS
        // =====================================================

        btnClientes.addActionListener(e ->
                abrirPanel(
                        new PanelClientes(
                                clienteService
                        )
                )
        );

        btnProductos.addActionListener(e ->
                abrirPanel(
                        new PanelProductos(
                                inventarioService
                        )
                )
        );

        btnPedidos.addActionListener(e ->
                abrirPanel(
                        new PanelPedidos(
                                pedidoService,
                                clienteService,
                                inventarioService
                        )
                )
        );
    }

    // =========================================================
    // TARJETA
    // =========================================================

    private JPanel crearTarjeta(
            String titulo,
            String valor,
            String descripcion) {

        JPanel tarjeta = new JPanel(
                new BorderLayout(
                        5,
                        5
                )
        );

        tarjeta.setBackground(Color.WHITE);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(210, 215, 220)
                        ),
                        new EmptyBorder(
                                10,
                                15,
                                10,
                                15
                        )
                )
        );

        JLabel lblTitulo = new JLabel(
                titulo,
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        lblTitulo.setForeground(
                new Color(55, 65, 75)
        );

        JLabel lblValor = new JLabel(
                valor,
                SwingConstants.CENTER
        );

        lblValor.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        lblValor.setForeground(
                new Color(30, 40, 50)
        );

        JLabel lblDescripcion = new JLabel(
                descripcion,
                SwingConstants.CENTER
        );

        lblDescripcion.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        lblDescripcion.setForeground(
                new Color(100, 105, 110)
        );

        tarjeta.add(
                lblTitulo,
                BorderLayout.NORTH
        );

        tarjeta.add(
                lblValor,
                BorderLayout.CENTER
        );

        tarjeta.add(
                lblDescripcion,
                BorderLayout.SOUTH
        );

        return tarjeta;
    }

    // =========================================================
    // CAMBIAR PANEL
    // =========================================================

    private void abrirPanel(JPanel panel) {

        Container padre = getParent();

        if (padre == null) {
            return;
        }

        padre.removeAll();

        padre.setLayout(
                new BorderLayout()
        );

        padre.add(
                panel,
                BorderLayout.CENTER
        );

        padre.revalidate();
        padre.repaint();
    }
}