package bruderkartoffel.game.wave.event;

public class EnemyWaveEvent extends WaveEvent{

    private int count;

    public EnemyWaveEvent(double time, int count) {
        super(time);

        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
