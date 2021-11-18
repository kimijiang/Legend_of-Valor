/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

import java.util.Scanner;

// Legends.java - Control the process of the game, initiate and proceed the game.
public class Legends extends RPGGame {


    Legends() {
    }


    @Override
    public void initiateGame() {
        System.out.println("Welcome to Legends: Monsters and Heroes!");
        System.out.println("How many heroes would you like in the team?(1~3)");
        Scanner input = new Scanner(System.in);
        String strNum = null;
        String regex1 = "[123]";
        while((strNum = input.next()) != null) {
            if(!strNum.matches(regex1))
                System.out.println("Wrong input! Please input again.");
            else
                break;
        }
        player = new Player(Integer.parseInt(strNum));
        System.out.println("What is the size of the nxn map?(6~8)");
        String regex2 = "[678]";
        while((strNum = input.next()) != null) {
            if(!strNum.matches(regex2))
                System.out.println("Wrong input! Please input again.");
            else
                break;
        }
        map = new Map(Integer.parseInt(strNum));
        startGame();
    }


    @Override
    public void startGame() {
        System.out.println("Game starts!");
        map.displayMap();
        Scanner input = new Scanner(System.in);
        String regex = "[WwAaSsDdQqIiGg]";
        String next = null;
        while((next = input.next()) != null) {
            if(!next.matches(regex)) {
                System.out.println("Wrong input! Please input again.");
                continue;
            }
            else {
                if(next.matches("[WwAaSsDd]")) {
                    player.move(next, map);
                    map.displayMap();
                }
                // show information
                else if(next.matches("[Ii]"))
                    player.showInformation1();
                // quit game
                else if(next.matches("[Qq]")) {
                    System.out.println("Game over.");
                    System.exit(0);
                }
                // get inventory
                else if(next.matches("[Gg]")) {
                    boolean equipOrUse = false;
                    for(Hero hero: player.getTeam()) {
                        hero.showEquipAndInventory();
                        if(hero.getPotions().size() > 0 || hero.getInventory().size() > 0)
                            equipOrUse = true;
                    }
                    if(!equipOrUse)
                        System.out.println("No weapon/armor or potion to equipped/use.");
                    else {
                        System.out.println("Input the name of weapon/armor to get equipped and of potion to use.");
                        String name = input.next();
                        for(int i = 0; i<player.getTeam().size(); i++) {
                            for(Equipment e: player.getTeam().get(i).getAll())
                                if(e.getName().equals(name)) {
                                    if(e instanceof Potion)
                                        player.getTeam().get(i).consumePotionByName(name);
                                    else if(e instanceof Weapon)
                                        player.getTeam().get(i).changeWeapon((Weapon) player.getTeam().get(i).getEquipmentByName(name));
                                    else if(e instanceof Armor)
                                        player.getTeam().get(i).changeArmor((Armor) player.getTeam().get(i).getEquipmentByName(name));
                                }
                        }
                    }
                }
            }
        }
    }



}
