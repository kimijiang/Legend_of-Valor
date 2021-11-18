/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// Equipment.java - All things heroes can be equipped with and use are thought as equipment.
public abstract class Equipment {

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

}
