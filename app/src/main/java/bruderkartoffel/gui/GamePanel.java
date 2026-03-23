package bruderkartoffel.gui;

import bruderkartoffel.game.Enemy;
import bruderkartoffel.game.GameState;
import bruderkartoffel.game.Player;
import bruderkartoffel.game.Weapon;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

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

        g2d.setColor(Color.RED);
        for (Enemy e: gameState.getEnemies()) {
            g2d.fillOval((int)e.getPosX(), (int)e.getPosY(), 50, 50);
        }

        AffineTransform old = g2d.getTransform();
        Point playerCenter = p.getCenter();

        for (Weapon weapon : p.getWeapons()) {

            double worldX = playerCenter.x + weapon.getPosX();
            double worldY = playerCenter.y + weapon.getPosY();

        /*
            // debug
            double angle = weapon.getAngle();
            double length = 40; // how long the debug line is

            double dirX = Math.cos(angle) * length;
            double dirY = Math.sin(angle) * length;

            g2d.setColor(Color.RED);
            g2d.drawLine(
                    (int)worldX,
                    (int)worldY,
                    (int)(worldX + dirX),
                    (int)(worldY + dirY)
            );

            for (Enemy e: gameState.getEnemies()) {
                g2d.setColor(Color.BLUE);
                g2d.drawLine(
                        (int)worldX,
                        (int)worldY,
                        (int)e.getPosX(),
                        (int)e.getPosY()
                );
            }
        */

            g2d.translate(worldX, worldY);
            g2d.rotate(weapon.getAngle());

            g2d.setColor(Color.BLACK);
            g2d.fillRect(-15, -5, 30, 10);

            g2d.setTransform(old);

        /*
            g2d.setColor(Color.GREEN);
            g2d.fillOval((int)worldX - 3, (int)worldY - 3, 6, 6);
         */
        }

        g2d.setTransform(old);
    }
}
