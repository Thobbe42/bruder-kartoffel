package bruderkartoffel.game;

import java.util.List;

public class Wave {

    private int duration;
    private List<WaveEvent> waveEvents;

    public Wave(int duration, List<WaveEvent> waveEvents) {
        this.duration = duration;
        this.waveEvents = waveEvents;
    }

    public int getDuration() {
        return duration;
    }

    public List<WaveEvent> getWaveEvents() {
        return waveEvents;
    }
}
