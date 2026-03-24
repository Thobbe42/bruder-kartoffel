package bruderkartoffel.gui;

import bruderkartoffel.game.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class GamePanel extends JPanel {

    private final GameState gameState;

    private final JLabel playerHitPoints;

    public GamePanel(GameState gameState) {

        this.gameState = gameState;
        this.playerHitPoints = new JLabel();
        playerHitPoints.setBounds(10, 10, 300, 25);
        playerHitPoints.setBackground(Color.RED);
        playerHitPoints.setFont(new Font("Bold", Font.BOLD, 18));
        playerHitPoints.setHorizontalAlignment(SwingConstants.CENTER);
        int hp = (int)gameState.getPlayer().getHitPoints();
        int maxHp = (int)gameState.getPlayer().getMaxHitPoints();
        playerHitPoints.setText("HP: " + hp + "/" + maxHp);
        playerHitPoints.setOpaque(true);
        playerHitPoints.setBorder(new LineBorder(Color.BLACK, 2));

        setLayout(null);
        add(playerHitPoints);

        KeyHandler kh = new KeyHandler(gameState);
        addKeyListener(kh);
        setFocusable(true);
        requestFocus();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        int hp = (int)gameState.getPlayer().getHitPoints();
        int maxHp = (int)gameState.getPlayer().getMaxHitPoints();
        playerHitPoints.setText("HP: " + hp + "/" + maxHp);

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
        for (Enemy e: gameState.getEnemies()) {
            if (!e.isSpawning()) {
                g2d.setColor(new Color(128, 0, 128));
                size = e.getSize();
                radius = size / 2;
                g2d.fillOval((int) e.getPosX() - radius, (int) e.getPosY() - radius, size, size);
            } else {
                // blink animation
                if (e.isShowSpawn()) {
                    g2d.setColor(Color.RED);
                    size = e.getSize() / 2;
                    radius = size / 2;
                    g2d.fillOval((int) e.getPosX() - radius, (int) e.getPosY() - radius, size, size);
                }
            }
        }

        // draw weapons
        AffineTransform old = g2d.getTransform();

        for (Weapon weapon : p.getWeapons()) {

            double worldX = p.getPosX() + weapon.getPosX();
            double worldY = p.getPosY() + weapon.getPosY();

            // debug
            double angle = weapon.getAngle();
            double length = 40;

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

            // player protective cirle
            int protRadius = gameState.getPlayer().getSize() * 3;
            g2d.setColor(Color.CYAN);
            g2d.drawOval((int)gameState.getPlayer().getPosX() - protRadius, (int)gameState.getPlayer().getPosY() - protRadius, protRadius*2, protRadius*2);

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
