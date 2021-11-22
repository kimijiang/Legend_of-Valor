/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Hero.java - Store the information of hero in the player's team, all things they have and what they can do.
public abstract class Hero {

    protected String name;
    protected int level;
    protected double maxHP;
    protected double HP;
    protected double mana;
    protected double strength;
    protected double dexterity;
    protected double agility;
    protected double defense;
    protected int money;
    protected int exp;
    protected int upNeedExp; // exp needed to level up
    protected boolean condition; // true: alive, false: faint
    protected double proDodge; // the probability of dodging an attack
    protected int heroNumber;
    public int heroPositionX;
    public int heroPositionY;

    protected Weapon weapon;
    protected Armor armor;
    protected List<Spell> spells;
    protected List<Potion> potions;
//    protected List<Hero> hlist;

    protected Inventory inventory; // unequipped weapons and armors are stored in the inventory

    Hero(String name, double mana, double strength, double dexterity, double agility, int money, int exp) { // hero number added
        this.name = name;
        this.level = 1;
        this.maxHP = 100.0;
        this.HP = 100.0;
        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
        this.defense = 0;
        this.money = money;
        this.exp = exp;
        this.upNeedExp = 10;
        this.condition = true;
        this.proDodge = agility * 0.002;
        this.weapon = null;
        this.armor = null;
        this.spells = new ArrayList<Spell>();
        this.potions = new ArrayList<Potion>();
        this.inventory = new Inventory();
//        this.heroNumber = heroNumber;
    }

    // skills get increased
    public abstract void skillUp();

    // favored skills get increased
    public abstract void favoredSkillUp();

    public boolean ifLevelUp() {
        // if level up
        if(exp >= upNeedExp) {
            while(exp >= upNeedExp) {
                level++;
                exp -= upNeedExp;
                upNeedExp = level * 10;
                mana = 1.1 * mana;
                skillUp();
                favoredSkillUp();
            }
            maxHP = 100 * level;
            HP = 100 * level;
            return true;
        }
        else
            return false;
    }

    public int getHeroNumber() {
        return heroNumber;
    }

    public void recExp(int exp) {
        this.exp += exp;
    }

    public void regainHPAndMana() {
        HP += 0.1 * maxHP;
        mana += 0.1 * mana;
    }

    // Heroes who fainted reborn after the fight
    public void reborn() {// relocate to nexus
        condition = true;
        HP = maxHP;
        this.setMoney(getMoney() / 2);
        System.out.println(getName() + " lost " + getMoney() / 2 + " money.");
    }


    public double causeDamage() { // Weapon.attack
        if(weapon == null)
            return 0.05 * strength;
        else
            return 0.05 * (strength + weapon.attack());
    }


    public void getEquipment(Equipment item) {
        if(item instanceof Spell) {
            spells.add((Spell) item);
            System.out.println(name + " got a new " + item.getClass().toString().split(" ")[1] + " " + item.getName() + ".");
        }
        else if(item instanceof Potion) {
            potions.add((Potion) item);
            System.out.println(name + " got a new potion " + item.getName() + ".");
        }
        else {
            inventory.add(item);
            System.out.println(name + " got the " + item.getClass().toString().split(" ")[1] + ".");
        }
    }

    public void changeWeapon(Weapon weapon) {
        if(weapon != null)
            inventory.add(this.weapon);
        this.weapon = weapon;
        System.out.println(name + " got equipped with the weapon " + weapon.getName() + ".");
    }


    public void changeArmor(Armor armor) {
        if(armor != null)
            inventory.add(this.armor);
        this.armor = armor;
        System.out.println(name + " got equipped with the armor " + armor.getName() + ".");
    }

    // remove the equipment from inventory
    public void removeEquipment(String name) {
        if(inventory.getSize() > 0)
            for(Equipment e: inventory.getEquipments()) {
                if(e.getName().equals(name)) {
                    inventory.remove(e);
                    return;
                }
            }
        if(weapon.getName().equals(name)) {
            weapon = null;
            return;
        }
        if(armor.getName().equals(name)) {
            armor = null;
            return;
        }
        if(spells.size() > 0)
            for(Spell s: spells) {
                if(s.getName().equals(name)) {
                    spells.remove(s);
                    return;
                }
            }
        if(potions.size() > 0)
            for(Potion p: potions) {
                if(p.getName().equals(name)) {
                    potions.remove(p);
                    return;
                }
            }
    }

