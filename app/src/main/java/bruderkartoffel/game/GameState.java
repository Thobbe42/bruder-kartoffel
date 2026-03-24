package bruderkartoffel.game;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GameState {

    public boolean up, left, down, right;

    private Player player;
    private List<Enemy> enemies;

    private Dimension worldSize;

    public GameState() {
        this.player = new Player();
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

        enemies.removeIf(Enemy::isDead);
        for (Enemy e: enemies) {
            e.update(delta, player.getPosX(), player.getPosY());
        }
    }


    public void handleCollisions() {
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
    }



    public void spawnEnemy() {
        Enemy enemy = new Enemy(10);
        enemy.setPosX(Math.random() * worldSize.getWidth());
        enemy.setPosY(Math.random() * worldSize.getHeight());
        enemies.add(enemy);
    }


    public Player getPlayer() {
        return this.player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
