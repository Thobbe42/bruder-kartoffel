package bruderkartoffel.gui;

import bruderkartoffel.game.core.GameClock;
import bruderkartoffel.game.core.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Mainframe extends JFrame {


    private GamePanel gamePanel;
    private GameState gameState;
    private GameClock clock;
    private Thread clockThread;

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
        clock = new GameClock(gameState, gamePanel);
        add(gamePanel, BorderLayout.CENTER);

        setExtendedState(MAXIMIZED_BOTH);

        clockThread = new Thread(clock);
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
                new Thread(() -> {
                    clock.stop();
                    try {
                        clockThread.join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    System.exit(0);
                }).start();
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
