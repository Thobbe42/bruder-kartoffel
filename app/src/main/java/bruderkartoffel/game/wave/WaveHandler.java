package bruderkartoffel.game.wave;

import bruderkartoffel.game.core.GameState;
import bruderkartoffel.game.weapon.Weapon;

import java.util.List;

public class WaveHandler {

    private double timer;
    private int wave;

    private GameState gameState;

    private Wave currentWave;


    private List<Wave> waveConfigs = List.of(
            new WaveBuilder(10)
                    .spawnGroup(0, 16)
                    .spawnGroup(4, 25)
                    .spawnGroup(11, 3)
                    .spawnGroup(15, 3)
                    .build(),
            new WaveBuilder(25)
                    .spawnGroup(0, 3)
                    .spawnGroup(5, 3)
                    .spawnGroup(10, 4)
                    .spawnGroup(15, 4)
                    .spawnGroup(20, 5)
                    .build(),
            new WaveBuilder(30)
                    .spawnGroup(0, 3, 1)
                    .spawnGroup(5, 4)
                    .spawnGroup(10, 5)
                    .spawnGroup(15, 5, 1)
                    .spawnGroup(20, 6)
                    .spawnGroup(25, 6)
                    .build(),
            new WaveBuilder(35)
                    .spawnGroup(0, 4, 1)
                    .spawnGroup(5, 5)
                    .spawnGroup(10, 6)
                    .spawnGroup(15, 6, 2)
                    .spawnGroup(20, 7)
                    .spawnGroup(25, 7)
                    .spawnGroup(30, 8)
                    .build(),
            new WaveBuilder(40)
                    .spawnGroup(0, 5, 2)
                    .spawnGroup(5, 6)
                    .spawnGroup(10, 7)
                    .spawnGroup(15, 7, 2)
                    .spawnGroup(20, 8)
                    .spawnGroup(25, 8)
                    .spawnGroup(30, 9)
                    .spawnGroup(35, 10)
                    .build()
    );

    public WaveHandler(GameState gameState) {
        this.gameState = gameState;

        this.wave = 0;
    }

    public void update(double dt) {
        this.timer -= dt;
        if (timer <= 0) {
            gameState.setPhase(GameState.Phase.WAVE_END);
            gameState.getEnemies().clear();
            gameState.getMaterialDrops().clear();
            for (Weapon w: gameState.getPlayer().getWeapons()) {
                w.getProjectiles().clear();
            }
        }

        double timeStamp = currentWave.getDuration() - timer;

        for (WaveEvent e: currentWave.getWaveEvents()) {
            if (!e.isTriggered() && timeStamp >= e.getTime()) {
                // trigger event
                if (e instanceof EnemyWaveEvent) {
                    gameState.spawnEnemyBatch(((EnemyWaveEvent) e).getCount());
                } else if (e instanceof TreeWaveEvent) {
                    gameState.spawnTrees(((TreeWaveEvent) e).getCount());
                }
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
