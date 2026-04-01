package bruderkartoffel.game.weapon;

import bruderkartoffel.game.core.GameState;
import bruderkartoffel.game.entity.Enemy;
import bruderkartoffel.game.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class Weapon {

    private double posX, posY;
    private double angle;

    private final List<Projectile> projectiles;

    private double range;

    private int shotsPerSecond;
    private double projectileDelay;
    private double delayAccumulator;

    private double baseDamage;

    public boolean hasTarget;

    public Weapon(int shotsPerSecond, double baseDamage, double range) {
        this.projectiles = new LinkedList<>();
        this.shotsPerSecond = shotsPerSecond;
        this.baseDamage = baseDamage;
        this.range = range;
        hasTarget = false;
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
            if (hasTarget) {
                spawnProjectile(worldX, worldY);
            }
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

        Enemy target = getTarget(gameState);
        if (target == null) return 0;

        double wx = gameState.getPlayer().getPosX() + posX;
        double wy = gameState.getPlayer().getPosY() + posY;
        double targetDx = target.getPosX() - wx;
        double targetDy = target.getPosY() - wy;

        return Math.atan2(targetDy, targetDx);
    }

    private Enemy getTarget(GameState gameState) {

        Enemy closest = null;
        double minDistSq = range * range;

        double wx = gameState.getPlayer().getPosX() + posX;
        double wy = gameState.getPlayer().getPosY() + posY;

        for (Enemy e : gameState.getEnemies()) {
            if (e.isTargetable()) {
                double dx = e.getPosX() - wx;
                double dy = e.getPosY() - wy;

                double distSq = dx * dx + dy * dy;

                if (distSq <= minDistSq) {
                    minDistSq = distSq;
                    closest = e;
                }
            }
        }

        hasTarget = (closest != null);
        return closest;
    }

    public void removeProjectile(Projectile p) {
        projectiles.remove(p);
    }

    public double getRange() {
        return range;
    }
}
