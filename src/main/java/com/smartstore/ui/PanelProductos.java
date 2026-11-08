package com.smartstore.ui;

import com.smartstore.algorithms.Ordenamientos;
import com.smartstore.model.Categoria;
import com.smartstore.model.Producto;
import com.smartstore.model.Proveedor;
import com.smartstore.service.InventarioService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelProductos extends JPanel {

    private final InventarioService inventarioService;

    // Componentes del formulario
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private JTextField txtStockMinimo;
    private JTextField txtCategoria;
    private JTextField txtProveedor;

    // Búsqueda
    private JTextField txtBuscar;

    // Tabla
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public PanelProductos(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
        inicializarInterfaz();
        cargarDatosTabla(inventarioService.obtenerProductos());
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

        JLabel lblTitulo = new JLabel("Gestión de Productos e Inventario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(35, 45, 55));
        panelNorte.add(lblTitulo, BorderLayout.WEST);

        // Barra de búsqueda y ordenamiento
        JPanel panelAccionesSup = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panelAccionesSup.setOpaque(false);

        txtBuscar = new JTextField(12);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnRefrescar = new JButton("Ver Todos");
        JButton btnOrdenarPrecio = new JButton("Ordenar Precio");
        JButton btnOrdenarNombre = new JButton("Ordenar A-Z");

        estilizarBotonSecundario(btnBuscar);
        estilizarBotonSecundario(btnRefrescar);
        estilizarBotonSecundario(btnOrdenarPrecio);
        estilizarBotonSecundario(btnOrdenarNombre);

        panelAccionesSup.add(new JLabel("Código / Nombre:"));
        panelAccionesSup.add(txtBuscar);
        panelAccionesSup.add(btnBuscar);
        panelAccionesSup.add(btnRefrescar);
        panelAccionesSup.add(btnOrdenarPrecio);
        panelAccionesSup.add(btnOrdenarNombre);

        panelNorte.add(panelAccionesSup, BorderLayout.EAST);
        add(panelNorte, BorderLayout.NORTH);

        // =====================================================
        // PANEL IZQUIERDO: FORMULARIO DE PRODUCTO
        // =====================================================
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setPreferredSize(new Dimension(360, 0));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        "Datos del Producto",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14),
                        new Color(35, 45, 55)
                )
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtDescripcion = new JTextField();
        txtPrecio = new JTextField();
        txtStock = new JTextField();
        txtStockMinimo = new JTextField();
        txtCategoria = new JTextField();
        txtProveedor = new JTextField();

        agregarCampo(panelFormulario, gbc, 0, "Código:", txtCodigo);
        agregarCampo(panelFormulario, gbc, 1, "Nombre:", txtNombre);
        agregarCampo(panelFormulario, gbc, 2, "Descripción:", txtDescripcion);
        agregarCampo(panelFormulario, gbc, 3, "Precio ($):", txtPrecio);
        agregarCampo(panelFormulario, gbc, 4, "Stock:", txtStock);
        agregarCampo(panelFormulario, gbc, 5, "Stock Mínimo:", txtStockMinimo);
        agregarCampo(panelFormulario, gbc, 6, "Categoría:", txtCategoria);
        agregarCampo(panelFormulario, gbc, 7, "Proveedor:", txtProveedor);

        // Botones de acción del formulario
        JPanel panelBotonesForm = new JPanel(new GridLayout(2, 2, 8, 8));
        panelBotonesForm.setOpaque(false);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        estilizarBotonPrimario(btnRegistrar);
        estilizarBotonSecundario(btnModificar);
        estilizarBotonPeligro(btnEliminar);
        estilizarBotonSecundario(btnLimpiar);

        panelBotonesForm.add(btnRegistrar);
        panelBotonesForm.add(btnModificar);
        panelBotonesForm.add(btnEliminar);
        panelBotonesForm.add(btnLimpiar);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        panelFormulario.add(panelBotonesForm, gbc);

        add(panelFormulario, BorderLayout.WEST);

        // =====================================================
        // PANEL CENTRAL: TABLA DE PRODUCTOS
        // =====================================================
        String[] columnas = {"Código", "Nombre", "Descripción", "Precio ($)", "Stock", "Mínimo", "Categoría", "Proveedor"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setRowHeight(26);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaProductos.getTableHeader().setBackground(new Color(235, 240, 245));
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaProductos.getSelectedRow() != -1) {
                cargarFormularioDesdeFila(tablaProductos.getSelectedRow());
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaProductos);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230)));
        add(scrollTabla, BorderLayout.CENTER);

        // =====================================================
        // EVENTOS
        // =====================================================
        btnRegistrar.addActionListener(e -> registrarProducto());
        btnModificar.addActionListener(e -> modificarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        btnBuscar.addActionListener(e -> buscarProductos());
        btnRefrescar.addActionListener(e -> {
            txtBuscar.setText("");
            cargarDatosTabla(inventarioService.obtenerProductos());
        });

        btnOrdenarPrecio.addActionListener(e -> {
            List<Producto> lista = inventarioService.obtenerProductos();
            Ordenamientos.quickSortProductosPorPrecio(lista, true);
            cargarDatosTabla(lista);
        });

        btnOrdenarNombre.addActionListener(e -> {
            List<Producto> lista = inventarioService.obtenerProductos();
            Ordenamientos.quickSortProductosPorNombre(lista, true);
            cargarDatosTabla(lista);
        });
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.35;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        panel.add(campo, gbc);
    }

    private void cargarDatosTabla(List<Producto> productos) {
        modeloTabla.setRowCount(0);
        for (Producto p : productos) {
            modeloTabla.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    p.getDescripcion(),
                    String.format("%.2f", p.getPrecio()),
                    p.getStock(),
                    p.getStockMinimo(),
                    p.getCategoria() != null ? p.getCategoria().getNombre() : "General",
                    p.getProveedor() != null ? p.getProveedor().getNombre() : "Principal"
            });
        }
    }

    private void cargarFormularioDesdeFila(int fila) {
        txtCodigo.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtCodigo.setEditable(false);
        txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtDescripcion.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtPrecio.setText(modeloTabla.getValueAt(fila, 3).toString().replace(",", "."));
        txtStock.setText(modeloTabla.getValueAt(fila, 4).toString());
        txtStockMinimo.setText(modeloTabla.getValueAt(fila, 5).toString());
        txtCategoria.setText(modeloTabla.getValueAt(fila, 6).toString());
        txtProveedor.setText(modeloTabla.getValueAt(fila, 7).toString());
    }

    private void registrarProducto() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());
            int stockMinimo = Integer.parseInt(txtStockMinimo.getText().trim());
            String catNombre = txtCategoria.getText().trim();
            String provNombre = txtProveedor.getText().trim();

            if (catNombre.isBlank()) catNombre = "General";
            if (provNombre.isBlank()) provNombre = "SmartStore S.A.S.";

            Categoria categoria = new Categoria(catNombre.hashCode() & 0x7fffffff, catNombre, catNombre, true);
            Proveedor proveedor = new Proveedor(provNombre.hashCode() & 0x7fffffff, provNombre, "NIT-" + provNombre.hashCode(), "3000000000", "contacto@" + provNombre.toLowerCase().replaceAll("\\s+", "") + ".com", "Colombia", true);

            Producto producto = new Producto(codigo, nombre, descripcion, precio, stock, stockMinimo, categoria, proveedor);
            inventarioService.registrarProducto(producto);

            cargarDatosTabla(inventarioService.obtenerProductos());
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Producto registrado correctamente en el inventario.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio, stock y stock mínimo deben ser numéricos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarProducto() {
        try {
            String codigo = txtCodigo.getText().trim();
            Producto p = inventarioService.buscarPorCodigo(codigo);
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto existente de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            p.setNombre(txtNombre.getText().trim());
            p.setDescripcion(txtDescripcion.getText().trim());
            p.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
            p.setStock(Integer.parseInt(txtStock.getText().trim()));
            p.setStockMinimo(Integer.parseInt(txtStockMinimo.getText().trim()));

            cargarDatosTabla(inventarioService.obtenerProductos());
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Producto modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio, stock y stock mínimo deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isBlank()) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el producto con código " + codigo + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                inventarioService.eliminarProducto(codigo);
                cargarDatosTabla(inventarioService.obtenerProductos());
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarProductos() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isBlank()) {
            cargarDatosTabla(inventarioService.obtenerProductos());
            return;
        }

        // Búsqueda directa O(1) si coincide con código
        Producto directo = inventarioService.buscarPorCodigo(criterio);
        if (directo != null) {
            cargarDatosTabla(List.of(directo));
            return;
        }

        // Búsqueda por subcadena de nombre
        List<Producto> porNombre = inventarioService.buscarPorNombre(criterio);
        cargarDatosTabla(porNombre);
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtCodigo.setEditable(true);
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtStockMinimo.setText("");
        txtCategoria.setText("");
        txtProveedor.setText("");
        tablaProductos.clearSelection();
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
}
