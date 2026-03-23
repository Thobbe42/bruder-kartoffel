package bruderkartoffel.game;

public class Projectile {

    private int speed = 800;
    private int range = 500;
    private boolean disabled = false;
    private double posX, posY, startX, startY;

    private double dirX, dirY;

    private int size = 10;

    public Projectile(double dirX, double dirY, double posX, double posY) {
        this.dirX = dirX;
        this.dirY = dirY;

        this.posX = posX;
        this.posY = posY;
        this.startX = posX;
        this.startY = posY;
    }

    public void update(double dt) {
        posX += dt * dirX * speed;
        posY += dt * dirY * speed;

        double dx = posX - startX;
        double dy = posY - startY;

        double length = Math.sqrt(dx * dx + dy * dy);
        if (length >= range) {
            disabled = true;
        }
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

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