    public void castSpellTo(int index, Monster monster) {// Spell.cast TODO:implements cast
        /*double manaNeeded = spells.get(index).getMana();
        if(mana < manaNeeded)
            System.out.println("Failed! " + name + " does not have enough mana.");
        else {
            monster.attacked(spells.get(index).getBaseDamage() * (1 + dexterity / 10000.0), this);
            spells.get(index).deteriorate(monster);
            mana -= manaNeeded;
        }*/
        spells.get(index).cast(this,monster);
    }

    public void attacked(double damage, Monster monster) {// armor.wear  reduce damage
        if(proDodge >= Math.random()) {
            System.out.println(this.name + " dodged the attack from " + monster.getName() + ".");
            return;
        }
        if(armor != null) {
            if(damage - defense - armor.wear() >= 0)//armor.wear reduce damage
                damage -= defense + armor.wear();
            else
                damage = 0;
        }
        else{
            if(damage - defense >= 0)
                damage -= defense;
            else
                damage = 0;
        }
        HP -= damage;
        if(HP <= 0) {
            condition = false;
            System.out.println(monster.getName() + " caused " + damage + " damage to " + this.name + ", and then " + this.name + " faint.");
        }
        else
            System.out.println(monster.getName() + " caused " + damage + " damage to " + this.name);
    }


    public void usePotion(int index) {//potion.use
        /*double increase = potions.get(index).getIncreaseAttr();
        List<String> attributes = potions.get(index).getAffectedAttr();
        for(String attribute: attributes) {
            switch (attribute) {
                case "Health":
                    HP += increase;
                    break;
                case "Mana":
                    mana += increase;
                    break;
                case "Defense":
                    defense += increase;
                    break;
                case "Strength":
                    strength += increase;
                    break;
                case "Dexterity":
                    dexterity += increase;
                    break;
                case "Agility":
                    agility += increase;
                    proDodge = agility * 0.002;
                    break;
            }
        }

        System.out.println(name + "'s statistics have increased.");*/

        potions.get(index).use(this);
        potions.remove(index);
    }



    public void consumePotionByName(String name) {
        int i = 0;
        for(; i<potions.size(); i++) {
            if(potions.get(i).getName().equals(name))
                break;
        }
        usePotion(i);
    }


    public void showEquipAndInventory() {
        System.out.println(name + "(" + this.getClass().toString().split(" ")[1] + "):");
        if(weapon != null)
            System.out.print("Weapon: " + weapon.getName() + "  ");
        else
            System.out.print("Weapon: " + "None  ");
        if(armor != null)
            System.out.println("Armor: " + armor.getName() + "  ");
        else
            System.out.println("Armor: " + "None  ");
        if(potions.size() != 0) {
            System.out.print("Potions:");
            for(Potion p: potions)
                System.out.print(" " + p.getName());
            System.out.println("");
        }
        else
            System.out.println("Potions: None");
        if(spells.size() != 0) {
            System.out.print("Spells:");
            for(Spell s: spells)
                System.out.print(" " + s.getName());
            System.out.println("");
        }
        else
            System.out.println("Spells: None");
        if(inventory.getSize() != 0) {
            System.out.print("Inventory:");
            for(Equipment e: inventory.getEquipments())
                System.out.print(e.getName() + "  ");
            System.out.println("");
        }
        else
            System.out.println("Inventory: None");
    }


    public Equipment getEquipmentByName(String name) {
        if(inventory.getSize() > 0)
            for(Equipment equipment: inventory.getEquipments())
                if(equipment.getName().equals(name))
                    return equipment;
        if(weapon != null)
            if(weapon.getName().equals(name))
                return weapon;
        if(armor != null)
            if(armor.getName().equals(name))
                return armor;
        if(spells.size() > 0)
            for(Spell spell: spells)
                if(spell.getName().equals(name))
                    return spell;
        if(potions.size() > 0)
            for(Potion potion: potions)
                if(potion.getName().equals(name))
                    return potion;
        return null;
    }


    public void showSpells() {
        System.out.println("Spells:");
        if(spells.size() == 0) {
            System.out.println("None");
            return;
        }
        for(int i = 0; i<spells.size(); i++) {
            Spell spell = spells.get(i);
            System.out.println(i + ". " + spell.getName() + "(" + spell.getClass().toString().split(" ")[1] + ")  " + spell.getMana() + " mana");
        }
    }

    public void showPotions() {
        System.out.println("Potions:");
        if(potions.size() == 0) {
            System.out.println("None");
            return;
        }
        for(int i = 0; i<potions.size(); i++) {
            Potion potion = potions.get(i);
            System.out.println(i + ". " + potion.getName() + ": increase " + potion.getAffectedAttr());
        }
    }

