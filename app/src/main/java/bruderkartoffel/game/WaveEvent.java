package bruderkartoffel.game;

public class WaveEvent {

    private double time;
    private int count;

    private boolean triggered;

    public WaveEvent(double time, int count) {
        this.time = time;
        this.count = count;
        this.triggered = false;
    }

    public double getTime() {
        return time;
    }

    public int getCount() {
        return count;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered() {
        this.triggered = true;
    }
}
