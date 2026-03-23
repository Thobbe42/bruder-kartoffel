package bruderkartoffel.game;

public class Weapon {

    private double posX, posY;

    public Weapon() {

    }

    public void update(double centerX, double centerY, double relativeX, double relativeY) {
        posX = centerX + relativeX;
        posY = centerY + relativeY;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }
}
