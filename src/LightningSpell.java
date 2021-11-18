/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// LightningSpell.java - A subclass of spell, it will reduce the dodge chance of monsters.
public class LightningSpell extends Spell{

    LightningSpell(String name, int price, int reqLevel, double baseDamage, double mana) {
        super(name, price, reqLevel, baseDamage, mana);
    }

    public String toString() {
        return name + "(Lightning)  " + price + "  " + reqLevel + "  " + baseDamage + "  " + mana;
    }

    @Override
    public void deteriorate(Monster monster) {
        monster.setDodgeChance(monster.getDodgeChance() * 0.9);
        System.out.println(monster.getName() + "'s dodge chance was weakened.");
    }

}
