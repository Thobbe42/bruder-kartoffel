package bruderkartoffel.game.wave.event;

public class TreeWaveEvent extends WaveEvent{


    private int count;

    public TreeWaveEvent(double time, int count) {
        super(time);

        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
