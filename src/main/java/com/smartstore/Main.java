package com.smartstore;

import javax.swing.SwingUtilities;
import com.smartstore.ui.MainFrame;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new MainFrame().setVisible(true);

        });

    }

}