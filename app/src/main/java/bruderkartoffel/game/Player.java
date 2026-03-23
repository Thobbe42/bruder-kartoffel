package bruderkartoffel.game;

public class Player {

    private double posX, posY;

    private int speed = 300;

    public Player() {

    }


    public void update(GameState gameState, double delta) {
        double dx = 0;
        double dy = 0;

        if (gameState.up) dy -= 1;
        if (gameState.down) dy += 1;
        if (gameState.left) dx -= 1;
        if (gameState.right) dx += 1;

        double length = Math.sqrt(dx * dx + dy * dy);

        if (length != 0) {
            dx /= length;
            dy /= length;
        }

        posX += dx * speed * delta;
        posY += dy * speed * delta;
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
}
