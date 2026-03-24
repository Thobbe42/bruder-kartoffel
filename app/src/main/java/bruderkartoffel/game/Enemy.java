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

    public void update(GameState gameState, double dt, double playerPosX, double playerPosY) {

        if (!spawning) {

            // avoid blobs
            double sepX = 0;
            double sepY = 0;
            int count = 0;

            for (Enemy other: gameState.getEnemies()) {
                if (other == this) continue;

                double dx = posX - other.getPosX();
                double dy = posY - other.getPosY();

                double distSq = dx * dx + dy * dy;
                double minDist = (this.size/2.0 + other.getSize()/2.0) * 0.7;

                if (distSq < minDist * minDist && distSq > 0) {
                    double dist = Math.sqrt(distSq);

                    // normalize
                    dx /= dist;
                    dy /= dist;

                    // push strength (stronger when closer)
                    double strength = (minDist - dist);

                    sepX += dx * strength;
                    sepY += dy * strength;
                    count++;
                }
            }

            if (count > 0) {
                sepX /= count;
                sepY /= count;

                posX += sepX * 0.1; // tweak factor
                posY += sepY * 0.1;
            }



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

    public boolean isTargetable() {
        return !spawning && !dead;
    }
}
