package bruderkartoffel.game.entity;

public class SpawnBehavior {

    public enum RenderMode {
        NONE,
        SPAWN_INDICATOR,
        ENTITY
    }

    private int blinkFrames = 5;
    private int blinkCycles = 6;
    private boolean showSpawn = true;

    public boolean update() {
        if (blinkCycles == 0) {
            return true; // finished
        }

        blinkFrames--;
        if (blinkFrames == 0) {
            showSpawn = !showSpawn;
            blinkFrames = 5;
            blinkCycles--;
        }

        return false;
    }

    public boolean isVisible() {
        return showSpawn;
    }
}
