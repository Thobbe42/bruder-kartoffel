package bruderkartoffel.game;

import java.awt.*;

public class Weapon {

    private double posX, posY;
    private double angle;

    public Weapon() {

    }

    public void update(GameState gameState, double relativeX, double relativeY) {
        posX = relativeX;
        posY = relativeY;
        angle = calculateRotationAngle(gameState);
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getAngle() {
        return angle;
    }

    private double calculateRotationAngle(GameState gameState) {
        double minDistance = Double.MAX_VALUE;

        double targetDx = 0;
        double targetDy = 0;
        Player p = gameState.getPlayer();

        for (Enemy e : gameState.getEnemies()) {
            double dx = e.getPosX() - (p.getPosX() + posX);
            double dy = e.getPosY() - (p.getPosY() + posY);

            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < minDistance) {
                minDistance = distance;
                targetDx = dx;
                targetDy = dy;
            }
        }

        return Math.atan2(targetDy, targetDx);
    }
}
