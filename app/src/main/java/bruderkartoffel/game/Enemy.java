package bruderkartoffel.game;

public class Enemy {

    private double posX, posY;
    private int size = 50;
    private int speed = 250;

    private double hitPoints;

    private boolean dead = false;

    public Enemy(double hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void update(double dt, double playerPosX, double playerPosY) {

        double dx = playerPosX - posX;
        double dy = playerPosY - posY;

        double length = Math.sqrt(dx * dx + dy * dy);

        if (length != 0) {
            dx /= length;
            dy /= length;
        }

        posX += dx * speed * dt;
        posY += dy * speed * dt;
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
}
