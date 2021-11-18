/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// Spell.java - Heroes can have all kinds of spells, and spells can cause some damage and extra effect to enemy.
public abstract class Spell extends Equipment implements Iscastable{

    protected double baseDamage;
    protected double mana; // amount of magic energy required to get cast

    Spell(String name, int price, int reqLevel, double baseDamage, double mana) {
        super(name, price, reqLevel);
        this.baseDamage = baseDamage;
        this.mana = mana;
    }

    public abstract void deteriorate(Monster monster);

    public double getBaseDamage() {
        return baseDamage;
    }

    public double getMana() {
        return mana;
    }

    public void cast(Hero hero, Monster monster){
        if(hero.getMana()<mana){
            System.out.println("Failed! " + hero.getName() + " does not have enough mana.");
        }
        else{
            monster.attacked(this.getBaseDamage()* (1 + hero.getDexterity() / 10000.0),hero);
            deteriorate(monster);
            hero.setMana(hero.getMana()-mana);
        }
    }
}
