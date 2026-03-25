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
        enemies.removeIf(Enemy::isDead);
        for (Enemy e: enemies) {
            e.update(this, delta, player.getPosX(), player.getPosY());
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



    public void spawnEnemy() {
        int protectedRadius = player.getSize() * 3;
        double posX, posY, dist;

        do {
            posX = Math.random() * worldSize.getWidth();
            posY = Math.random() * worldSize.getHeight();

            double dx = player.getPosX() - posX;
            double dy = player.getPosY() - posY;

             dist = Math.sqrt(dx * dx + dy * dy);
        } while (dist <= protectedRadius);

        spawnEnemyAt(posX, posY);
    }

    public void spawnEnemyBatch(int amount) {
        double baseX, baseY;
        int protectedRadius = player.getSize() * 3;
        int radius = 50 + (amount * 4);
        double dist;

        do {
            baseX = Math.random() * worldSize.getWidth();
            baseY = Math.random() * worldSize.getHeight();

            double dx = player.getPosX() - baseX;
            double dy = player.getPosY() - baseY;

            dist = Math.sqrt(dx * dx + dy * dy);

        } while(dist <= (protectedRadius + radius));

        for (int i = 0; i < amount; i++) {
            // calculate random point in the batch spawn circle
            double angle = Math.random() * 2 * Math.PI;
            double r = Math.sqrt(Math.random()) * radius;

            double posX= baseX + r * Math.cos(angle);
            double posY = baseY + r * Math.sin(angle);

            spawnEnemyAt(posX, posY);
        }
    }

    private void spawnEnemyAt(double posX, double posY) {
        Enemy enemy = new Enemy(10, 1);

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
