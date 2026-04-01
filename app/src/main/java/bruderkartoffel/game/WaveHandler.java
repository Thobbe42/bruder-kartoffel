package bruderkartoffel.game;

import java.util.List;

public class WaveHandler {

    private double timer;
    private int wave;

    private GameState gameState;

    private Wave currentWave;


    private List<Wave> waveConfigs = List.of(
            new Wave(20,
                    List.of(
                            new WaveEvent(0, 3),
                            new WaveEvent(0, 1),
                            new WaveEvent(5, 3),
                            new WaveEvent(5, 1),
                            new WaveEvent(10, 3),
                            new WaveEvent(10, 1),
                            new WaveEvent(15, 3),
                            new WaveEvent(15, 1)
                    )),
            new Wave(25,
                    List.of(
                            new WaveEvent(0, 3),
                            new WaveEvent(0, 2),
                            new WaveEvent(5, 3),
                            new WaveEvent(5, 2),
                            new WaveEvent(10, 3),
                            new WaveEvent(10, 2),
                            new WaveEvent(15, 3),
                            new WaveEvent(15, 2),
                            new WaveEvent(20, 3),
                            new WaveEvent(20, 2)
                    ))
    );

    public WaveHandler(GameState gameState) {
        this.gameState = gameState;

        this.wave = 0;
    }

    public void update(double dt) {
        this.timer -= dt;
        if (timer <= 0) {
            gameState.setPhase(GameState.Phase.SHOP);
            gameState.getEnemies().clear();
        }

        double timeStamp = currentWave.getDuration() - timer;

        for (WaveEvent e: currentWave.getWaveEvents()) {
            if (!e.isTriggered() && timeStamp >= e.getTime()) {
                // trigger event
                gameState.spawnEnemyBatch(e.getCount());
                e.setTriggered();
            }
        }
    }

    public void nextWave() {
        if (gameState.getPhase() == GameState.Phase.WAVE) return;

        if (wave >= waveConfigs.size()) return;

        this.currentWave = waveConfigs.get(wave);
        this.timer = currentWave.getDuration();
        gameState.setPhase(GameState.Phase.WAVE);
        this.wave += 1;
    }

    public double getTimer() {
        return this.timer + 1;
    }

    public int getWave() {
        return wave;
    }
}
