package bruderkartoffel.game;

import bruderkartoffel.gui.GamePanel;

import java.awt.*;

public class GameClock implements Runnable{

    private final int FPS = 60;

    private final GameState gameState;
    private final GamePanel gamePanel;

    private volatile boolean running;

    public GameClock(GameState gameState, GamePanel gamePanel) {
        this.gameState = gameState;
        this.gamePanel = gamePanel;
        this.running = true;
    }

    @Override
    public void run() {

        double drawInterval = 1_000_000_000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long lastDraw = System.nanoTime();


        while(running) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                double dt = (currentTime - lastDraw) / 1_000_000_000.0;
                gameState.update(dt, gamePanel.getSize());
                gamePanel.repaint();
                Toolkit.getDefaultToolkit().sync();
                delta--;
                lastDraw = currentTime;
            } else {
            try {
                Thread.sleep(1); // 🔥 critical
            } catch (InterruptedException e) {
                break;
            }
        }

        }
    }

    public void stop() {
        this.running = false;
    }
}
