package bruderkartoffel.game.entity;

import bruderkartoffel.game.weapon.Weapon;
import bruderkartoffel.game.core.GameState;

import java.util.LinkedList;
import java.util.List;

public class Player {

    private double posX, posY;
    private int size = 70;

    private int speed = 300;
    private double hitPoints, maxHitPoints;

    private double experience;
    private int level;
    private int experienceForLevelUp;

    private List<Weapon> weapons;
    private int weaponCount;

    private int iFrames = 0;

    public Player(double hitPoints) {
        this.hitPoints = hitPoints;
        this.maxHitPoints = hitPoints;
        this.experience = 0;
        this.level = 1;
        nextExperienceRequirement();
        this.weapons = new LinkedList<>();

        for (int i = 0; i < 6; i++) {
            weapons.add(new Weapon(4, 3, 500));
            weaponCount++;
        }
    }


    public void update(GameState gameState, double delta) {
        double dx = 0;
        double dy = 0;

        if (gameState.up) dy -= 1;
        if (gameState.down) dy += 1;
        if (gameState.left) dx -= 1;
        if (gameState.right) dx += 1;

        double length = Math.sqrt(dx * dx + dy * dy);

        if (length != 0) {
            dx /= length;
            dy /= length;
        }

        double targetX = posX + dx * speed * delta;
        double targetY = posY + dy * speed * delta;
        int border = (gameState.getWorldSize().width - gameState.getMapSize().width)/2;

        if (targetX < size/2.0 + border) targetX = size/2.0 + border;
        if (targetX > gameState.getWorldSize().width - border - size/2.0) targetX = gameState.getWorldSize().width - border - size/2.0;
        if (targetY < size/2.0 + border) targetY = size/2.0 + border;
        if (targetY > gameState.getWorldSize().height - border - size/2.0) targetY = gameState.getWorldSize().height - border - size/2.0;

        posX = targetX;
        posY = targetY;

        // calculate relative weapon positions
        if (weaponCount > 0) {

            // center of player
            int radius = size/2;

            // default weapon radius
            int weaponRadius = (int)(radius * 2);

            int space = 360 / weaponCount;
            int rotation = space;


            for (Weapon weapon: weapons) {

                double posX = weaponRadius * Math.cos(Math.toRadians(rotation));
                double posY = weaponRadius * Math.sin(Math.toRadians(rotation));

                weapon.update(gameState, posX, posY, delta);

                rotation += space;
            }
        }

        // reduce iFrames;
        if (iFrames > 0) {
            iFrames--;
        }
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public int getSize() {
        return size;
    }

    public double getHitPoints() {
        return hitPoints;
    }

    public double getMaxHitPoints() {
        return maxHitPoints;
    }

    public void takeDamage(double damage) {
        if (iFrames == 0) {
            this.hitPoints -= damage;
            iFrames = calculateIFrames(damage);
        }
    }

    private int calculateIFrames(double damage) {
        // 0.4 * damage% / 15%
        double damagePercentTaken = damage/maxHitPoints * 100;
        double time = 0.4 * (damagePercentTaken/15);
        double invulnerabilityTime = Math.max(0.2, Math.min(0.4, time));

        return (int)(invulnerabilityTime * 60);
    }

    public double getExperience() {
        return this.experience;
    }

    public int getExperienceForLevel() {
        return experienceForLevelUp;
    }

    public void addExperience(int experience) {
        this.experience += experience;
        if (this.experience >= experienceForLevelUp) {
            level++;
            this.experience -= experienceForLevelUp;
            nextExperienceRequirement();
        }
    }

    private void nextExperienceRequirement() {
        this.experienceForLevelUp = (level + 3) * (level + 3);
    }

    public int getLevel() {
        return level;
    }
}
