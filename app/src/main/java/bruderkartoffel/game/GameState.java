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
        for (Enemy e: enemies) {
            e.update(delta, player.getPosX(), player.getPosY());
        }
    }

    public void spawnEnemy() {
        double seed = Math.random();
        Enemy enemy = new Enemy();
        enemy.setPosX(seed * worldSize.getWidth());
        enemy.setPosY(seed * worldSize.getHeight());
        enemies.add(enemy);
    }


    public Player getPlayer() {
        return this.player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
