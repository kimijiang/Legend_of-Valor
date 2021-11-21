/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// Monster.java - The enemy that heroes will encounter and fight against.
public class Monster {

    private static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_RED = "\u001B[31m";
    private int monsterNumber = 0;

    protected String name;
    protected int level;
    protected double HP;
    protected boolean condition; // true: alive, false: dead

    protected double damage;
    protected double defense;
    protected double dodgeChance;

    Monster(String name, int level, double damage, double defense, double dodgeChance) {
        this.name = ANSI_RED + name + ANSI_RESET;
        this.level = level;
        this.HP = 100.0 * level;
        this.condition = true;
        this.damage = damage;
        this.defense = defense;
        this.dodgeChance = dodgeChance;
//        this.monsterNumber = monsterNumber;
    }

    public void attacked(double damage, Hero hero) {
        if(dodgeChance * 0.01 >= Math.random()) {
            System.out.println(this.name + " dodged " + "the attack from " + hero.getName() + ".");
            return;
        }
        if(damage - defense * 0.02 <= 0)
            damage = 0;
        else
            damage -= defense * 0.02;
        HP -= damage;
        if(HP <= 0) {
            condition = false;
            System.out.println(hero.getName() + " caused " + damage + " damage to " + this.name + " and it died.");
        }
        else {
            System.out.println(hero.getName() + " causes " + damage + " damage to " + this.name);
        }
    }

    public String getName() {return name;}

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getDodgeChance() {
        return dodgeChance;
    }

    public void setDodgeChance(double dodgeChance) {
        this.dodgeChance = dodgeChance;
    }

    public int getLevel() {return level;}

    public int getMonsterNumber() {
        return monsterNumber;
    }

    public boolean isCondition() {
        return condition;
    }

    public String toString() {
        return "Level: " + level + "  " +
                "HP: " + HP + "\n" +
                "Defense: " + defense + "  " +
                "Damage: " + damage;
    }

    public boolean move(Map map){
        int hero = map.getEnemy(monsterNumber);
        if (hero < 0){
            return false;
        } else {
            map.updateMonsterLocation(monsterNumber);
            return true;
        }
    }


}
