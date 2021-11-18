/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Fight.java - Control the process of a fight between heroes and monsters.
public class Fight {

    private static final int EXP_WIN = 2; // Exp get in each fight if heroes win

    private boolean winner; // true: Heroes  false: monsters

    private List<Monster> monsters;
    private List<Hero> heroes;

    Fight(List<Hero> heroes) {
        this.heroes = heroes;
        this.monsters = new ArrayList<Monster>();
    }

    // fight starts
    public void fight() {
        initiateMonsters();
        System.out.println("Fight!");
        while(!isOver()) {
            round();
        }
        System.out.println("Fight over.");
        if(winner == true) {
            // heroes win, get the reward
            System.out.println("Heroes win!");
            int money = monsters.get(0).getLevel() * 100;
            for(int i = 0; i<heroes.size(); i++) {
                if(heroes.get(i).isCondition()) {
                    heroes.get(i).setMoney(heroes.get(i).getMoney() + money);
                    heroes.get(i).recExp(EXP_WIN);
                    if(heroes.get(i).ifLevelUp())
                        System.out.println(heroes.get(i).getName() + " level up. " + heroes.get(i).getLevel() + " level now.");
                }
                else
                    heroes.get(i).reborn();
            }
        }
        else {
            // heroes lose
            System.out.println("You lose!");
            for(int i = 0; i<heroes.size(); i++) {
                heroes.get(i).reborn();
                heroes.get(i).setMoney(heroes.get(i).getMoney() / 2);
                System.out.println(heroes.get(i).getName() + " lost " + heroes.get(i).getMoney() / 2 + " money.");
            }
        }

    }

