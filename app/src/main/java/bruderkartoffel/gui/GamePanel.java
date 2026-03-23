package bruderkartoffel.gui;

import bruderkartoffel.game.GameState;
import bruderkartoffel.game.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final GameState gameState;

    public GamePanel(GameState gameState) {

        this.gameState = gameState;

        KeyHandler kh = new KeyHandler(gameState);
        addKeyListener(kh);
        setFocusable(true);
        requestFocus();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        // draw player
        g2d.setColor(Color.lightGray);
        g2d.fillRect(0,0,getWidth(), getHeight());

        g2d.setColor(Color.WHITE);
        Player p = gameState.getPlayer();
        g2d.fillOval((int)p.getPosX(), (int)p.getPosY(), 70, 70);
    }
}
