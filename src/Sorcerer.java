/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// Sorcerer.java - A type of heroes, flavored on the dexterity and agility.
public class Sorcerer extends Hero{

    private static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_CYAN = "\u001B[36m";

    Sorcerer(String name, double mana, double strength, double dexterity, double agility, int money, int exp) {
        super(ANSI_CYAN + name + ANSI_RESET, mana, strength, dexterity, agility, money, exp);
    }

    @Override
    public void skillUp() {
        strength *= 1.05;
    }

    @Override
    public void favoredSkillUp() {
        dexterity *= 1.1;
        agility *= 1.1;
        proDodge = agility * 0.002;
    }
}
