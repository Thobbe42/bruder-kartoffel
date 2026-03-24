package bruderkartoffel.game;

public class Enemy {

    private double posX, posY;
    private int size = 50;
    private int speed = 250;

    private double hitPoints;
    private double baseDamage;

    private boolean dead = false;


    private boolean spawning = true;
    private boolean showSpawn = true;
    private int blinkFrames = 5;
    private int blinkCycles = 10;

    public Enemy(double hitPoints, double baseDamage) {
        this.hitPoints = hitPoints;
        this.baseDamage = baseDamage;
    }

    public void update(double dt, double playerPosX, double playerPosY) {

        if (!spawning) {
            double dx = playerPosX - posX;
            double dy = playerPosY - posY;

            double length = Math.sqrt(dx * dx + dy * dy);

            if (length != 0) {
                dx /= length;
                dy /= length;
            }

            posX += dx * speed * dt;
            posY += dy * speed * dt;
        } else {
            // blink animation update
            if (blinkCycles == 0) {
                spawning = false;
            } else {

                blinkFrames--;
                if (blinkFrames == 0) {
                    showSpawn = !showSpawn;
                    blinkFrames = 5;
                    blinkCycles--;
                }
            }
        }
    }

    public void dealDamage(double damage) {
        hitPoints -= damage;
        if (hitPoints <= 0) {
            dead = true;
        }
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public int getSize() {
        return size;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isSpawning() {
        return spawning;
    }

    public boolean isShowSpawn() {
        return showSpawn;
    }

    public double getBaseDamage() {
        return baseDamage;
    }
}
