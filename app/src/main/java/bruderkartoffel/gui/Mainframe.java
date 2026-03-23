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

        // add content
        Dimension size = new Dimension(1920, 1080);
        gameState = new GameState();
        gamePanel = new GamePanel(gameState);
        add(gamePanel, BorderLayout.CENTER);

        gamePanel.addComponentListener(new ComponentAdapter() {
            private boolean started = false;

            @Override
            public void componentResized(ComponentEvent e) {
                if (!started) {
                    started = true;

                    gameState.setWorldSize(gamePanel.getSize());

                    Thread gameThread = new Thread(new GameClock(gameState, gamePanel));
                    gameThread.start();
                }
            }
        });

        setExtendedState(MAXIMIZED_BOTH);

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
