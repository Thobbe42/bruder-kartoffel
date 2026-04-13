package bruderkartoffel.game.material;

import java.awt.*;

public class MaterialDrop {


    private int value;
    private int size;
    private Point pos;
    private boolean active;

    public MaterialDrop(int value, int size, Point pos) {
        this.value = value;
        this.size = size;
        this.pos = pos;
        this.active = true;
    }

    public int getSize() {
        return size;
    }

    public Point getPos() {
        return pos;
    }

    public int getValue() {
        return value;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
