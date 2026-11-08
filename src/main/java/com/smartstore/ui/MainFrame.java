package com.smartstore.ui;

import com.smartstore.model.Cliente;
import com.smartstore.model.Factura;
import com.smartstore.model.Pedido;
import com.smartstore.model.Producto;
import com.smartstore.service.ClienteService;
import com.smartstore.service.InventarioService;
import com.smartstore.service.LogisticaService;
import com.smartstore.service.PedidoService;
import com.smartstore.service.PersistenciaService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private JPanel panelContenido;

    private final ClienteService clienteService;
    private final InventarioService inventarioService;
    private final PedidoService pedidoService;
    private final LogisticaService logisticaService;
    private final List<Factura> facturas;
    private final PersistenciaService persistenciaService;

    public MainFrame() {

        clienteService = new ClienteService();
        inventarioService = new InventarioService();
        pedidoService = new PedidoService();
        logisticaService = new LogisticaService();
        facturas = new ArrayList<>();
        persistenciaService = new PersistenciaService();

        cargarDatosDesdeArchivos();

        setTitle("SmartStore");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarYSalir();
            }
        });

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
                7,
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

JButton btnLogistica =
        new JButton("Logística y Rutas");

JButton btnReportes =
        new JButton("Reportes");

// Estilo
configurarBoton(btnInicio);
configurarBoton(btnClientes);
configurarBoton(btnProductos);
configurarBoton(btnPedidos);
configurarBoton(btnFacturas);
configurarBoton(btnLogistica);
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
opcionesMenu.add(btnLogistica);
opcionesMenu.add(btnReportes);

menu.add(
        opcionesMenu,
        BorderLayout.NORTH
);

// -----------------------------------------------------
// BOTONES INFERIORES: GUARDAR Y SALIR
// -----------------------------------------------------

JButton btnGuardar = new JButton("Guardar datos");
configurarBoton(btnGuardar);
btnGuardar.setBackground(new Color(40, 140, 75));
btnGuardar.setForeground(Color.WHITE);

JButton btnSalir =
        new JButton("Salir");

configurarBoton(btnSalir);

btnSalir.setBackground(
        new Color(190, 60, 60)
);

btnSalir.setForeground(
        Color.WHITE
);

JPanel panelInferior = new JPanel(
        new GridLayout(2, 1, 0, 6)
);

panelInferior.setOpaque(false);

panelInferior.setBorder(
        BorderFactory.createEmptyBorder(
                10,
                0,
                0,
                0
        )
);

panelInferior.add(btnGuardar);
panelInferior.add(btnSalir);

menu.add(
        panelInferior,
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

        btnLogistica.addActionListener(e ->
                mostrarPanel(
                        new PanelLogistica(
                                logisticaService
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

        btnGuardar.addActionListener(e -> {
            guardarDatosEnArchivos();
            JOptionPane.showMessageDialog(
                    this,
                    "Datos guardados exitosamente en los archivos .txt",
                    "Persistencia Exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        btnSalir.addActionListener(e -> confirmarYSalir());
    }

    // =========================================================
    // PERSISTENCIA DE DATOS (SERIALIZACIÓN / DESERIALIZACIÓN)
    // =========================================================

    public void cargarDatosDesdeArchivos() {
        try {
            List<Cliente> clientesCargados = persistenciaService.cargarClientes();
            clienteService.cargarClientes(clientesCargados);

            List<Producto> productosCargados = persistenciaService.cargarProductos();
            inventarioService.cargarProductos(productosCargados);

            List<Pedido> pedidosCargados = persistenciaService.cargarPedidos();
            pedidoService.cargarPedidos(pedidosCargados);

            List<Factura> facturasCargadas = persistenciaService.cargarFacturas();
            facturas.clear();
            facturas.addAll(facturasCargadas);
        } catch (Exception ex) {
            System.err.println("Advertencia al cargar datos: " + ex.getMessage());
        }
    }

    public void guardarDatosEnArchivos() {
        try {
            persistenciaService.guardarClientes(clienteService.listarClientes());
            persistenciaService.guardarProductos(inventarioService.obtenerProductos());
            persistenciaService.guardarPedidos(pedidoService.listarPedidos());
            persistenciaService.guardarFacturas(facturas);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar los datos: " + ex.getMessage(),
                    "Error de persistencia",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void confirmarYSalir() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea guardar los datos antes de salir?",
                "Salir de SmartStore",
                JOptionPane.YES_NO_CANCEL_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            guardarDatosEnArchivos();
            System.exit(0);
        } else if (opcion == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
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