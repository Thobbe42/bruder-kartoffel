package bruderkartoffel.game.entity;

import bruderkartoffel.game.core.GameState;

public class Enemy extends Entity{

    private int experienceValue;

    private double hitPoints;
    private double baseDamage;
    private double speed = 250;


    public Enemy(double hitPoints, double baseDamage, int experienceValue) {
        super();
        this.hitPoints = hitPoints;
        this.baseDamage = baseDamage;
        this.experienceValue = experienceValue;
        this.spawnBehavior = new SpawnBehavior();
        this.size = 50;
    }

    @Override
    public void update(GameState gameState, double dt) {

        if (spawnBehavior != null) {
            boolean done = spawnBehavior.update();

            if (done) {
                spawnBehavior = null;
            }

            return;
        }

        double playerPosX = gameState.getPlayer().getPosX();
        double playerPosY = gameState.getPlayer().getPosY();

        // avoid blobs
        double sepX = 0;
        double sepY = 0;
        int count = 0;

        for (Entity other: gameState.getEntities()) {
            if (other == this) continue;

            double dx = posX - other.getPosX();
            double dy = posY - other.getPosY();

            double distSq = dx * dx + dy * dy;
            double minDist = (size/2.0 + other.getSize()/2.0) * 0.9;

            if (distSq < minDist * minDist && distSq > 0) {
                double dist = Math.sqrt(distSq);

                // normalize
                dx /= dist;
                dy /= dist;

                // push strength
                double strength = (minDist - dist);

                sepX += dx * strength;
                sepY += dy * strength;
                count++;
            }
        }

        if (count > 0) {
            sepX /= count;
            sepY /= count;

            posX += sepX * 0.1;
            posY += sepY * 0.1;
        }



        double dx = playerPosX - posX;
        double dy = playerPosY - posY;

        double length = Math.sqrt(dx * dx + dy * dy);

        if (length != 0) {
            dx /= length;
            dy /= length;
        }

        double targetX = posX + dx * speed * dt;
        double targetY = posY + dy * speed * dt;
        int border = (gameState.getWorldSize().width - gameState.getMapSize().width)/2;

        if (targetX < size/2.0 + border) targetX = size/2.0 + border;
        if (targetX > gameState.getWorldSize().width - border - size/2.0) targetX = gameState.getWorldSize().width - border - size/2.0;
        if (targetY < size/2.0 + border) targetY = size/2.0 + border;
        if (targetY > gameState.getWorldSize().height - border - size/2.0) targetY = gameState.getWorldSize().height - border - size/2.0;

        posX = targetX;
        posY = targetY;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENEMY;
    }

    @Override
    public void dealDamage(double damage) {
        hitPoints -= damage;
        if (hitPoints <= 0) {
            dead = true;
        }
    }

    public double getBaseDamage() {
        return baseDamage;
    }

    @Override
    public boolean isTargetable() {
        return spawnBehavior == null && !dead;
    }

    @Override
    public int getExperienceValue() {
        return experienceValue;
    }
}
