/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

import java.util.List;

// Potion.java - Potions consumed to increase some attributes of heroes.
public class Potion extends Equipment implements IsUseable{

    private double increaseAttr;
    private List<String> affectedAttr;

    Potion(String name, int price, int reqLevel, double increaseAttr, List<String> affectedAttr) {
        super(name, price, reqLevel);
        this.increaseAttr = increaseAttr;
        this.affectedAttr = affectedAttr;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s: affectedAttr)
            sb.append(s).append("/");
        return  name + "  " + price + "  " + reqLevel + "  " + increaseAttr + "  " + sb.toString().substring(0, sb.toString().length() - 1);
    }

    public double getIncreaseAttr() {return increaseAttr;}

    public List<String> getAffectedAttr() {return affectedAttr;}

    public void use(Hero hero, int index){
        double increase = getIncreaseAttr();
        List<String> attributes = getAffectedAttr();
        for(String attribute: attributes) {
            switch (attribute) {
                case "Health":
                    hero.setHP(hero.getHP()+increase);
                    break;
                case "Mana":
                    hero.setMana(hero.getMana()+ increase);
                    break;
                case "Defense":
                    hero.setDefense(hero.getDefense()+increase);
                    break;
                case "Strength":
                    hero.setStrength(hero.getStrength()+increase);
                    break;
                case "Dexterity":
                    hero.setDexterity(hero.getDexterity()+increase);
                    break;
                case "Agility":
                    hero.setAgility(hero.getAgility()+increase);
                    hero.setProDodge(hero.getAgility()*0.002);
                    break;
            }
        }
        hero.getPotions().remove(index);
        System.out.println(name + "'s statistics have increased.");
    }

}
