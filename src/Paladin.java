/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// Paladin.java - A type of heroes, favored on the strength and dexterity.
public class Paladin extends Hero{

    public static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\033[0m";


    Paladin(String name, double mana, double strength, double dexterity, double agility, int money, int exp) {
        super(ANSI_YELLOW + name + ANSI_RESET, mana, strength, dexterity, agility, money, exp);
    }

    @Override
    public void skillUp() {
        agility *= 1.05;
        proDodge = agility * 0.002;
    }

    @Override
    public void favoredSkillUp() {
        strength *= 1.1;
        dexterity*= 1.1;
    }

}
