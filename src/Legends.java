import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Legends extends RPGGame {

    private int round = 0;
    private boolean ifPlayerWin;

    private List<Monster> monsters = new ArrayList<Monster>();
    private int num_monster = 0;

    private static final int NUM_HERO = 3;
    private static final int LENGTH_EDGE = 8;
    private static final int ROUND_SPAWN = 8;

    Legends() {
    }


    @Override
    public void initiateGame() {
        System.out.println("Welcome to Legends of Valor!");
        player = new Player(NUM_HERO);
        map = new Map(LENGTH_EDGE);
        monsters = new ArrayList<Monster>();
        monstersSpawn();
        startGame();
    }


    @Override
    public void startGame() {
        map.displayMap();
        while(isOver()) {
            round++;
            if(round % ROUND_SPAWN == 0)
                monstersSpawn();
            round();
        }
        if(ifPlayerWin)
            System.out.println("Player win!");
        else
            System.out.println("Player lose.");
    }


    public void turnForHero(Hero hero) {
        System.out.println("H"+(hero.getHeroNumber()+1) + "'s turn");
        Scanner input = new Scanner(System.in);
        String regex = "[WwAaSsDdQqIiGgTt]";
        String next = null;
        while((next = input.next()) != null) {
            if(!next.matches(regex)) {
                System.out.println("Wrong input! Please input again.");
                continue;
            }
            else {
                if(next.matches("[WwAaSsDd]")) {
                    hero.move(next, map);
//                    map.displayMap();
                    break;
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
                    for(Hero h: player.getTeam()) {
                        h.showEquipAndInventory();
                        if(h.getPotions().size() > 0 || h.getInventory().size() > 0)
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
                else if(next.matches("[Tt]")) {
                    System.out.println("Which lane to teleport to?(0:top, 1:mid, 2:bot)");
                    String lane = null;
                    while((lane = input.next()) != null) {
                        if(!lane.matches("[012]")) {
                            System.out.println("Wrong input! Please input again.");
                            continue;
                        }
                        else {
                            // same lane
                            if(hero.getHeroNumber() == Integer.parseInt(lane)) {
                                System.out.println("Can't telepot to the same lane.");
                                continue;
                            }
                            else {
                                hero.teleport(Integer.parseInt(lane), map);
                                System.out.println(hero.getName() + "teleports to ");
                                break;
                            }
                        }
                    }
                    break;
                }
                else if(next.matches("[Bb]")) {
                    hero.back(map);
                    break;
                }
            }
        }
    }


    public void turnForMonster(Monster monster) {
        Hero enemy = null;
        if(map.getEnemy(monster.getMonsterNumber()) >= 0) {
            enemy = player.getTeam().get(map.getEnemy(monster.getMonsterNumber()));
            enemy.attacked(monster.getDamage(), monster);
        }
        else
            monster.move(map);
    }


    public void monstersSpawn() {
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
        if(monsterList.size() < player.getTeam().size()) {
            System.out.println("Oops! No enough corresponding monsters in text files.");
            System.exit(0);
        }
        Random rd = new Random();
        for(int i = 0; i<player.getTeam().size(); i++) {
            Monster monster = monsterList.get(rd.nextInt(monsterList.size()));
            monster.setMonsterNumber(num_monster++);
            monsters.add(monster);
        }
        map.monstersSpawn();
        System.out.println("Three monsters spawn.");
    }


    public int getHighestLevel() {
        int highestLevel = 0;
        for(Hero hero: player.getTeam()) {
            if(hero.getLevel() > highestLevel)
                highestLevel = hero.getLevel();
        }
        return highestLevel;
    }


    public boolean isOver() {
        boolean heroReachNexus = false;
        boolean monsterReachNexus = false;
        int[][] location_heroes = map.getLocationOfHeroes();
        int[][] location_monsters = map.getLocationOfMonsters();
        for(int i=0; i<location_heroes.length; i++) {
            if(location_heroes[i][0] == 0)
                heroReachNexus = true;
        }
        for(int i=0; i<location_monsters.length; i++) {
            if(location_monsters[i][0] == 7)
                monsterReachNexus = true;
        }
        if(heroReachNexus)
            ifPlayerWin = true;
        else if(monsterReachNexus)
            ifPlayerWin = false;
        return heroReachNexus || monsterReachNexus;
    }


    public void round() {
        for(Hero hero: player.getTeam())
            hero.regainHPAndMana();
        if(round != 1)
            System.out.println("Heroes regain HP and mana.");
        for(Hero hero: player.getTeam()) {
            int enemy_monster = map.getEnemyForHero(hero.getHeroNumber());
            if(enemy_monster >=0) {
                new Fight(hero, monsters.get(enemy_monster)).fight();
            }
            else {
                turnForHero(hero);
                map.displayMap();
            }

        }
        for(Monster monster: monsters) {
            if(monster.isCondition()) {
                turnForMonster(monster);
            }
            else {
                map.monsterDead(monster.getMonsterNumber());
                monsters.remove(monster);
            }

        }
        map.displayMap();
    }


}
