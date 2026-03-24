package bruderkartoffel.game;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Weapon {

    private double posX, posY;
    private double angle;

    private final List<Projectile> projectiles;

    private int shotsPerSecond;
    private double projectileDelay;
    private double delayAccumulator;

    private double baseDamage;

    public Weapon(int shotsPerSecond, double baseDamage) {
        this.projectiles = new LinkedList<>();
        this.shotsPerSecond = shotsPerSecond;
        this.baseDamage = baseDamage;
        delayAccumulator = 0;
    }

    public void update(GameState gameState, double relativeX, double relativeY, double delta) {
        posX = relativeX;
        posY = relativeY;
        angle = calculateRotationAngle(gameState);

        // check attack delay
        projectileDelay = 1.0/shotsPerSecond;
        delayAccumulator += delta;
        if (delayAccumulator >= projectileDelay) {


            // world coordinates
            Player p = gameState.getPlayer();
            double worldX = p.getPosX() + posX;
            double worldY = p.getPosY() + posY;
            spawnProjectile(worldX, worldY);
            delayAccumulator -= projectileDelay;
        }


        // update projectiles
        for (Projectile proj: projectiles) {
            proj.update(delta);
        }

        // remove dead projectiles
        projectiles.removeIf(Projectile::isDisabled);
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

    public List<Projectile> getProjectiles() {
        return this.projectiles;
    }

    private void spawnProjectile(double worldX, double worldY) {
        double dirX = Math.cos(angle);
        double dirY = Math.sin(angle);
        Projectile proj = new Projectile(dirX, dirY, worldX, worldY, baseDamage);
        projectiles.add(proj);
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

    public void removeProjectile(Projectile p) {
        projectiles.remove(p);
    }
}
