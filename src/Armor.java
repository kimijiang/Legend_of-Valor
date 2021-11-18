/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */


// Armor.java - Store the information of armor and be computed during fight.
public class Armor extends Equipment{

    private double redDamage;

    Armor(String name, int price, int reqLevel, double redDamamage) {
        super(name, price, reqLevel);
        this.redDamage = redDamamage;
    }

    public String toString() {
        return name + "  " + price + "  " + reqLevel + "  " + reqLevel;
    }

    public double getRedDamage() {
        return redDamage;
    }


}
