package bruderkartoffel.game;

import java.awt.*;

public class GameState {

    public boolean up, left, down, right;

    private Player player;

    private Dimension worldSize;

    public GameState() {
        this.player = new Player();
    }


    public void setWorldSize(Dimension worldSize) {
        this.worldSize = worldSize;

        player.setPosX(worldSize.getWidth()/2);
        player.setPosY(worldSize.getHeight()/2);
    }

    public void update(double delta) {
        player.update(this, delta);
    }


    public Player getPlayer() {
        return this.player;
    }

}
