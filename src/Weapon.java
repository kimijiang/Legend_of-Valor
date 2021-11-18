/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// Weapon.java - Store the damage and other information of weapon, used to attack a monster.
public class Weapon extends Equipment implements IsAttackable{

    private double damage;
    private int require;  // 1: 1 hand, 2: 2 hands

    Weapon(String name, int price, int reqLevel, double damage, int require) {
        super(name, price, reqLevel);
        this.damage = damage;
        this.require = require;
    }

    public String toString() {
        return name + "  " + price + "  " + reqLevel + "  " + damage + "  " + require;
    }

    public double getDamage() {
        return damage;
    }

    public double attack(){
        return this.getDamage();
    }

}
