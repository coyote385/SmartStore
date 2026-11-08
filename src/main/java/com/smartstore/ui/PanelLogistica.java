package com.smartstore.ui;

import com.smartstore.service.LogisticaService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelLogistica extends JPanel {

    private final LogisticaService logisticaService;

    private JComboBox<String> cmbOrigen;
    private JComboBox<String> cmbDestino;
    private JTextArea txtResultadoRuta;
    private JTable tablaCentros;
    private DefaultTableModel modeloTabla;

    private JTextField txtNuevaCiudad;
    private JComboBox<String> cmbConectarCon;

    public PanelLogistica(LogisticaService logisticaService) {
        this.logisticaService = logisticaService;
        inicializarInterfaz();
        refrescarCiudades();
        cargarTablaCentros();
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

        JLabel lblTitulo = new JLabel("Red Logística y Cálculo de Rutas de Despacho");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(35, 45, 55));
        panelNorte.add(lblTitulo, BorderLayout.WEST);

        JButton btnRefrescar = new JButton("Actualizar Red");
        estilizarBotonSecundario(btnRefrescar);
        panelNorte.add(btnRefrescar, BorderLayout.EAST);
        add(panelNorte, BorderLayout.NORTH);

        // =====================================================
        // PANEL IZQUIERDO: CALCULAR RUTA Y GESTIONAR NODOS
        // =====================================================
        JPanel panelControl = new JPanel();
        panelControl.setLayout(new BoxLayout(panelControl, BoxLayout.Y_AXIS));
        panelControl.setPreferredSize(new Dimension(360, 0));
        panelControl.setOpaque(false);

        // Subpanel 1: Calcular Ruta
        JPanel panelCalculo = new JPanel(new GridLayout(3, 2, 8, 8));
        panelCalculo.setBackground(Color.WHITE);
        panelCalculo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        "Calcular Ruta de Envío (BFS)",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14),
                        new Color(35, 45, 55)
                )
        ));

        cmbOrigen = new JComboBox<>();
        cmbDestino = new JComboBox<>();

        panelCalculo.add(new JLabel("Ciudad Origen:"));
        panelCalculo.add(cmbOrigen);
        panelCalculo.add(new JLabel("Ciudad Destino:"));
        panelCalculo.add(cmbDestino);

        JButton btnCalcularRuta = new JButton("Calcular Ruta");
        JButton btnRecorridoBFS = new JButton("Ver BFS Completo");
        estilizarBotonPrimario(btnCalcularRuta);
        estilizarBotonSecundario(btnRecorridoBFS);

        panelCalculo.add(btnCalcularRuta);
        panelCalculo.add(btnRecorridoBFS);

        panelControl.add(panelCalculo);
        panelControl.add(Box.createVerticalStrut(15));

        // Subpanel 2: Agregar nuevo centro
        JPanel panelNuevo = new JPanel(new GridLayout(3, 2, 8, 8));
        panelNuevo.setBackground(Color.WHITE);
        panelNuevo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        "Expandir Red Logística",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14),
                        new Color(35, 45, 55)
                )
        ));

        txtNuevaCiudad = new JTextField();
        cmbConectarCon = new JComboBox<>();

        panelNuevo.add(new JLabel("Nueva Ciudad:"));
        panelNuevo.add(txtNuevaCiudad);
        panelNuevo.add(new JLabel("Conectar con:"));
        panelNuevo.add(cmbConectarCon);

        JButton btnAgregarConectar = new JButton("+ Conectar");
        estilizarBotonPrimario(btnAgregarConectar);
        btnAgregarConectar.setBackground(new Color(40, 140, 75));

        panelNuevo.add(new JLabel());
        panelNuevo.add(btnAgregarConectar);

        panelControl.add(panelNuevo);
        add(panelControl, BorderLayout.WEST);

        // =====================================================
        // PANEL CENTRAL: RESULTADOS Y TABLA DE LA RED
        // =====================================================
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setOpaque(false);

        // Visualización de la ruta
        txtResultadoRuta = new JTextArea(4, 20);
        txtResultadoRuta.setEditable(false);
        txtResultadoRuta.setFont(new Font("Monospaced", Font.BOLD, 14));
        txtResultadoRuta.setForeground(new Color(0, 100, 160));
        txtResultadoRuta.setBackground(new Color(240, 248, 255));
        txtResultadoRuta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 215, 240)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        txtResultadoRuta.setText("Seleccione origen y destino para calcular la ruta de entrega óptima.");

        panelCentral.add(txtResultadoRuta, BorderLayout.NORTH);

        // Tabla de la red de distribución
        String[] columnas = {"Centro de Distribución", "N° Conexiones", "Centros Directos Conectados (Vecinos)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCentros = new JTable(modeloTabla);
        tablaCentros.setRowHeight(26);
        tablaCentros.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaCentros.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaCentros.getTableHeader().setBackground(new Color(235, 240, 245));

        JScrollPane scrollTabla = new JScrollPane(tablaCentros);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Topología Actual de la Red Logística"));
        panelCentral.add(scrollTabla, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);

        // =====================================================
        // EVENTOS
        // =====================================================
        btnCalcularRuta.addActionListener(e -> calcularRuta());
        btnRecorridoBFS.addActionListener(e -> mostrarRecorridoBFS());
        btnAgregarConectar.addActionListener(e -> agregarYConectarCentro());
        btnRefrescar.addActionListener(e -> {
            refrescarCiudades();
            cargarTablaCentros();
        });
    }

    private void refrescarCiudades() {
        cmbOrigen.removeAllItems();
        cmbDestino.removeAllItems();
        cmbConectarCon.removeAllItems();

        List<String> centros = logisticaService.listarCentros();
        for (String c : centros) {
            cmbOrigen.addItem(c);
            cmbDestino.addItem(c);
            cmbConectarCon.addItem(c);
        }

        if (cmbDestino.getItemCount() > 1) {
            cmbDestino.setSelectedIndex(1);
        }
    }

    private void cargarTablaCentros() {
        modeloTabla.setRowCount(0);
        for (String centro : logisticaService.listarCentros()) {
            List<String> vecinos = logisticaService.consultarCentrosConectados(centro);
            modeloTabla.addRow(new Object[]{
                    centro,
                    vecinos.size(),
                    String.join(", ", vecinos)
            });
        }
    }

    private void calcularRuta() {
        String origen = (String) cmbOrigen.getSelectedItem();
        String destino = (String) cmbDestino.getSelectedItem();

        if (origen == null || destino == null) {
            JOptionPane.showMessageDialog(this, "Seleccione origen y destino.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<String> ruta = logisticaService.calcularRutaEntrega(origen, destino);
        if (ruta.isEmpty()) {
            txtResultadoRuta.setText("No existe una ruta terrestre conectada entre " + origen + " y " + destino);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("RUTA ÓPTIMA ENCONTRADA (Algoritmo BFS):\n");
            sb.append(String.join("  ➔  ", ruta)).append("\n");
            sb.append("Total de saltos intermedios: ").append(ruta.size() - 1).append(" tramos.");
            txtResultadoRuta.setText(sb.toString());
        }
    }

    private void mostrarRecorridoBFS() {
        String origen = (String) cmbOrigen.getSelectedItem();
        if (origen == null) return;

        List<String> bfs = logisticaService.recorridoBFS(origen);
        JOptionPane.showMessageDialog(this,
                "Recorrido por Niveles (BFS) partiendo desde " + origen + ":\n\n" + String.join(" ➔ ", bfs),
                "Recorrido BFS de la Red", JOptionPane.INFORMATION_MESSAGE);
    }

    private void agregarYConectarCentro() {
        String nueva = txtNuevaCiudad.getText().trim();
        String conectar = (String) cmbConectarCon.getSelectedItem();

        if (nueva.isBlank() || conectar == null) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre de la nueva ciudad.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        logisticaService.conectarCentros(conectar, nueva);
        txtNuevaCiudad.setText("");
        refrescarCiudades();
        cargarTablaCentros();

        JOptionPane.showMessageDialog(this, "Centro " + nueva + " conectado con éxito a " + conectar, "Red Actualizada", JOptionPane.INFORMATION_MESSAGE);
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
}
