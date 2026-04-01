package bruderkartoffel.game.core;

import bruderkartoffel.game.entity.Enemy;
import bruderkartoffel.game.entity.Player;
import bruderkartoffel.game.weapon.Projectile;
import bruderkartoffel.game.weapon.Weapon;
import bruderkartoffel.game.wave.WaveHandler;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GameState {


    public enum Phase{
        INIT, WAVE, SHOP
    }

    public boolean up, left, down, right;

    private Player player;
    private List<Enemy> enemies;

    private Dimension worldSize;
    private Dimension mapSize;

    private Point camera;

    private Phase phase;

    private WaveHandler waveHandler;

    /**
     * Creates a new GameState with default values.
     * Default values are 20 player HP and a 2500x2500 World.
     */
    public GameState() {

        this.phase = Phase.INIT;
        this.waveHandler = new WaveHandler(this);

        this.player = new Player(20);
        this.enemies = new LinkedList<>();

        // default world setup
        this.worldSize = new Dimension(2000, 2000);
        this.mapSize = new Dimension(worldSize.width - 300, worldSize.height - 300);
        this.camera = new Point(worldSize.width/2, worldSize.height/2);

        this.player.setPosX(worldSize.getWidth()/2);
        this.player.setPosY(worldSize.getHeight()/2);
    }

    /**
     * Set the world size to a dedicated dimension different from the default.
     * Can be used to override map size for specific cases.
     *
     * @param worldSize The Dimension of the world for this GameState.
     */
    public void setWorldSize(Dimension worldSize) {
        this.worldSize = worldSize;
        this.mapSize = new Dimension(worldSize.width - 300, worldSize.height - 300);
        this.camera = new Point(worldSize.width/2, worldSize.height/2);

        player.setPosX(worldSize.getWidth()/2);
        player.setPosY(worldSize.getHeight()/2);
    }

    /**
     * Updates the gameState for the next frame based on inputs and movemnts/collisions.
     *
     * @param dt The exact time passed since the last frame to account for fluctuations
     *              in framerate.
     * @param screenSize The Dimensions of the GamePanel Object, to correctly update the
     *                   camera position.
     */
    public void update(double dt, Dimension screenSize) {
        switch (phase) {
            case INIT -> updateInit(screenSize);
            case WAVE -> updateWave(dt, screenSize);
        }
    }

    private void updateInit(Dimension screenSize) {
        if (screenSize.width != 0 && screenSize.height != 0) {
            updateCamera(screenSize);
            waveHandler.nextWave();
        }
    }

    private void updateWave(double dt, Dimension screenSize) {
        player.update(this, dt);
        enemies.removeIf(Enemy::isDead);
        for (Enemy e: enemies) {
            e.update(this, dt, player.getPosX(), player.getPosY());
        }
        updateCamera(screenSize);
        handleCollisions();
        waveHandler.update(dt);
    }

    /**
     * Updates the camera by moving it to the player position and clamping it to the
     * border of the map.
     *
     * @param screenSize The Dimension of the GamePanel object for correct clamping.
     */
    public void updateCamera(Dimension screenSize) {

        camera.x = (int)player.getPosX();
        camera.y = (int)player.getPosY();

        if (screenSize.width <= 0 || screenSize.height <= 0) {
            return;
        }

        double halfW = screenSize.getWidth() / 2.0;
        double halfH = screenSize.getHeight() / 2.0;

        camera.x = (int)Math.max(halfW, Math.min(camera.x, worldSize.width - halfW));
        camera.y = (int)Math.max(halfH, Math.min(camera.y, worldSize.height - halfH));
    }

    /**
     * Handles all the possible collisions occurring in one frame.
     * Checks for collision of projectiles with hittable targets, handles
     * disabling of projectiles, and checks collisions of enemies with the
     * player.
     */
    public void handleCollisions() {

        // projectile-enemy collision
        List<Projectile> projectiles = new LinkedList<>();
        for (Weapon weapon: player.getWeapons()) {
            projectiles.addAll(weapon.getProjectiles());
        }

        for (Projectile proj: projectiles) {
            if (proj.isDisabled()) continue; // avoid infinite piercing within a single frame

            for (Enemy e: enemies) {
                if (e.isDead()) continue; // no collision with dead or spawning targets

                double dx = proj.getPosX() - e.getPosX();
                double dy = proj.getPosY() - e.getPosY();

                double distSq = dx * dx + dy * dy;
                int radiusSum = proj.getSize()/2 + e.getSize()/2;

                if (distSq <= radiusSum * radiusSum) {
                    proj.setDisabled(true);
                    e.dealDamage(proj.getDamage());
                    if (e.isDead()) {
                        player.addExperience(e.getExperienceValue());
                    }
                }
            }
        }

        //player-enemy collision
        for (Enemy e: enemies) {
            if (e.isDead()) continue; // avoid hits by enemies that died in the same frame

            double dx = e.getPosX() - player.getPosX();
            double dy = e.getPosY() - player.getPosY();

            double distSq = dx * dx + dy * dy;
            int radiusSum = e.getSize()/2 + player.getSize()/2;

            if (distSq <= radiusSum * radiusSum) {
                player.takeDamage(e.getBaseDamage());
            }
        }
    }


    /**
     * Spawns a single enemy at a random location outside the players protective radius.
     */
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

    /**
     * Spawns a specified amount of enemies in a cluster.
     * Enemies spawn in a close group, outside the players protective radius.
     *
     * @param amount The number of enemies to be spawned in the batch.
     */
    public void spawnEnemyBatch(int amount) {
        double baseX, baseY;
        int protectedRadius = player.getSize() * 3;
        int radius = 50 + (amount * 4);
        double dist;

        int border = (worldSize.width - mapSize.width)/2;
        do {
            baseX = Math.random() * (mapSize.getWidth() - 2 * radius) + border + radius;
            baseY = Math.random() * (mapSize.getHeight() - 2 * radius) + border + radius;

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
        Enemy enemy = new Enemy(10, 1, 1);

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

    public Dimension getWorldSize() {
        return this.worldSize;
    }

    public Dimension getMapSize() {
        return this.mapSize;
    }

    public Point getCamera() {
        return this.camera;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return this.phase;
    }

    public WaveHandler getWaveHandler() {
        return this.waveHandler;
    }
}
