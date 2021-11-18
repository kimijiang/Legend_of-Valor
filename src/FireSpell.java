/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// FireSpell.java - A subclass of spell, it will reduce the defense of monsters.
public class FireSpell extends Spell{


    FireSpell(String name, int price, int reqLevel, double baseDamage, double mana) {
        super(name, price, reqLevel, baseDamage, mana);
    }

    public String toString() {
        return name + "(Fire)  " + price + "  " + reqLevel + "  " + baseDamage + "  " + mana;
    }

    @Override
    public void deteriorate(Monster monster) {
        monster.setDefense(monster.getDefense() * 0.9);
        System.out.println(monster.getName() + "'s defense was weakened.");
    }

}
