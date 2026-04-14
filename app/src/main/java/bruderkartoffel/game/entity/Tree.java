package bruderkartoffel.game.entity;

public class Tree {

    private SpawnBehavior spawnBehavior;

    private double hitPoints;
    private int experienceValue;

    private boolean dead;

    private double posX, posY;  

    public Tree() {
        this.spawnBehavior = new SpawnBehavior();
        this.hitPoints = 20;
        this.experienceValue = 3;
        this.dead = false;
    }

    public void update() {
        if (spawnBehavior != null) {
            boolean done = spawnBehavior.update();

            if (done) {
                spawnBehavior = null;
            }

        }
    }

    public void dealDamage(double damage) {
        hitPoints -= damage;
        if (hitPoints <= 0) {
            dead = true;
        }
    }

    public SpawnBehavior.RenderType getRenderType() {
        if (spawnBehavior != null) return spawnBehavior.isVisible()
                ? SpawnBehavior.RenderType.SPAWN_INDICATOR
                : SpawnBehavior.RenderType.NONE;
        return SpawnBehavior.RenderType.ENTITY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isTargetable() {
        return spawnBehavior == null && !dead;
    }

    public int getExperienceValue() {
        return experienceValue;
    }
}
