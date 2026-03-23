package bruderkartoffel.game;

public class Weapon {

    private double posX, posY;

    public Weapon() {

    }

    public void update(Player p, double relativeX, double relativeY) {
        posX = p.getPosX() + relativeX;
        posY = p.getPosY() + relativeY;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }
}
