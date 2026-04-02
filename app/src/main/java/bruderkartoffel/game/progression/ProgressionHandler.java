package bruderkartoffel.game.progression;

import bruderkartoffel.game.entity.Player;
import bruderkartoffel.game.entity.Stats;

import java.util.ArrayList;
import java.util.List;

public class ProgressionHandler {


    private Player player;

    private int level;

    private double exp;
    private double requiredExp;

    private int levelUpsInWave;

    private List<LevelUp> currentLevelUps;
    private boolean active;


    public ProgressionHandler(Player player) {
        this.player = player;
        this.level = 1;
        this.exp = 0;
        nextExperienceRequirement();
        this.levelUpsInWave = 0;
        this.active = false;
        this.currentLevelUps = new ArrayList<>();
    }


    public void addExperience(int experience) {
        this.exp += experience;
        if (this.exp >= requiredExp) {
            level++;
            this.exp -= requiredExp;
            levelUpsInWave++;
            Stats stats = player.getStats();
            stats.maxHP++;
            stats.hp++;
        }
    }

    public void generateLevelUp(boolean reroll) {
        if (!reroll && levelUpsInWave == 0) return;

        this.currentLevelUps = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            currentLevelUps.add(new LevelUp(this));
        }

        this.active = true;
    }

    public void apply(LevelUp levelUp) {
        Stats stats = player.getStats();
        double value = levelUp.getValue();
        switch(levelUp.getStat()) {
            case HP -> stats.addMaxHP(value);
            case ARMOR -> stats.addArmor(value);
            case DAMAGE -> stats.addDamage(value);
            case DODGE -> stats.addDodge(value);
            case SPEED -> stats.addSpeed(value);
            case ATK_SPEED -> stats.addAtkSpeed(value);
        }
        levelUpsInWave--;
        active = false;
    }

    public double getExp() {
        return exp;
    }

    public double getRequiredExp() {
        return requiredExp;
    }

    public int getLevel() {
        return level;
    }

    public int getLevelUpsInWave() {
        return levelUpsInWave;
    }

    public boolean isActive() {
        return active;
    }

    private void nextExperienceRequirement() {
        this.requiredExp = (level + 3) * (level + 3);
    }

    public List<LevelUp> getCurrentLevelUps() {
        return currentLevelUps;
    }
}
