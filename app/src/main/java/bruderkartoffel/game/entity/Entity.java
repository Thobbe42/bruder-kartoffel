package bruderkartoffel.game.entity;

import bruderkartoffel.game.core.GameState;

public abstract class Entity {

    public enum EntityType {
        ENEMY,
        TREE
    }

    protected SpawnBehavior spawnBehavior;

    protected double posX, posY;
    protected int size;

    protected boolean dead;

    public Entity () {

    }

    public abstract void update(GameState gameState, double dt);

    public abstract EntityType getEntityType();

    public abstract void dealDamage(double damage);

    public SpawnBehavior.RenderMode getRenderMode() {
        if (spawnBehavior != null) return spawnBehavior.isVisible()
                ? SpawnBehavior.RenderMode.SPAWN_INDICATOR
                : SpawnBehavior.RenderMode.NONE;
        return SpawnBehavior.RenderMode.ENTITY;
    }

    public boolean isTargetable() {
        return false;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosX() {
        return posX;
    }

    public int getSize() {
        return size;
    }

    public boolean isDead() {
        return dead;
    }

    public int getExperienceValue() {
        return 0;
    }
}
