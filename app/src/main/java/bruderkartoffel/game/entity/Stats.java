package bruderkartoffel.game.entity;

public class Stats {

    public double maxHP;
    public  double hp;

    public double damage;
    public double attackSpeed;

    public double armor;
    public double dodge;

    public double speed;



    public void setDefault() {
        this.maxHP = 10;
        this.hp= maxHP;
        this.damage = 10;
        this.attackSpeed = 0;
        this.armor = 0;
        this.dodge = 0;

        this.speed = 300;
    }
}
