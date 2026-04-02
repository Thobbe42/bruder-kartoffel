package bruderkartoffel.game.entity;

public class Stats {

    public double maxHP;
    public  double hp;

    public double damage;
    public double attackSpeed;

    public double armor;
    public double dodge;

    public double speed;


    public enum Stat{
      HP, DAMAGE, ATK_SPEED, ARMOR, DODGE, SPEED
    }

    public void setDefault() {
        this.maxHP = 10;
        this.hp= maxHP;
        this.damage = 10;
        this.attackSpeed = 0;
        this.armor = 0;
        this.dodge = 0;

        this.speed = 300;
    }

    public void addMaxHP(double value) {
        this.maxHP += value;
        this.hp += value;
    }

    public void addDamage(double value) {
        this.damage += value;
    }

    public void addAtkSpeed(double value) {
        this.attackSpeed += value;
    }

    public void addArmor(double value) {
        this.armor += value;
    }

    public void addDodge(double value) {
        this.dodge += value;
    }

    public void addSpeed(double value) {
        this.speed += value;
    }
}
