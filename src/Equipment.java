import java.util.List;

/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// Equipment.java - All things heroes can be equipped with and use are thought as equipment.
public abstract class Equipment implements IsBuyable, IsSellable{

    protected String name;
    protected int price;
    protected int reqLevel;

    Equipment(String name, int price, int reqLevel) {
        this.name = name;
        this.price = price;
        this.reqLevel = reqLevel;
    }

    public String getName() {
        return name;
    }

    public int getReqLevel() {
        return reqLevel;
    }

    public int getPrice() {return price;}

    public void buy(Hero hero, Equipment equipment){
        if (hero.getLevel() < equipment.getReqLevel()) {
            System.out.println(hero.getName() + "'s level is lower than required.");
            return;
        }
        if(hero.getMoney() < equipment.getPrice())
            System.out.println(hero.getName() + " does not have enough money.");
        else{
            hero.setMoney(hero.getMoney() - equipment.getPrice());
            System.out.println(hero.getName() + " bought " + equipment.getName());
            hero.getEquipment(equipment);
        }
    }

    public void sell(Player player,String name){
        List<Hero> heroList = player.getTeam();
        Equipment item = null;
        Hero hero = null;
        for(int i = 0; i<heroList.size(); i++) {
            hero = heroList.get(i);
            item = hero.getEquipmentByName(name);
            if(item != null)
                break;
        }
        hero.removeEquipment(item.getName());
        hero.setMoney(hero.getMoney() + item.getPrice() / 2);
        System.out.println(hero.getName() + " sold " + item.getName() + " and got " + item.getPrice() / 2);
    }


}
