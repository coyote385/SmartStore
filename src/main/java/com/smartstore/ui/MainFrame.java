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

        // =====================================================
        // ENCABEZADO
        // =====================================================

        JPanel encabezado = new JPanel(
                new BorderLayout()
        );

        encabezado.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        15,
                        8,
                        15
                )
        );

        encabezado.setBackground(
                new Color(35, 45, 55)
        );

        // Título
        JLabel titulo = new JLabel(
                "SmartStore"
        );

        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        titulo.setForeground(
                Color.WHITE
        );

        // Subtítulo
        JLabel subtitulo = new JLabel(
                "Sistema de gestión de tienda"
        );

        subtitulo.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        subtitulo.setForeground(
                new Color(210, 210, 210)
        );

        JPanel informacion = new JPanel(
                new GridLayout(2, 1)
        );

        informacion.setOpaque(false);

        informacion.add(titulo);
        informacion.add(subtitulo);

        encabezado.add(
                informacion,
                BorderLayout.WEST
        );

        add(
                encabezado,
                BorderLayout.NORTH
        );

        // =====================================================
// MENÚ LATERAL
// =====================================================

JPanel menu = new JPanel(new BorderLayout());

menu.setBackground(
        new Color(28, 36, 45)
);

menu.setBorder(
        BorderFactory.createEmptyBorder(
                10,
                8,
                10,
                8
        )
);

// -----------------------------------------------------
// BOTONES DEL MENÚ
// -----------------------------------------------------

JPanel opcionesMenu = new JPanel(
        new GridLayout(
                6,
                1,
                0,
                4
        )
);

opcionesMenu.setOpaque(false);

JButton btnInicio =
        new JButton("Inicio");

JButton btnClientes =
        new JButton("Clientes");

JButton btnProductos =
        new JButton("Productos");

JButton btnPedidos =
        new JButton("Pedidos");

JButton btnFacturas =
        new JButton("Facturas");

JButton btnReportes =
        new JButton("Reportes");

// Estilo
configurarBoton(btnInicio);
configurarBoton(btnClientes);
configurarBoton(btnProductos);
configurarBoton(btnPedidos);
configurarBoton(btnFacturas);
configurarBoton(btnReportes);

// Botón inicial activo
btnInicio.setBackground(
        new Color(0, 170, 220)
);

btnInicio.setForeground(
        Color.WHITE
);

// Agregar botones
opcionesMenu.add(btnInicio);
opcionesMenu.add(btnClientes);
opcionesMenu.add(btnProductos);
opcionesMenu.add(btnPedidos);
opcionesMenu.add(btnFacturas);
opcionesMenu.add(btnReportes);

menu.add(
        opcionesMenu,
        BorderLayout.NORTH
);

// -----------------------------------------------------
// BOTÓN SALIR
// -----------------------------------------------------

JButton btnSalir =
        new JButton("Salir");

configurarBoton(btnSalir);

btnSalir.setBackground(
        new Color(190, 60, 60)
);

btnSalir.setForeground(
        Color.WHITE
);

JPanel panelSalir = new JPanel(
        new BorderLayout()
);

panelSalir.setOpaque(false);

panelSalir.setBorder(
        BorderFactory.createEmptyBorder(
                10,
                0,
                0,
                0
        )
);

panelSalir.add(
        btnSalir,
        BorderLayout.CENTER
);

menu.add(
        panelSalir,
        BorderLayout.SOUTH
);

add(
        menu,
        BorderLayout.WEST
);

        // =====================================================
        // CONTENIDO
        // =====================================================

        panelContenido =
                new JPanel(
                        new BorderLayout()
                );

        add(
                panelContenido,
                BorderLayout.CENTER
        );

        // Mostrar inicio al arrancar
        mostrarInicio();

        // =====================================================
        // EVENTOS
        // =====================================================

        btnInicio.addActionListener(e ->
                mostrarInicio()
        );

        btnClientes.addActionListener(e ->
                mostrarPanel(
                        new PanelClientes(
                                clienteService
                        )
                )
        );

        btnProductos.addActionListener(e ->
                mostrarPanel(
                        new PanelProductos(
                                inventarioService
                        )
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

            int opcion =
                    JOptionPane.showConfirmDialog(
                            this,
                            "¿Desea salir de SmartStore?",
                            "Salir",
                            JOptionPane.YES_NO_OPTION
                    );

            if (opcion ==
                    JOptionPane.YES_OPTION) {

                System.exit(0);
            }
        });
    }

    // =========================================================
// CONFIGURAR BOTÓN
// =========================================================

private void configurarBoton(JButton boton) {

    boton.setFocusPainted(false);

    boton.setFont(
            new Font(
                    "Arial",
                    Font.BOLD,
                    13
            )
    );

    boton.setForeground(
            Color.WHITE
    );

    boton.setBackground(
            new Color(45, 57, 70)
    );

    boton.setBorder(
            BorderFactory.createEmptyBorder(
                    10,
                    15,
                    10,
                    15
            )
    );

    boton.setHorizontalAlignment(
            SwingConstants.LEFT
    );

    boton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
    );
}

    // =========================================================
    // MOSTRAR INICIO
    // =========================================================

    private void mostrarInicio() {

        mostrarPanel(
                new PanelInicio(
                        clienteService,
                        inventarioService,
                        pedidoService
                )
        );
    }

    // =========================================================
    // MOSTRAR PANEL
    // =========================================================

    private void mostrarPanel(
            JPanel panel) {

        panelContenido.removeAll();

        panelContenido.add(
                panel,
                BorderLayout.CENTER
        );

        panelContenido.revalidate();
        panelContenido.repaint();
    }
}