package com.smartstore.ui;

import com.smartstore.model.Cliente;
import com.smartstore.model.DetallePedido;
import com.smartstore.model.Pedido;
import com.smartstore.model.Producto;
import com.smartstore.service.ClienteService;
import com.smartstore.service.InventarioService;
import com.smartstore.service.PedidoService;

import javax.swing.*;
import java.awt.*;

public class PanelPedidos extends JPanel {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final InventarioService inventarioService;

    public PanelPedidos(
            PedidoService pedidoService,
            ClienteService clienteService,
            InventarioService inventarioService) {

        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.inventarioService = inventarioService;

        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel(
                "Gestión de Pedidos",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(
                new FlowLayout()
        );

        JButton btnCrear =
                new JButton("Crear pedido");

        JButton btnBuscar =
                new JButton("Buscar pedido");

        JButton btnCancelar =
                new JButton("Cancelar pedido");

        JButton btnListar =
                new JButton("Listar pedidos");

        botones.add(btnCrear);
        botones.add(btnBuscar);
        botones.add(btnCancelar);
        botones.add(btnListar);

        add(botones, BorderLayout.CENTER);

        btnCrear.addActionListener(e ->
                crearPedido()
        );

        btnBuscar.addActionListener(e ->
                buscarPedido()
        );

        btnCancelar.addActionListener(e ->
                cancelarPedido()
        );

        btnListar.addActionListener(e ->
                listarPedidos()
        );
    }

    private void crearPedido() {

        try {

            if (clienteService.cantidadClientes() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Primero debe registrar un cliente."
                );

                return;
            }

            if (inventarioService.cantidadProductos() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Primero debe registrar un producto."
                );

                return;
            }

            String idTexto =
                    JOptionPane.showInputDialog(
                            this,
                            "ID del pedido:"
                    );

            if (idTexto == null) return;

            int id =
                    Integer.parseInt(idTexto);

            String clienteTexto =
                    JOptionPane.showInputDialog(
                            this,
                            "ID del cliente:"
                    );

            if (clienteTexto == null) return;

            int clienteId =
                    Integer.parseInt(clienteTexto);

            Cliente cliente =
                    clienteService.buscarPorId(
                            clienteId
                    );

            if (cliente == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Cliente no encontrado."
                );

                return;
            }

            Pedido pedido =
                    new Pedido(
                            id,
                            cliente,
                            "Pendiente"
                    );

            boolean agregarMas = true;

            while (agregarMas) {

                String codigo =
                        JOptionPane.showInputDialog(
                                this,
                                "Código del producto:"
                        );

                if (codigo == null) return;

                Producto producto =
                        inventarioService.buscarPorCodigo(
                                codigo
                        );

                if (producto == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Producto no encontrado."
                    );

                    continue;
                }

                String cantidadTexto =
                        JOptionPane.showInputDialog(
                                this,
                                "Cantidad:"
                        );

                if (cantidadTexto == null) return;

                int cantidad =
                        Integer.parseInt(cantidadTexto);

                if (cantidad <= 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "La cantidad debe ser mayor que cero."
                    );

                    continue;
                }

                DetallePedido detalle =
                        new DetallePedido(
                                producto,
                                cantidad,
                                producto.getPrecio()
                        );

                pedidoService.agregarProducto(
                        pedido,
                        detalle
                );

                int opcion =
                        JOptionPane.showConfirmDialog(
                                this,
                                "¿Desea agregar otro producto?",
                                "Productos",
                                JOptionPane.YES_NO_OPTION
                        );

                agregarMas =
                        opcion == JOptionPane.YES_OPTION;
            }

            if (pedido.cantidadProductos() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El pedido debe tener al menos un producto."
                );

                return;
            }

            pedidoService.crearPedido(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido creado correctamente.\n"
                            + "Total: $"
                            + pedidoService.calcularTotal(pedido)
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Los valores numéricos no son válidos.",
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

    private void buscarPedido() {

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

            JOptionPane.showMessageDialog(
                    this,
                    pedido.toString()
                            + "\nTotal: $"
                            + pedido.calcularTotal(),
                    "Pedido",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID inválido."
            );
        }
    }

    private void cancelarPedido() {

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

            pedidoService.cancelarPedido(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido cancelado correctamente."
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID inválido."
            );
        }
    }

    private void listarPedidos() {

        StringBuilder texto =
                new StringBuilder();

        for (Pedido pedido :
                pedidoService.listarPedidos()) {

            texto.append(pedido)
                    .append("\n\n");
        }

        if (texto.length() == 0) {
            texto.append("No hay pedidos registrados.");
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
                "Pedidos",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}