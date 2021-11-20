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

// Player.java - The player in this game, store the heroes team and what player can do in the game.
public class Player {

    private String sign;
    private List<Hero> team = new ArrayList<Hero>();

    Player(int n) {
        sign = "\033[31m" + "*";
        createTeam(n);
    }

    // create team with the number of heroes
    public void createTeam(int n) {
        Random rd = new Random();
        if(n == 1) {
            List<Warrior> warriorList = Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Warriors.txt");
            Warrior warrior = warriorList.get(rd.nextInt(warriorList.size()));
            team.add(warrior);
            System.out.println(warrior.getName() + "(Warrior) joined your team.");
        }
        else if(n == 2) {
            List<Warrior> warriorList = Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Warriors.txt");
            List<Sorcerer> sorcererList = Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Sorcerers.txt");
            Warrior warrior = warriorList.get(rd.nextInt(warriorList.size()));
            Sorcerer sorcerer = sorcererList.get(rd.nextInt(sorcererList.size()));
            team.add(warrior);
            System.out.println(warrior.getName() + "(Warrior) joined your team.");
            team.add(sorcerer);
            System.out.println(sorcerer.getName() + "(Sorcerer) joined your team.");
        }
        else if(n == 3) {
            List<Warrior> warriorList = Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Warriors.txt");
            List<Sorcerer> sorcererList = Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Sorcerers.txt");
            List<Paladin> paladinList = Utils.scanTextFile(System.getProperty("user.dir") + "/src/ConfigFiles/Paladins.txt");
            Warrior warrior = warriorList.get(rd.nextInt(warriorList.size()));
            Sorcerer sorcerer = sorcererList.get(rd.nextInt(sorcererList.size()));
            Paladin paladin = paladinList.get(rd.nextInt(paladinList.size()));
            team.add(warrior);
            System.out.println(warrior.getName() + "(Warrior) joined your team.");
            team.add(sorcerer);
            System.out.println(sorcerer.getName() + "(Sorcerer) joined your team.");
            team.add(paladin);
            System.out.println(paladin.getName() + "(Paladin) joined your team.");
        }
    }

    /*public void move(String moveSign, Map map) { // change to hero
        // move up
        if(moveSign.equals("W") || moveSign.equals("w")) {
            // out of bound
            if(map.getLocation_player()[1] - 1 < 0) {
                System.out.println("You can not move up!");
                return;
            }
            else {
                int x = map.getLocation_player()[0];
                int y = map.getLocation_player()[1] - 1;
                accessCell(x, y, map, "up");
            }
        }
        else if (moveSign.equals("S") || moveSign.equals("s")) {
            if(map.getLocation_player()[1] + 1 >= map.getLength_side()) {
                System.out.println("You can not move down!");
                return;
            }
            else {
                int x = map.getLocation_player()[0];
                int y = map.getLocation_player()[1] + 1;
                accessCell(x, y, map, "down");
            }
        }
        else if(moveSign.equals("A") || moveSign.equals("a")) {
            if(map.getLocation_player()[0] - 1 < 0) {
                System.out.println("You can not move left!");
                return;
            }
            else {
                int x = map.getLocation_player()[0] - 1;
                int y = map.getLocation_player()[1];
                accessCell(x, y, map, "left");
            }
        }
        else if(moveSign.equals("D") || moveSign.equals("d")) {
            if(map.getLocation_player()[0] + 1 >= map.getLength_side()) {
                System.out.println("You can not move right!");
                return;
            }
            else {
                int x = map.getLocation_player()[0] + 1;
                int y = map.getLocation_player()[1];
                accessCell(x, y, map, "right");
            }
        }
    }*/


    /*public void accessCell(int x, int y, Map map, String direction) {// judge cell type
        if(map.getCell(x, y) instanceof Inaccessible)
            System.out.println("You can not move " + direction + "!");
        else if(map.getCell(x, y) instanceof Market){
            map.updateLocation(x, y);
            ((Market) map.getCell(x, y)).accessed(this);
        }
        else if(map.getCell(x, y) instanceof CommonSpace) {
            map.updateLocation(x, y);
            if(((CommonSpace) map.getCell(x, y)).ifEngageBattle())
                new Fight(team).fight();
            else
                System.out.println("Nothing happened.");
        }
    }*/


    public void showTeam() {
        System.out.print("Your team: ");
        for(int i = 0; i<team.size(); i++) {
            System.out.print(team.get(i).getName() + "(" + i + ")  ");
        }
        System.out.println("");
    }

    public void showInformation1() {
        for(Hero hero: team) {
            System.out.println(hero.stats1());
        }
    }


    public List<Hero> getTeam() {return team;}

}
