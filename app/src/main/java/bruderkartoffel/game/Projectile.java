package bruderkartoffel.game;

public class Projectile {

    private int speed = 800;
    private double posX, posY;

    private double dirX, dirY;

    private int size = 10;

    public Projectile(double dirX, double dirY) {
        this.dirX = dirX;
        this.dirY = dirY;
    }

    public void update(double dt) {
        posX += dt * dirX * speed;
        posY += dt * dirY * speed;
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
}