    // initiate monsters in the fight
    public void initiateMonsters() {
        int level = getHighestLevel();
        List<Monster> monsterList = new ArrayList<Monster>();
        for(Monster monster: (List<Monster>) Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Dragons.txt"))
            if(monster.getLevel() == level)
                monsterList.add(monster);
        for(Monster monster: (List<Monster>) Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Exoskeletons.txt"))
            if(monster.getLevel() == level)
                monsterList.add(monster);
        for(Monster monster: (List<Monster>) Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Spirits.txt"))
            if(monster.getLevel() == level)
                monsterList.add(monster);
        if(monsterList.size() < heroes.size()) {
            System.out.println("Oops! No enough corresponding monsters in text files.");
            System.exit(0);
        }
        Random rd = new Random();
        for(int i = 0; i<heroes.size(); i++)
            monsters.add(monsterList.get(rd.nextInt(monsterList.size())));
    }


    public void round() {
        displayBattleField();
        for(int i = 0; i<heroes.size(); i++) {
            if(heroes.get(i).isCondition()) {
                System.out.println(heroes.get(i).getName() + "'s turn.");
                chooseOperation(heroes.get(i));
            }
            if(isOver())
                return;
        }
        for(Monster monster: monsters) {
            if(monster.isCondition()) {
                try {
                    System.out.println(monster.getName() + " is taking action...");
                    Thread.sleep(1000);
                    heroes.get(getMonsterTarget(monster)).attacked(monster.getDamage(), monster);
                }catch (Exception e) {
                    System.out.println("Error: Thread break.");
                }
            }
            if(isOver())
                return;
        }
        for(int i = 0;i <heroes.size(); i++)
            if(heroes.get(i).isCondition())
                heroes.get(i).regainHPAndMana();
    }


    // judge if the fight is over
    public boolean isOver() {
        boolean monsterAllDied = true;
        boolean heroesAllDied = true;
        for(Monster monster: monsters)
            if(monster.isCondition()) {
                monsterAllDied = false;
                break;
            }
        for(Hero hero: heroes)
            if(hero.isCondition()) {
                heroesAllDied = false;
                break;
            }
        if(monsterAllDied == true)
            winner = true;
        if(heroesAllDied == true)
            winner = false;
        return monsterAllDied || heroesAllDied;
    }


    public void chooseOperation(Hero hero) {
        System.out.println("0. Regular attack");
        System.out.println("1. Cast a spell");
        System.out.println("2. Use a potion");
        System.out.println("3. Change weapon/armor");
        System.out.println("Choose the operation for " + hero.getName());
        Scanner input = new Scanner(System.in);
        String regex = "[0123Ii]";
        String choice = null;
        while((choice = input.next()) != null) {
            if(!choice.matches(regex)) {
                System.out.println("Wrong input! Please input again.");
                continue;
            }
            else {
                // Regular attack
                if(choice.equals("0")) {
                    monsters.get(getHeroTarget(hero)).attacked(hero.causeDamage(), hero);
                    break;
                }
                // Cast a spell
                else if(choice.equals("1")) {
                    if(hero.getSpells().size() == 0) {
                        System.out.println(hero.getName() + " does not have any spell. Please input again.");
                        continue;
                    }
                    hero.showSpells();
                    System.out.println(hero.getName() + " has " + hero.getMana() + " mana left.");
                    System.out.println("Input the number to cast the spell:");
                    String num_spell = null;
                    String regex2 = "[0~" + (hero.getSpells().size()-1) + "]";
                    while((num_spell = input.next()) != null) {
                        if(num_spell.matches(regex2)) {
                            hero.castSpellTo(Integer.parseInt(num_spell), monsters.get(getHeroTarget(hero)));
                            break;
                        }
                        else
                            System.out.println("Wrong input! Please input again.");
                    }
                    break;
                }
                // Use a potion
                else if(choice.equals("2")) {
                    if(hero.getPotions().size() == 0) {
                        System.out.println(hero.getName() + " does not have any potion. Please input again.");
                        continue;
                    }
                    hero.showPotions();
                    System.out.println("Input the number to use a potion:");
                    String num_potion = null;
                    String regex3 = "[0~" + (hero.getPotions().size()-1) + "]";
                    while((num_potion = input.next()) != null) {
                        if(num_potion.matches(regex3)) {
                            hero.usePotion(Integer.parseInt(num_potion));
                            break;
                        }
                        else
                            System.out.println("Wrong input! Please input again.");
                    }
                    break;
                }
                // Change weapon or armor
                else if(choice.equals("3")) {
                    List<Equipment> inventory = hero.getInventory();
                    List<Equipment> weaponsAndArmors = new ArrayList<Equipment>();
                    for(Equipment item: inventory) {
                        if(item instanceof Weapon || item instanceof Armor)
                            weaponsAndArmors.add(item);
                    }
                    if(weaponsAndArmors.size() == 0) {
                        System.out.println("Inventory: no weapons and armors. Please input again.");
                        continue;
                    }
                    Weapon weapon = hero.getWeapon();
                    Armor armor = hero.getArmor();
                    if(weapon != null)
                        System.out.println("Equipped weapon: " + weapon.getName());
                    else
                        System.out.println("Equipped weapon: None");
                    if(armor != null)
                        System.out.println("Equipped armor: " + armor.getName());
                    else
                        System.out.println("Equipped armor: None");
                    System.out.print("Inventory: ");
                    for(Equipment e: weaponsAndArmors)
                        System.out.print(e.getName() + "  ");
                    System.out.println("");
                    System.out.println("Input the name of weapon or armor you'd like to be equipped with:");
                    String nameOfEquip = null;
                    while((nameOfEquip = input.next()) != null) {
                        boolean ifMadeIt = false;
                        for(Equipment e: weaponsAndArmors) {
                            if (e.getName().equals(nameOfEquip)) {
                                if(e instanceof Weapon)
                                    hero.changeWeapon((Weapon) e);
                                else if(e instanceof Armor)
                                    hero.changeArmor((Armor) e);
                                ifMadeIt = true;
                                break;
                            }
                        }
                        if(!ifMadeIt)
                            System.out.println("No such weapon or armor. Please input again.");
                        else
                            break;
                    }
                    break;
                }
                // show information
                else if(choice.equals("I") || choice.equals("i")) {
                    displayBattleFieldWithInfo();
                    System.out.println("0. Regular attack");
                    System.out.println("1. Cast a spell");
                    System.out.println("2. Use a potion");
                    System.out.println("3. Change weapon/armor");
                    System.out.println("Choose the operation for " + hero.getName());
                    continue;
                }
            }
        }
    }


    public void displayBattleField() {
        System.out.println("============================Battle Field============================");
        for(int i = 0; i<heroes.size(); i++)
            drawRow(heroes.get(i).getName(), monsters.get(i).getName());
        System.out.println("=============Input I/i to show information while choosing===========");
    }


    public void displayBattleFieldWithInfo() {
        System.out.println("============================Battle Field============================");
        for(int i = 0; i<heroes.size(); i++) {
            System.out.format("\n\n        %s                              %s\n", heroes.get(i).getName(), monsters.get(i).getName());
            System.out.format("%s         %s\n%s         %s\n", heroes.get(i).stats2().split("\\n")[0],
                    monsters.get(i).toString().split("\\n")[0],
                    heroes.get(i).stats2().split("\\n")[1],
                    monsters.get(i).toString().split("\\n")[1]);
        }
        System.out.println("====================================================================");
    }

    // Get the target that hero will attack
    public int getHeroTarget(Hero hero) {
        int target = heroes.indexOf(hero);
        if(monsters.get(target).isCondition())
            return target;
        else {
            for(int i = 0; i<monsters.size(); i++) {
                if(monsters.get(i).isCondition()) {
                    target = i;
                    break;
                }
            }
        }
        return target;
    }

    // Get the target that monster will attack
    public int getMonsterTarget(Monster monster) {
        int target = monsters.indexOf(monster);
        if(heroes.get(target).isCondition())
            return target;
        else {
            for(int i = 0; i<heroes.size(); i++) {
                if(heroes.get(i).isCondition()) {
                    target = i;
                    break;
                }
            }
        }
        return target;
    }


    public int getHighestLevel() {
        int highestLevel = 0;
        for(Hero hero: heroes) {
            if(hero.getLevel() > highestLevel)
                highestLevel = hero.getLevel();
        }
        return highestLevel;
    }


    public void drawRow(String name_hero, String name_monster) {
        System.out.format("\n\n        %s                              %s\n\n", name_hero, name_monster);
    }

}
