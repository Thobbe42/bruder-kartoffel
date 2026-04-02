package bruderkartoffel.game.progression;

public class ProgressionHandler {

    private int level;

    private double exp;
    private double requiredExp;

    private int levelUpsInWave;

    public ProgressionHandler() {
        this.level = 1;
        this.exp = 0;
        nextExperienceRequirement();
        levelUpsInWave = 0;
    }


    public void addExperience(int experience) {
        this.exp += experience;
        if (this.exp >= requiredExp) {
            level++;
            this.exp -= requiredExp;
            levelUpsInWave++;
        }
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

    private void nextExperienceRequirement() {
        this.requiredExp = (level + 3) * (level + 3);
    }
}
