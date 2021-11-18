/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

import java.util.List;

// Potion.java - Potions consumed to increase some attributes of heroes.
public class Potion extends Equipment{

    private double increaseAttr;
    private List<String> affectedAttr;

    Potion(String name, int price, int reqLevel, double increaseAttr, List<String> affectedAttr) {
        super(name, price, reqLevel);
        this.increaseAttr = increaseAttr;
        this.affectedAttr = affectedAttr;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s: affectedAttr)
            sb.append(s).append("/");
        return  name + "  " + price + "  " + reqLevel + "  " + increaseAttr + "  " + sb.toString().substring(0, sb.toString().length() - 1);
    }

    public double getIncreaseAttr() {return increaseAttr;}

    public List<String> getAffectedAttr() {return affectedAttr;}

}
