package bruderkartoffel.game.progression;

import bruderkartoffel.game.entity.Stats;

public class LevelUp {


    private ProgressionHandler progressionHandler;
    private double value;
    private Stats.Stat stat;


    public LevelUp(ProgressionHandler progressionHandler) {
        this.progressionHandler = progressionHandler;
        randomizeStat();
    }

    public void apply() {
        progressionHandler.apply(this);
    }

    private void randomizeStat() {
        int length = Stats.Stat.values().length;
        int randomStat = (int)(Math.random() * length);
        this.stat = Stats.Stat.values()[randomStat];
        setValue();
    }

    private void setValue() {
        switch(stat) {
            case HP -> value = 3;
            case ARMOR -> value = 1;
            case DAMAGE -> value = 5;
            case DODGE -> value = 3;
            case SPEED -> value = 3;
            case ATK_SPEED -> value = 10;
        }
    }


    public Stats.Stat getStat() {
        return this.stat;
    }

    public double getValue() {
        return this.value;
    }
}
