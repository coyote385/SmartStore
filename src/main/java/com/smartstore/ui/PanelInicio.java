package com.smartstore.ui;

import javax.swing.*;
import java.awt.*;

public class PanelInicio extends JPanel {

    public PanelInicio() {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel(
                "Bienvenido a SmartStore",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        JLabel descripcion = new JLabel(
                "Sistema de gestión para productos, clientes y pedidos",
                SwingConstants.CENTER
        );

        descripcion.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        JPanel contenido = new JPanel(
                new GridLayout(2, 1, 10, 10)
        );

        contenido.add(titulo);
        contenido.add(descripcion);

        add(
                contenido,
                BorderLayout.CENTER
        );
    }
}