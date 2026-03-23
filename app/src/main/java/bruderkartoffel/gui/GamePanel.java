package bruderkartoffel.gui;

import bruderkartoffel.game.*;

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

        // draw background
        g2d.setColor(Color.lightGray);
        g2d.fillRect(0,0,getWidth(), getHeight());

        // draw player
        g2d.setColor(Color.WHITE);
        Player p = gameState.getPlayer();
        int size = p.getSize();
        int radius = size/2;
        g2d.fillOval((int)p.getPosX() - radius, (int)p.getPosY() - radius, size, size);

        // draw enemies
        g2d.setColor(new Color(128, 0, 128));
        for (Enemy e: gameState.getEnemies()) {
            size = e.getSize();
            radius = size/2;
            g2d.fillOval((int)e.getPosX() - radius, (int)e.getPosY() -radius, size, size);
        }

        // draw weapons
        AffineTransform old = g2d.getTransform();

        for (Weapon weapon : p.getWeapons()) {

            double worldX = p.getPosX() + weapon.getPosX();
            double worldY = p.getPosY() + weapon.getPosY();

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

            // weapon transformation
            g2d.translate(worldX, worldY);
            g2d.rotate(weapon.getAngle());

            g2d.setColor(Color.BLACK);
            g2d.fillRect(-15, -5, 30, 10);

            g2d.setTransform(old);

            g2d.setColor(Color.GREEN);
            g2d.fillOval((int)worldX - 3, (int)worldY - 3, 6, 6);


            // draw per-weapon projectiles
            for (Projectile proj: weapon.getProjectiles()) {

                size = proj.getSize();
                radius = size/2;
                g2d.setColor(Color.BLACK);
                g2d.fillOval((int)proj.getPosX() - radius, (int)proj.getPosY() - radius, size, size);
            }
        }

        // reset transformation
        g2d.setTransform(old);
    }
}
