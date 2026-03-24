package bruderkartoffel.game;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GameState {

    public boolean up, left, down, right;

    private Player player;
    private List<Enemy> enemies;

    private Dimension worldSize;

    private int playerIFrames = 0;

    public GameState() {
        this.player = new Player(20);
        this.enemies = new LinkedList<>();
    }


    public void setWorldSize(Dimension worldSize) {
        this.worldSize = worldSize;

        player.setPosX(worldSize.getWidth()/2);
        player.setPosY(worldSize.getHeight()/2);


        // spawn 4 enemies for testing
        for (int i = 0; i < 4; i++) {
            spawnEnemy();
        }
    }

    public void update(double delta) {
        player.update(this, delta);

        for (Enemy e: enemies) {
            e.update(delta, player.getPosX(), player.getPosY());
        }
    }


    public void handleCollisions() {

        // projectile-enemy collision
        List<Projectile> projectiles = new LinkedList<>();
        for (Weapon weapon: player.getWeapons()) {
            projectiles.addAll(weapon.getProjectiles());
        }

        for (Projectile proj: projectiles) {
            for (Enemy e: enemies) {
                double dx = proj.getPosX() - e.getPosX();
                double dy = proj.getPosY() - e.getPosY();

                double distSq = dx * dx + dy * dy;
                int radiusSum = proj.getSize()/2 + e.getSize()/2;

                if (distSq <= radiusSum * radiusSum) {
                    proj.setDisabled(true);
                    e.dealDamage(proj.getDamage());
                }
            }
        }

        //player-enemy collision
        if (playerIFrames > 0) {
            playerIFrames--;
        } else {
            playerIFrames = 30; // only allow 2 damage ticks per second
            for (Enemy e: enemies) {
                double dx = e.getPosX() - player.getPosX();
                double dy = e.getPosY() - player.getPosY();

                double distSq = dx * dx + dy * dy;
                int radiusSum = e.getSize()/2 + player.getSize()/2;

                if (distSq <= radiusSum * radiusSum) {
                    player.dealDamage(e.getBaseDamage());
                }
            }
        }

        enemies.removeIf(Enemy::isDead);
    }



    public void spawnEnemy() {
        int protectedRadius = player.getSize() * 3;

        Enemy enemy = new Enemy(10, 1);
        double posX, posY, dist;

        do {
            posX = Math.random() * worldSize.getWidth();
            posY = Math.random() * worldSize.getHeight();

            double dx = player.getPosX() - posX;
            double dy = player.getPosY() - posY;

             dist = Math.sqrt(dx * dx + dy * dy);
        } while (dist <= protectedRadius);

        enemy.setPosX(posX);
        enemy.setPosY(posY);
        enemies.add(enemy);
    }


    public Player getPlayer() {
        return this.player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
