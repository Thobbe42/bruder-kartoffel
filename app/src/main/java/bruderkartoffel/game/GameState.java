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

        Enemy e = new Enemy();
        e.setPosX(300);
        e.setPosY(200);
        enemies.add(e);
    }

    public void update(double delta) {
        player.update(this, delta);
        for (Enemy e: enemies) {
            e.update(delta, player.getPosX(), player.getPosY());
        }
    }


    public Player getPlayer() {
        return this.player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
