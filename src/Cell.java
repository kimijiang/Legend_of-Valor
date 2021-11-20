/**
 *
 * Author: Haoyu Zhang
 * BU ID: U81614811
 * 2021/11/06
 *
 */

// Cell.java - Single cell in the map, the super class of CommonSpace, Inaccessible and Market.
public abstract class Cell {
    protected int flag = 0;// flag = 1, another hero is already in this cell;flag = 0, not

    protected String sign; // The sign showed on the map

    Cell(String sign) {
        this.sign = sign;
    }


    public String getSign() {return sign;}

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setFlag(int flag){
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }
}
