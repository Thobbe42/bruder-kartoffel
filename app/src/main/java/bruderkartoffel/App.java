package bruderkartoffel;

import bruderkartoffel.game.GameState;
import bruderkartoffel.gui.Mainframe;

import javax.swing.*;

public class App {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                Mainframe::new
        );
    }
}
