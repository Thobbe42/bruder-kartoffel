package bruderkartoffel.game.material;

import bruderkartoffel.game.core.GameState;
import bruderkartoffel.game.entity.Player;

import java.awt.*;

public class MaterialDrop {


    private int value;
    private int size;
    private Point pos;
    private double speed;
    private boolean active;

    public MaterialDrop(int value, int size, Point pos, double speed) {
        this.value = value;
        this.size = size;
        this.pos = pos;
        this.active = true;
        this.speed = speed;
    }


    public void update(GameState gameState, double dt) {

        Player p = gameState.getPlayer();
        Point playerPos = new Point((int)p.getPosX(), (int)p.getPosY());
        int maxDist = p.getCollectionRadius();

        double dx = playerPos.x - pos.x;
        double dy = playerPos.y - pos.y;

        double distSq = dx * dx + dy * dy;

        if (distSq <= maxDist * maxDist) {

            double length = Math.sqrt(distSq);

            if (length != 0) {
                dx /= length;
                dy /= length;
            }

            double targetX = pos.x + dx * speed * dt;
            double targetY = pos.y + dy * speed * dt;
            int border = (gameState.getWorldSize().width - gameState.getMapSize().width)/2;

            if (targetX < size/2.0 + border) targetX = size/2.0 + border;
            if (targetX > gameState.getWorldSize().width - border - size/2.0) targetX = gameState.getWorldSize().width - border - size/2.0;
            if (targetY < size/2.0 + border) targetY = size/2.0 + border;
            if (targetY > gameState.getWorldSize().height - border - size/2.0) targetY = gameState.getWorldSize().height - border - size/2.0;

            pos.x = (int)targetX;
            pos.y = (int)targetY;
        }
    }

    public int getSize() {
        return size;
    }

    public Point getPos() {
        return pos;
    }

    public int getValue() {
        return value;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
