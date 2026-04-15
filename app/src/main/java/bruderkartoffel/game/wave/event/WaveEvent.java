package bruderkartoffel.game.wave.event;

public class WaveEvent {

    private double time;

    private boolean triggered;

    public WaveEvent(double time) {
        this.time = time;
        this.triggered = false;
    }

    public double getTime() {
        return time;
    }


    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered() {
        this.triggered = true;
    }
}
