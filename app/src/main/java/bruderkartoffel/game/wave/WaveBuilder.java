package bruderkartoffel.game.wave;

import java.util.ArrayList;
import java.util.List;

class WaveBuilder {

    private double duration;
    private List<WaveEvent> events = new ArrayList<>();

    public WaveBuilder(double duration) {
        this.duration = duration;
    }

    public WaveBuilder spawn(double time, int count) {
        events.add(new EnemyWaveEvent(time, count));
        return this;
    }

    public WaveBuilder spawnGroup(double time, int... counts) {
        for (int c : counts) {
            events.add(new EnemyWaveEvent(time, c));
        }
        return this;
    }

    public WaveBuilder spawnTrees(double time, int count) {
        events.add(new TreeWaveEvent(time, count));
        return this;
    }

    public Wave build() {
        return new Wave(duration, events);
    }
}