package bruderkartoffel.gui;

import bruderkartoffel.game.core.GameClock;
import bruderkartoffel.game.core.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Mainframe extends JFrame {


    private GamePanel gamePanel;
    private PauseMenuPanel pausePanel;
    private LevelUpPanel levelUpPanel;
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
        setUndecorated(true);

        // init global keys
        initKeyHandling();

        // initialize GameState, GamePanel, and Clock
        gameState = new GameState(this::updateUiForPhase);
        gamePanel = new GamePanel(gameState);
        pausePanel = new PauseMenuPanel(this::exitGame, gameState::setPhase);
        levelUpPanel = new LevelUpPanel(gameState.getProgressionHandler());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(pausePanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(levelUpPanel, JLayeredPane.MODAL_LAYER);

        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = layeredPane.getWidth();
                int h = layeredPane.getHeight();

                gamePanel.setBounds(0, 0, w, h);
                pausePanel.setBounds(0, 0, w, h);
                levelUpPanel.setBounds(0, 0, w, h);

                if (!levelUpPanel.isInited())
                    levelUpPanel.init(gameState.getPlayer().getStats());
            }
        });

        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                super.windowLostFocus(e);
                pausePanel.setPausedPhase(gameState.getPhase());
                gameState.setPhase(GameState.Phase.PAUSE);
                pausePanel.setVisible(true);
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


    private void exitGame() {
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


    private void updateUiForPhase(GameState.Phase phase) {
        switch (phase) {
            case WAVE -> {
                pausePanel.setVisible(false);
                levelUpPanel.setVisible(false);
            }

            case PAUSE -> {
                pausePanel.setVisible(true);
            }

            case WAVE_END -> {
                pausePanel.setVisible(false);
                levelUpPanel.setVisible(true);
            }
        }
    }

    private void initKeyHandling() {

        JRootPane root = getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        im.put(KeyStroke.getKeyStroke("ESCAPE"), "esc");

        am.put("esc", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameState.getPhase() != GameState.Phase.PAUSE) {
                    pausePanel.setPausedPhase(gameState.getPhase());
                    gameState.setPhase(GameState.Phase.PAUSE);
                    pausePanel.setVisible(true);
                } else {
                    gameState.setPhase(pausePanel.getPausedPhase());
                    pausePanel.setVisible(false);
                }
            }
        });
    }
}
