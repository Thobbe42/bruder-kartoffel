package bruderkartoffel.game.material;

import java.awt.*;

public class MaterialDrop {


    private int value;
    private int size;
    private Point pos;

    public MaterialDrop(int value, int size, Point pos) {
        this.value = value;
        this.size = size;
        this.pos = pos;
    }

    public int getSize() {
        return size;
    }

    public Point getPos() {
        return pos;
    }
}
