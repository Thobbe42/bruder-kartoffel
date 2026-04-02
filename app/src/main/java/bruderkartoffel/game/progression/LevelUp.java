package bruderkartoffel.game.progression;

import bruderkartoffel.game.entity.Stats;

public class LevelUp {

    private Stats.Stat stat;
    private int rarity;

    public LevelUp(int rarity) {
        this.rarity = rarity;
        randomizeStat();
    }


    private void randomizeStat() {
        int length = Stats.Stat.values().length;
        int randomStat = (int)(Math.random() + length) - 1;
        this.stat = Stats.Stat.values()[randomStat];
    }
}
