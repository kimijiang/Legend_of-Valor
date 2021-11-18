/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// Cell.java - Single cell in the map, the super class of CommonSpace, Inaccessible and Market.
public abstract class Cell {

    protected String sign; // The sign showed on the map

    Cell(String sign) {
        this.sign = sign;
    }

    public String getSign() {return sign;}

    public void setSign(String sign) {
        this.sign = sign;
    }
}
