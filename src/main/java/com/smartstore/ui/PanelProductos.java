package com.smartstore.ui;

import com.smartstore.model.Categoria;
import com.smartstore.model.Producto;
import com.smartstore.model.Proveedor;
import com.smartstore.service.InventarioService;

import javax.swing.*;
import java.awt.*;

public class PanelProductos extends JPanel {

    private final InventarioService inventarioService;

    public PanelProductos(
            InventarioService inventarioService) {

        this.inventarioService = inventarioService;

        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel(
                "Gestión de Productos",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(
                new FlowLayout()
        );

        JButton btnRegistrar =
                new JButton("Registrar producto");

        JButton btnBuscar =
                new JButton("Buscar producto");

        JButton btnEliminar =
                new JButton("Eliminar producto");

        JButton btnListar =
                new JButton("Listar productos");

        botones.add(btnRegistrar);
        botones.add(btnBuscar);
        botones.add(btnEliminar);
        botones.add(btnListar);

        add(botones, BorderLayout.CENTER);

        btnRegistrar.addActionListener(e ->
                registrarProducto()
        );

        btnBuscar.addActionListener(e ->
                buscarProducto()
        );

        btnEliminar.addActionListener(e ->
                eliminarProducto()
        );

        btnListar.addActionListener(e ->
                listarProductos()
        );
    }

    private void registrarProducto() {

        try {

            String codigo = JOptionPane.showInputDialog(
                    this,
                    "Código:"
            );

            if (codigo == null) return;

            String nombre = JOptionPane.showInputDialog(
                    this,
                    "Nombre:"
            );

            String descripcion =
                    JOptionPane.showInputDialog(
                            this,
                            "Descripción:"
                    );

            String precioTexto =
                    JOptionPane.showInputDialog(
                            this,
                            "Precio:"
                    );

            String stockTexto =
                    JOptionPane.showInputDialog(
                            this,
                            "Stock:"
                    );

            String minimoTexto =
                    JOptionPane.showInputDialog(
                            this,
                            "Stock mínimo:"
                    );

            String categoriaNombre =
                    JOptionPane.showInputDialog(
                            this,
                            "Categoría:"
                    );

            String proveedorNombre =
                    JOptionPane.showInputDialog(
                            this,
                            "Proveedor:"
                    );

            if (nombre == null ||
                    descripcion == null ||
                    precioTexto == null ||
                    stockTexto == null ||
                    minimoTexto == null ||
                    categoriaNombre == null ||
                    proveedorNombre == null) {

                return;
            }

            double precio =
                    Double.parseDouble(precioTexto);

            int stock =
                    Integer.parseInt(stockTexto);

            int stockMinimo =
                    Integer.parseInt(minimoTexto);

            Categoria categoria =
                    new Categoria(
                            1,
                            categoriaNombre,
                            categoriaNombre,
                            true
                    );

            Proveedor proveedor =
                    new Proveedor(
                            1,
                            proveedorNombre,
                            "NIT-" + proveedorNombre,
                            "3000000000",
                            "proveedor@smartstore.com",
                            "Colombia",
                            true
                    );

            Producto producto =
                    new Producto(
                            codigo,
                            nombre,
                            descripcion,
                            precio,
                            stock,
                            stockMinimo,
                            categoria,
                            proveedor
                    );

            inventarioService.registrarProducto(
                    producto
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Producto registrado correctamente."
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Precio, stock y stock mínimo deben ser numéricos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void buscarProducto() {

        String codigo =
                JOptionPane.showInputDialog(
                        this,
                        "Código del producto:"
                );

        if (codigo == null) return;

        Producto producto =
                inventarioService.buscarPorCodigo(codigo);

        if (producto == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Producto no encontrado."
            );

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                producto.toString(),
                "Producto encontrado",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void eliminarProducto() {

        String codigo =
                JOptionPane.showInputDialog(
                        this,
                        "Código del producto:"
                );

        if (codigo == null) return;

        try {

            inventarioService.eliminarProducto(codigo);

            JOptionPane.showMessageDialog(
                    this,
                    "Producto eliminado correctamente."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void listarProductos() {

        StringBuilder texto =
                new StringBuilder();

        for (Producto producto :
                inventarioService.obtenerProductos()) {

            texto.append(producto)
                    .append("\n\n");
        }

        if (texto.length() == 0) {
            texto.append("No hay productos registrados.");
        }

        JTextArea area =
                new JTextArea(texto.toString());

        area.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(area);

        scroll.setPreferredSize(
                new Dimension(700, 400)
        );

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Productos",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}