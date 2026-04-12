package bruderkartoffel.gui;

import bruderkartoffel.game.core.GameClock;
import bruderkartoffel.game.core.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Mainframe extends JFrame {


    private GamePanel gamePanel;
    private PauseMenuPanel pausePanel;
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
        pausePanel = new PauseMenuPanel();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(pausePanel, JLayeredPane.PALETTE_LAYER);

        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = layeredPane.getWidth();
                int h = layeredPane.getHeight();

                gamePanel.setBounds(0, 0, w, h);
                pausePanel.setBounds(0, 0, w, h);
            }
        });

        clock = new GameClock(gameState, gamePanel);

        add(layeredPane);
        gamePanel.setVisible(true);

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
        im.put(KeyStroke.getKeyStroke("ENTER"), "enter");

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

        am.put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameState.getPhase() == GameState.Phase.WAVE) {
                    gameState.setPhase(GameState.Phase.PAUSE);
                    pausePanel.setVisible(true);
                } else {
                    gameState.setPhase(GameState.Phase.WAVE);
                    pausePanel.setVisible(false);
                }
            }
        });
    }
}
