package bruderkartoffel.game.progression;

import java.util.ArrayList;
import java.util.List;

public class ProgressionHandler {

    private int level;

    private double exp;
    private double requiredExp;

    private int levelUpsInWave;

    private List<LevelUp> currentLevelUps;
    private boolean active;

    public ProgressionHandler() {
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
        }
    }

    public void generateLevelUp(boolean reroll) {
        if (!reroll && levelUpsInWave == 0) return;

        this.currentLevelUps = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            currentLevelUps.add(new LevelUp(0));
        }

        this.active = true;
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

    public void setActive(boolean active) {
        this.active = active;
    }

    private void nextExperienceRequirement() {
        this.requiredExp = (level + 3) * (level + 3);
    }

    public List<LevelUp> getCurrentLevelUps() {
        return currentLevelUps;
    }
}
