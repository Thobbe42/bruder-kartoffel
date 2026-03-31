package bruderkartoffel.game;

public class WaveHandler {

    private double duration;
    private double timer;
    private int wave;

    private GameState gameState;

    public WaveHandler(GameState gameState) {
        this.gameState = gameState;

        this.wave = 0;
        this.duration = 20;
        this.timer = duration;
    }

    public void update(double dt) {
        this.timer -= dt;
        if (timer <= 0) {
            gameState.setPhase(GameState.Phase.SHOP);
        }
    }

    public void nextWave() {
        this.wave += 1;
        this.duration = (duration < 60) ? (duration + 5) : 60;
        this.timer = duration;
        gameState.setPhase(GameState.Phase.WAVE);
    }

    public double getTimer() {
        return this.timer;
    }

    public int getWave() {
        return wave;
    }
}
