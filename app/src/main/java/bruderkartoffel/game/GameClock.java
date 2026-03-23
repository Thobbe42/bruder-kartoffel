package bruderkartoffel.game;

import bruderkartoffel.gui.GamePanel;

import java.awt.*;

public class GameClock implements Runnable{

    private final int FPS = 60;

    private final GameState gameState;
    private final GamePanel gamePanel;

    public GameClock(GameState gameState, GamePanel gamePanel) {
        this.gameState = gameState;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {

        double drawInterval = 1_000_000_000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long lastDraw = System.nanoTime();


        while(true) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                double dt = (currentTime - lastDraw) / 1_000_000_000.0;
                gameState.update(dt);
                gameState.handleCollisions();
                gamePanel.repaint();
                Toolkit.getDefaultToolkit().sync();
                delta--;
                lastDraw = currentTime;
            }

        }
    }
}
