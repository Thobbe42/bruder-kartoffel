package bruderkartoffel.game.wave;

import bruderkartoffel.game.wave.event.WaveEvent;

import java.util.List;

public class Wave {

    private double duration;
    private List<WaveEvent> waveEvents;

    public Wave(double duration, List<WaveEvent> waveEvents) {
        this.duration = duration;
        this.waveEvents = waveEvents;
    }

    public double getDuration() {
        return duration;
    }

    public List<WaveEvent> getWaveEvents() {
        return waveEvents;
    }
}
