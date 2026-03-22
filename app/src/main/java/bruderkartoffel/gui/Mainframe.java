package bruderkartoffel.gui;

import javax.swing.*;
import java.awt.*;

public class Mainframe extends JFrame {

    public Mainframe() {
        // basic metadata
        setTitle("Bruder Kartoffel");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // content
        pack();

        // fullscreen dimensions
        Dimension dim = getToolkit().getScreenSize();
        setSize(dim);

        setVisible(true);
    }
}
