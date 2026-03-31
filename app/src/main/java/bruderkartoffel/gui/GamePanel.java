package bruderkartoffel.gui;

import bruderkartoffel.game.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    private final GameState gameState;

    private final JLabel playerHitPoints;
    private final JLabel playerExperience;

    private boolean debug = false;

    public GamePanel(GameState gameState) {

        this.gameState = gameState;
        this.playerHitPoints = new JLabel();
        playerHitPoints.setBounds(15, 15, 400, 40);
        playerHitPoints.setForeground(Color.WHITE);
        playerHitPoints.setFont(new Font("Bold", Font.BOLD, 25));
        playerHitPoints.setHorizontalAlignment(SwingConstants.CENTER);
        int hp = (int)gameState.getPlayer().getHitPoints();
        int maxHp = (int)gameState.getPlayer().getMaxHitPoints();
        playerHitPoints.setText(hp + "/" + maxHp);
        playerHitPoints.setBorder(new LineBorder(Color.BLACK, 4));

        playerExperience = new JLabel();
        playerExperience.setBounds(15, 70, 400, 40);
        playerExperience.setForeground(Color.WHITE);
        playerExperience.setFont(new Font("Bold", Font.BOLD, 25));
        playerExperience.setHorizontalAlignment(SwingConstants.RIGHT);
        int exp = (int)gameState.getPlayer().getExperience();
        int level = gameState.getPlayer().getLevel();
        playerExperience.setText("Lvl: " + level);
        playerExperience.setBorder(new LineBorder(Color.BLACK, 4));


        setLayout(null);
        add(playerHitPoints);
        add(playerExperience);

        KeyHandler kh = new KeyHandler(gameState, this);
        addKeyListener(kh);
        setFocusable(true);
        requestFocus();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getWidth() <= 0 || getHeight() <= 0) {
            return;
        }

        Graphics2D g2d = (Graphics2D)g;
        AffineTransform old = g2d.getTransform();

        // draw background
        g2d.setColor(Color.lightGray);
        g2d.fillRect(0,0,getWidth(), getHeight());

        // apply camera
        Point camera = gameState.getCamera();
        g2d.translate(-camera.x + getWidth()/2, -camera.y + getHeight()/2);


        // draw border
        g2d.setColor(Color.BLACK);
        int border = 150;
        g2d.drawRect(border,
                border,
                gameState.getMapSize().width,
                gameState.getMapSize().height
        );


        // draw player
        g2d.setColor(Color.WHITE);
        Player p = gameState.getPlayer();
        int size = p.getSize();
        int radius = size/2;
        g2d.fillOval((int)p.getPosX() - radius, (int)p.getPosY() - radius, size, size);
        // player border
        g2d.setColor(Color.BLACK);
        g2d.drawOval((int)p.getPosX() - radius, (int)p.getPosY() - radius, size, size);


        // draw enemies
        List<Enemy> enemiesSnapshot;

        synchronized (gameState.getEnemies()) {
            enemiesSnapshot = new ArrayList<>(gameState.getEnemies());
        }

        for (Enemy e: enemiesSnapshot) {
            if (!e.isSpawning()) {
                g2d.setColor(new Color(128, 0, 128));
                size = e.getSize();
                radius = size / 2;
                g2d.fillOval((int) e.getPosX() - radius, (int) e.getPosY() - radius, size, size);

                // enemy border
                g2d.setColor(Color.BLACK);
                g2d.drawOval((int) e.getPosX() - radius, (int) e.getPosY() - radius, size, size);
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
        for (Weapon weapon : p.getWeapons()) {

            double worldX = p.getPosX() + weapon.getPosX();
            double worldY = p.getPosY() + weapon.getPosY();


            AffineTransform beforeWeapon = g2d.getTransform();

            // weapon transformation
            g2d.translate(worldX, worldY);
            g2d.rotate(weapon.getAngle());

            g2d.setColor(Color.BLACK);
            g2d.fillRect(-15, -5, 30, 10);

            g2d.setTransform(beforeWeapon);

            // debug
            if (debug) {
                double angle = weapon.getAngle();
                double length = 40;

                double dirX = Math.cos(angle) * length;
                double dirY = Math.sin(angle) * length;

                // weapon direction line
                g2d.setColor(Color.RED);
                g2d.drawLine(
                        (int) worldX,
                        (int) worldY,
                        (int) (worldX + dirX),
                        (int) (worldY + dirY)
                );

                // weapon range
                radius = (int) weapon.getRange();
                size = radius * 2;
                g2d.drawOval((int) worldX - radius, (int) worldY - radius, size, size);

                // weapon target lines
                for (Enemy e : enemiesSnapshot) {
                    if (e.isTargetable()) {
                        g2d.setColor(Color.BLUE);
                        g2d.drawLine(
                                (int) worldX,
                                (int) worldY,
                                (int) e.getPosX(),
                                (int) e.getPosY()
                        );
                    }
                }


                // player protective circle
                int protRadius = gameState.getPlayer().getSize() * 3;
                g2d.setColor(Color.CYAN);
                g2d.drawOval((int)gameState.getPlayer().getPosX() - protRadius, (int)gameState.getPlayer().getPosY() - protRadius, protRadius*2, protRadius*2);

                // weapon center point
                g2d.setColor(Color.GREEN);
                g2d.fillOval((int)worldX - 3, (int)worldY - 3, 6, 6);
            }




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



        // draw hp/exp indicators
        double hp = gameState.getPlayer().getHitPoints();
        double maxHp = gameState.getPlayer().getMaxHitPoints();

        int width = (int)(hp/maxHp * 400);
        g2d.setColor(Color.RED);
        g2d.fillRect(15, 15, width, 40);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(15 + width, 15, 400 - width, 40);

        playerHitPoints.setText((int)hp + "/" + (int)maxHp);

        int level = gameState.getPlayer().getLevel();
        double exp = gameState.getPlayer().getExperience();
        int maxExp = gameState.getPlayer().getExperienceForLevel();

        width = (int)(exp/maxExp * 400);
        g2d.setColor(Color.GREEN);
        g2d.fillRect(15, 70, width, 40);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(15 + width, 70, 400 - width, 40);

        playerExperience.setText("Lvl. " + level);
    }

    public void toggleDebug() {
        this.debug = !debug;
    }
}
