package bruderkartoffel.game.entity;

import bruderkartoffel.game.core.GameState;

public class Tree extends Entity {

    private double hitPoints;
    private int experienceValue;


    public Tree() {
        this.spawnBehavior = new SpawnBehavior();
        this.hitPoints = 20;
        this.experienceValue = 3;
        this.dead = false;
        this.size = 60;
    }

    @Override
    public void update(GameState gameState, double dt) {

        if (spawnBehavior != null) {
            boolean done = spawnBehavior.update();

            if (done) {
                spawnBehavior = null;
            }

        }
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.TREE;
    }

    @Override
    public void dealDamage(double damage) {
        hitPoints -= damage;
        if (hitPoints <= 0) {
            dead = true;
        }
    }

    public SpawnBehavior.RenderMode getRenderType() {
        if (spawnBehavior != null) return spawnBehavior.isVisible()
                ? SpawnBehavior.RenderMode.SPAWN_INDICATOR
                : SpawnBehavior.RenderMode.NONE;
        return SpawnBehavior.RenderMode.ENTITY;
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
