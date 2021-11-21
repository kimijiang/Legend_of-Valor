import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Fight.java - Control the process of a fight between heroes and monsters.
public class Fight {

    private static final int EXP_WIN = 2; // Exp get in each fight if heroes win

    private Hero hero;
    private Monster monster;

    Fight(Hero hero, Monster monster) {
        this.hero = hero;
        this.monster = monster;
    }

    // fight starts
    public  void fight() {
        displayBattleField();
        chooseOperation();
    }



    public void chooseOperation() {
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
                    monster.attacked(hero.causeDamage(), hero);
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
                            hero.castSpellTo(Integer.parseInt(num_spell), monster);
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
        drawRow(hero.getName(), monster.getName());
        System.out.println("=============Input I/i to show information while choosing===========");
    }


    public void displayBattleFieldWithInfo() {
        System.out.println("============================Battle Field============================");
        System.out.format("\n\n        %s                              %s\n", hero.getName(), monster.getName());
        System.out.format("%s         %s\n%s         %s\n", hero.stats2().split("\\n")[0],
                monster.toString().split("\\n")[0],
                hero.stats2().split("\\n")[1],
                monster.toString().split("\\n")[1]);
        System.out.println("====================================================================");
    }


    public void drawRow(String name_hero, String name_monster) {
        System.out.format("\n\n        %s                              %s\n\n", name_hero, name_monster);
    }

}
