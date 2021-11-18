/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// IceSpell.java - A subclass of spell, it will reduce the damage of monsters.
public class IceSpell extends Spell{



    IceSpell(String name, int price, int reqLevel, double baseDamage, double mana) {
        super(name, price, reqLevel, baseDamage, mana);
    }


    public String toString() {
        return name + "(Ice)  " + price + "  " + reqLevel + "  " + baseDamage + "  " + mana;
    }

    @Override
    public void deteriorate(Monster monster) {
        monster.setDamage(monster.getDamage() * 0.9);
        System.out.println(monster.getName() + "'s base damage was weakened.");
    }
}
