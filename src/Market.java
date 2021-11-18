/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

import java.util.*;

// Market.java - A subclass of cell, heroes can access a market and buy or sell items.
public class Market extends Cell{

    public static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\033[0m";

    private List<Weapon> weaponsSold;
    private List<Armor> armorsSold;
    private List<Spell> spellsSold;
    private List<Potion> potionsSold;
    private List<Equipment> allCommodities; // Include weapons, armors, spells and potions.

    Market(List<Weapon> weapons, List<Armor> armors, List<Spell> spells, List<Potion> potions) {
        super(ANSI_BLUE + "M" + ANSI_RESET);
        weaponsSold = weapons;
        armorsSold = armors;
        spellsSold = spells;
        potionsSold = potions;
        allCommodities = new ArrayList<Equipment>();
        allCommodities.addAll(weapons);
        allCommodities.addAll(armors);
        allCommodities.addAll(spells);
        allCommodities.addAll(potions);
    }


    public void accessed(Player player) {
        displayCommodities();
        accessMenu();
        Scanner input = new Scanner(System.in);
        String regex1 = "[123]";
        String next = null;
        while((next = input.next()) != null) {
            if(!next.matches(regex1)) {
                System.out.println("Wrong input! Please input again.");
                continue;
            }
            else {
                // Buy items
                if(next.equals("1")) {
                    player.showTeam();
                    System.out.println("Input numbers for heroes to buy items.(exp: 0-3, which means the 0th hero to buy the 3th item.)");
                    String regex2 = "[0-" + (player.getTeam().size()-1) + "]-" + "[0-9]\\d{0,1}";
//                    System.out.println(regex2);
                    String buy = null;
                    while ((buy = input.next()) != null) {
                        if(!buy.matches(regex2)) {
                            System.out.println("Wrong input! Please input again.");
                            continue;
                        }
                        else {
                            String[] num = buy.split("-");
                            if(Integer.parseInt(num[1]) >= allCommodities.size()) {
                                System.out.println("Wrong input! Please input again.");
                                continue;
                            }
                            buyItem(player.getTeam().get(Integer.parseInt(num[0])), Integer.parseInt(num[1]));
                            break;
                        }
                    }
                    displayCommodities();
                    accessMenu();
                    continue;
                }
                // Sell items
                else if(next.equals("2")) {
                    List<Hero> heroList = player.getTeam();
                    List<Equipment> equipment = new ArrayList<Equipment>();
                    for(Hero hero: heroList)
                        equipment.addAll(hero.getAll());
                    if(equipment.size() == 0) {
                        System.out.println("Nothing to sell for each hero.");
                        continue;
                    }
                    for(int i = 0; i<heroList.size(); i++) {
                        System.out.print(i + ". ");
                        heroList.get(i).showEquipAndInventory();
                    }
                    System.out.println("Input the name of item to sell it:");
                    String name = input.next();
                    while(!sellItem(player, name)) {
                        System.out.println("No such item. Please input again.");
                        name = input.next();
                    }
                    displayCommodities();
                    accessMenu();
                    continue;
                }
                // Quit the market
                else if(next.equals("3")) {
                    System.out.println("You quit the market.");
                    break;
                }
            }
        }

    }

    public void accessMenu() {
        System.out.println("Please choose what you'd like to do next.");
        System.out.println("1: Buy items.");
        System.out.println("2: Sell your items.");
        System.out.println("3: Quit.");
    }


    public void displayCommodities() {
        int num = 0;
        System.out.println("=========================Menu=========================");
        System.out.println("Weaponry:");
        System.out.println("   Name    Cost    Level    Damage    Require hands");
        for(Weapon weapon: weaponsSold) {
            System.out.println(String.valueOf(num) + ".  " + weapon);
            num++;
        }
        System.out.println("Armory:");
        System.out.println("   Name    Cost    Level    Damage reduction");
        for(Armor armor: armorsSold) {
            System.out.println(String.valueOf(num) + ".  " + armor);
            num++;
        }
        System.out.println("Spells:");
        System.out.println("   Name    Cost    Level    Damage    Mana cost");
        for(Spell spell: spellsSold) {
            System.out.println(String.valueOf(num) + ".  " + spell);
            num++;
        }
        System.out.println("Potions:");
        System.out.println("   Name    Cost    Level    Attribute increase    Attribute affected");
        for(Potion potion: potionsSold) {
            System.out.println(String.valueOf(num) + ".  " + potion);
            num++;
        }
        System.out.println("========================================================");
//        System.out.println("Welcome to the market.");
    }


    public void buyItem(Hero hero, int index) {
        Equipment item = getCommodityByIndex(index);
        if (hero.getLevel() < item.getReqLevel()) {
            System.out.println(hero.getName() + "'s level is lower than required.");
            return;
        }
        if(hero.getMoney() < item.getPrice())
            System.out.println(hero.getName() + " does not have enough money.");
        else {
            hero.setMoney(hero.getMoney() - item.getPrice());
            if(item instanceof Weapon)
                weaponsSold.remove(item);
            else if(item instanceof Armor)
                armorsSold.remove(item);
            else if(item instanceof Spell)
                spellsSold.remove(item);
            else if(item instanceof Potion)
                potionsSold.remove(item);
            allCommodities.remove(item);
            System.out.println(hero.getName() + " bought " + item.getName());
            hero.getEquipment(item);
        }
    }

    public boolean sellItem(Player player, String name) {
        List<Hero> heroList = player.getTeam();
        Equipment item = null;
        Hero hero = null;
        for(int i = 0; i<heroList.size(); i++) {
            hero = heroList.get(i);
            item = hero.getEquipmentByName(name);
            if(item != null)
                break;
        }
        if(item != null) {
            if(item instanceof Weapon)
                weaponsSold.add((Weapon) item);
            else if(item instanceof Armor)
                armorsSold.add((Armor) item);
            else if(item instanceof Spell)
                spellsSold.add((Spell) item);
            else if(item instanceof Potion)
                potionsSold.add((Potion) item);
            updateAllCommodities();
            hero.removeEquipment(item.getName());
            hero.setMoney(hero.getMoney() + item.getPrice() / 2);
            System.out.println(hero.getName() + " sold " + item.getName() + " and got " + item.getPrice() / 2);
            return true;
        }
        else
            return false;
    }

    public Equipment getCommodityByIndex(int index) {
            return allCommodities.get(index);
    }

    // Update commodity list after buying or selling
    public void updateAllCommodities() {
        List<Equipment> commodities = new ArrayList<Equipment>();
        commodities.addAll(weaponsSold);
        commodities.addAll(armorsSold);
        commodities.addAll(spellsSold);
        commodities.addAll(potionsSold);
        allCommodities = commodities;
    }


}