    // Get all items
    public List<Equipment> getAll() {
        List<Equipment> equipmentList = new ArrayList<Equipment>();
        if(weapon != null)
            equipmentList.add(weapon);
        if(armor != null)
            equipmentList.add(armor);
        if(spells.size() != 0)
            for(Spell spell: spells)
                equipmentList.add(spell);
        if(potions.size() != 0)
            for(Potion potion: potions)
                equipmentList.add(potion);
        if(inventory.getSize() != 0)
            for(Equipment equipment: inventory.getEquipments())
                equipmentList.add(equipment);
        return equipmentList;
    }


    public String getName() {
        return name;
    }

    public int getLevel() {return level;}

    public int getMoney() {return money;}

    public void setMoney(int money) {this.money = money;}

    public double getMana() {return mana;}

    public Weapon getWeapon() {return weapon;}

    public Armor getArmor() {return armor;}

    public List<Spell> getSpells() {return spells;}

    public List<Potion> getPotions() {return potions;}

    public List<Equipment> getInventory() {return inventory.getEquipments();}

    public double getDexterity() {
        return dexterity;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    public boolean isCondition() {
        return condition;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public void setAgility(double agility) {
        this.agility = agility;
    }

    public void setDexterity(double dexterity) {
        this.dexterity = dexterity;
    }

    public double getAgility() {
        return agility;
    }

    public double getStrength() {
        return strength;
    }

    public double getDefense() {
        return defense;
    }

    public void setProDodge(double proDodge) {
        this.proDodge = proDodge;
    }

    public double getProDodge() {
        return proDodge;
    }

    // not in a fight
    public String stats1() {
        return name + "(" + getClass().toString().split(" ")[1] +"):\n" +
                "Level:" + level + "  " +
                "HP:" + HP + "  " +
                "Mana:" + mana + "  " +
                "Current exp:" + exp + "  " +
                "Money:" + money + "\n" +
                "Strength:" + strength + "  " +
                "Dexterity:" + dexterity + "  " +
                "Agility:" + agility;
    }

    // in a fight
    public String stats2() {
        String stats = "Level:" + level + "  " +
                "HP:" + HP + "  " +
                "Mana:" + mana + "\n";
        if(weapon == null)
            stats += "Weapon: None";
        else
            stats += "Weapon: " + weapon.getName() + "  ";
        if(armor == null)
            stats += "Armor: None";
        else
            stats += "Armor: " + armor.getName();
        return stats;
    }

    public void move(String moveSign, Map map){
        // move up
        int[][] heroesPosition = map.getLocationOfHeroes();
        heroPositionX = heroesPosition[heroNumber][0];
        heroPositionY = heroesPosition[heroNumber][1];

        if(moveSign.equals("W") || moveSign.equals("w")) {
            // out of bound
            if(heroPositionY - 1 < 0) {
                System.out.println("You can not move up!");
                return;
            }
            else {
                if(map.checkCell(heroPositionX,heroPositionY-1)){
                    map.setCell(heroPositionX,heroPositionY,0);
                    map.setCell(heroPositionX,heroPositionY-1,1);
                    accessCell(heroPositionX, heroPositionY-1, map, "up");
                }
                else{
                    System.out.println("A hero is already in this cell! ");
                    return;
                }
            }
        }

        else if (moveSign.equals("S") || moveSign.equals("s")) {
            if(heroPositionY + 1 >= map.getLength_side()) {
                System.out.println("You can not move down!");
                return;
            }
            else {
                if(map.checkCell(heroPositionX,heroPositionY+1)){
                    map.setCell(heroPositionX,heroPositionY,0);
                    map.setCell(heroPositionX,heroPositionY+1,1);
                    accessCell(heroPositionX, heroPositionY+1, map, "down");
                }
                else{
                    System.out.println("A hero is already in this cell! ");
                    return;
                }
            }
        }

        else if(moveSign.equals("A") || moveSign.equals("a")) {
            if(heroPositionX - 1 < 0) {
                System.out.println("You can not move left!");
                return;
            }
            else {
                if(map.checkCell(heroPositionX-1,heroPositionY)) {
                    map.setCell(heroPositionX,heroPositionY,0);
                    map.setCell(heroPositionX-1,heroPositionY,1);
                    accessCell(heroPositionX-1, heroPositionY, map, "left");
                }
                else{
                    System.out.println("A hero is already in this cell! ");
                    return;
                }
            }
        }

        else if(moveSign.equals("D") || moveSign.equals("d")) {
            if(heroPositionX + 1 >= map.getLength_side()) {
                System.out.println("You can not move right!");
                return;
            }
            else {
                if(map.checkCell(heroPositionX+1,heroPositionY)){
                    map.setCell(heroPositionX,heroPositionY,0);
                    map.setCell(heroPositionX+1,heroPositionY,1);
                    accessCell(heroPositionX+1, heroPositionY, map, "right");
                }
                else{
                    System.out.println("A hero is already in this cell! ");
                    return;
                }
            }
        }

//        else if(moveSign.equals("T") || moveSign.equals("t")){ //teleport
//            teleport(heroPositionX,heroPositionY,map);
//        }
//
//        else if(moveSign.equals("B") || moveSign.equals("b")){
//            back(heroPositionX,heroPositionY,map);
//        }


    }

    public void accessCell(int x, int y, Map map, String direction) {// judge cell type
        if(map.getCell(x, y) instanceof Inaccessible)
            System.out.println("You can not move " + direction + "!");
        else if(map.getCell(x, y) instanceof Market){
            map.updateLocation(x, y, heroNumber);
            ((Market) map.getCell(x, y)).accessed(this);
        }
        else if(map.getCell(x, y) instanceof CommonSpace) {
            map.updateLocation(x, y, heroNumber);
            if(((CommonSpace) map.getCell(x, y)).ifEngageBattle()){
                // Plain Bush Cave Koulou
                if(map.getCell(x,y) instanceof PlainCell){
//                    hlist.add(this);
//                    new Fight(hlist).fight();
//                    hlist.remove(this);
                }
                else if(map.getCell(x,y) instanceof BushCell){
                    this.dexterity = dexterity*1.1;
//                    hlist.add(this);
//                    new Fight(hlist).fight();
//                    hlist.remove(this);
                    this.dexterity = dexterity/1.1;
                }
                else if(map.getCell(x,y) instanceof CaveCell){
                    this.agility = agility*1.1;
//                    hlist.add(this);
//                    new Fight(hlist).fight();
//                    hlist.remove(this);
                    this.agility = agility/1.1;
                }
                else{
                    this.strength = strength*1.1;
//                    hlist.add(this);
//                    new Fight(hlist).fight();
//                    hlist.remove(this);
                    this.strength = strength/1.1;
                }
            }
            else
                System.out.println("Nothing happened.");
        }
    }

    public int maxMonsterPosition(int[][] monstersPosition){
        int[] a = new int[monstersPosition.length];
        for(int i =0;i<monstersPosition.length;i++){
            a[i] = monstersPosition[i][1];
        }

        int temp;
        for(int i =0;i<a.length;i++){
            for(int j = i+1;j<a.length;j++){
                if(a[j]>a[i]){
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a[0];
    }


    public void teleport(int lane, Map map){
        Scanner s = new Scanner(System.in);
        int maxMonsterX = maxMonsterPosition( map.getLocationOfMonsters());

        if(lane == 0){
            map.setCell(heroPositionY, heroPositionX,0);
            map.setCell(1,maxMonsterX+1,1);
            map.updateLocation(1,maxMonsterX+1, heroNumber);
        }
        else if(lane == 1){
            map.setCell(heroPositionY, heroPositionX,0);
            map.setCell(4,maxMonsterX+1,1);
            map.updateLocation(4,maxMonsterX+1, heroNumber);
        }
        else{
            map.setCell(heroPositionY, heroPositionX,0);
            map.setCell(7, maxMonsterX+1,1);
            map.updateLocation(7, maxMonsterX+1, heroNumber);
        }

    }


    public void back(Map map){
        if(this.heroNumber == 0){
            map.setCell(heroPositionX, heroPositionY,0);
            map.updateLocation(0,7, heroNumber);
            heroPositionY = 7;
            heroPositionX = 0;
        }

        else if(this.heroNumber == 1){
            map.setCell(heroPositionX, heroPositionY,0);
            map.updateLocation(3,7, heroNumber);
            heroPositionY = 7;
            heroPositionX = 3;
        }

        else{
            map.setCell(heroPositionX, heroPositionY,0);
            map.updateLocation(6,7, heroNumber);
            heroPositionY = 7;
            heroPositionX = 6;
        }

    }


    public void setHeroNumber(int heroNumber) {
        this.heroNumber = heroNumber;
    }

}
