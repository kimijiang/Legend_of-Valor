/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// Warrior.java - A type of heroes, favored on the strength and agility.
public class Warrior extends Hero{

    private static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";


    Warrior(String name, double mana, double strength, double dexterity, double agility, int money, int exp) {
        super(ANSI_PURPLE + name + ANSI_RESET, mana, strength, dexterity, agility, money, exp);
    }

    @Override
    public void skillUp() {
        dexterity *= 1.05;
    }

    @Override
    public void favoredSkillUp() {
        strength *= 1.1;
        agility *= 1.1;
        proDodge = agility * 0.002;
    }
}
