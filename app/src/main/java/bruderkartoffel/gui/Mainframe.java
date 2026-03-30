package bruderkartoffel.gui;

import bruderkartoffel.game.GameClock;
import bruderkartoffel.game.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Mainframe extends JFrame {


    private GamePanel gamePanel;
    private GameState gameState;

    public Mainframe() {
        // basic metadata
        setTitle("Bruder Kartoffel");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // init global keys
        initKeyHandling();

        // initialize GameState, GamePanel, and Clock
        gameState = new GameState();
        gamePanel = new GamePanel(gameState);
        GameClock clock = new GameClock(gameState, gamePanel);
        add(gamePanel, BorderLayout.CENTER);

        setExtendedState(MAXIMIZED_BOTH);

        Thread clockThread = new Thread(clock);
        clockThread.start();
        setVisible(true);
    }


    private void initKeyHandling() {

        JRootPane root = getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        im.put(KeyStroke.getKeyStroke("ESCAPE"), "exit");

        am.put("exit", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public GamePanel getGamePanel() {
        return this.gamePanel;
    }

    public GameState getGameState() {
        return this.gameState;
    }
}
